package com.fbd.web.app.recharge.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.signature.bean.Signature;
import com.fbd.core.app.signature.util.FileUtils;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.utils.ContractUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.recharge.service.IRechargeAgreementService;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.HTMLUtils;


@Service(value="rechargeAgreementService")
public class RechargeAgreementServiceImpl implements IRechargeAgreementService {
	
    private static final Logger logger = LoggerFactory.getLogger(RechargeAgreementServiceImpl.class);

    
    @Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IContractTemplateDao contractTemplateDao;
	@Resource
	private IUserDao userDao;
	

	@Override
	public String signRechargeAgreement(String orderId,HttpServletRequest request,HttpServletResponse response) {
        String inFile = "";
        String outFile =  "";
		try{
			//查询充值信息信息
			RechargeModel recharge = this.rechargeDao.selectByOrderId(orderId);
			if(recharge!=null){
				String agreementState = recharge.getAgreementState();
				if("1".equals(agreementState)){
					return recharge.getAgreementUrl();
				}else{  //创建签章协议

					 String basePath = request.getSession().getServletContext().getRealPath("/")+"tempFile";
		             File file = new File(basePath);
		             if(!file.exists()){
		            	 file.mkdirs();
		             }
		             inFile = basePath+File.separator+PKGenarator.getId()+".pdf";
		             outFile = basePath+File.separator+PKGenarator.getId()+".pdf";
		             String path = createRechargeAgreement(inFile, outFile, recharge,request);
		             return path;
				}
			}else{
				throw new ApplicationException("当前充值信息不存在！"); 
			}
		}catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException("获取充值协议失败！");
		}finally{
            File file1 = new File(inFile);
            if(file1.exists()){
                file1.delete();
            }
            File file2 = new File(outFile);
            if(file2.exists()){
            	file2.delete();
            }
		}
	}
	
	private String createRechargeAgreement(String tempInFilePath,String tempOutFilePath,RechargeModel recharge,HttpServletRequest request) {
		
		try{
			ContractTemplateModel qmodel = new ContractTemplateModel();
			qmodel.setContractType("recharge_model");
			//查询用户信息
			UserModel user = this.userDao.findByUserId(recharge.getUserId());
			
			ContractTemplateModel rechargeTemplate = contractTemplateDao.getModel(qmodel);
			if(rechargeTemplate!=null){
				String content = rechargeTemplate.getTemplateContent();
				 //替换合同模板中的内容
				content = content.replace("#{realName}", user.getRealName())
						         .replace("#{nickName}", user.getNickName()) 
						         .replace("#{rechargeFee}", String.valueOf(recharge.getRechargeFee())) 
				                 .replace("#{rechargeAmt}", String.valueOf(recharge.getRechargeAmt()))
				                 .replace("#{actualAmt}", String.valueOf(recharge.getActualAmt()))
				                 .replace("#{orderId}",recharge.getOrderId())
				                 .replace("#{rechargeTime}",DateUtil.date2Str(recharge.getCreateTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
	            String body = dealContractTemplateContent(content).replace("&nbsp;", "&#160;");
	            body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body style=\"font-family:'SimSun'\">"
	                    + body + "</body></html>";
	            //========================电子签章合同生成============================
	            Document doc = new Document(PageSize.A4);
	            PdfWriter pw = PdfWriter.getInstance(doc,new FileOutputStream(tempInFilePath));
	            doc.open();
	            HTMLUtils.bfCN=BaseFont.createFont(ContractUtils.class.getClassLoader().getResource("")+"SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	            XMLWorkerHelper.getInstance().parseXHtml(pw,doc,new StringReader(body));
	            //获取 pdf的总页数
	            int pageNum = pw.getPageNumber();
	            doc.close();
	            logger.info("------------>开始调用签章服务器开始-------------");
	            //将文件转换为base64未的字符串
	            File signFile = new File(tempInFilePath);
	            String signDoc = FileUtils.fileToBase64Str(signFile);
	            
	            Map<String,String> params = new HashMap<String,String>();
	            Signature signature = new Signature();
	            signature.setDoc(signDoc);
	            signature.setPageNum(String.valueOf(pageNum));
	            signature.setPositionX(200);
	            signature.setPositionY(200);
	            String requestStr = JsonHelper.getStrFromObject(signature);
	            params.put("productCode",FbdCoreConstants.signature_product_code);
	            params.put("signature", requestStr);
	            logger.info("签章请求参数-params："+params);
	            String result = HttpRequestUtils.callHttpPOST(FbdCoreConstants.signature_server_url+"tsaSignApi/signContract.html", params);
	            @SuppressWarnings("unchecked")
	            Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
	            logger.info("------------>调用签章服务器结束-result:"+resultMap+"--------------");
	            String pdfDoc = "";
	            if(Boolean.parseBoolean(resultMap.get("success").toString())){
	            	 logger.info("=============签章成功=========");
	            	 pdfDoc = resultMap.get("doc").toString();
	            }else{
	            	pdfDoc = signDoc;
	            }
	            //将文件上传到ftp文件服务器上
	            logger.info("------------>文件上传到ftp文件服务器上-----------");
	            String fileName = uploadPDFToFileSystem(pdfDoc, request);
	            if(Boolean.parseBoolean(resultMap.get("success").toString())){
	            	 recharge.setAgreementState("1");
		             recharge.setAgreementUrl(fileName);
	            }else{
	            	recharge.setAgreementUrl(fileName);
	            }
	            this.rechargeDao.updateByOrderId(recharge);
	            return fileName;
			}else{
				throw new ApplicationException("充值协议模板不存在，请联系客服！");
			}
		}catch(Exception e){
			throw new ApplicationException("获取充值协议失败，请联系客服！");
		}
	}  
	
	
    public static String dealContractTemplateContent(String contractContent){
        Pattern pattern = Pattern.compile("[#{](.*?)[}]");
        return pattern.matcher(contractContent).replaceAll("___");
    }
    
    
    @SuppressWarnings("restriction")
    public String uploadPDFToFileSystem(String doc,HttpServletRequest request){
        String filePathName = "";
        try{
            String path = "recharge/"+DateUtil.date2Str(new Date(), "yyyyMMdd");   //合同保存路劲
            String basePath =  request.getSession().getServletContext().getRealPath(path);
            BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(doc);
            String fileName=PKGenarator.getId()+".pdf";
            File targetFile = new File(basePath, fileName);  
            if(!targetFile.exists()){  
                targetFile.mkdirs();  
            }  
            //保存在文件服务器上
            InputStream is = new ByteArrayInputStream(bytes); 
            boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName,is);
            if(uploadResult){ //上传成功
                filePathName =  path+"/"+fileName;
            }else{
                throw new ApplicationException("文件上传失败！");
            }
        }catch(Exception e){
            throw new ApplicationException(e.getMessage());
        }
        return filePathName;
    }

}
