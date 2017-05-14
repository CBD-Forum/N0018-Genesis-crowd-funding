/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ContractCreateServiceImpl.java 
 *
 * Created: [2016-8-24 下午7:41:11] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.signature.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.huiyuan.dao.ICreateEnterpriseMemberDao;
import com.fbd.core.app.huiyuan.dao.ICreatePersonalMemberDao;
import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.huiyuan.model.CreatePersonalMember;
import com.fbd.core.app.signature.bean.Signature;
import com.fbd.core.app.signature.service.IContractCreateService;
import com.fbd.core.app.signature.util.FileUtils;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSignatureDao;
import com.fbd.core.app.user.dao.IUserSignatureOrgDao;
import com.fbd.core.app.user.dao.IUserSignaturePersonDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.ContractUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.HTMLUtils;
//import com.kinggrid.pdf.KGPdfHummer;
//import com.kinggrid.pdf.executes.PdfSignature4KG;
//import com.kinggrid.pdf.executes.PdfSignature;
/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * Description: 
 * @version 1.0
 *
 */

@Service(value="contractCreateService")
public class ContractCreateServiceImpl implements IContractCreateService {
    
    private static final Logger logger = LoggerFactory.getLogger(ContractCreateServiceImpl.class);
    
    
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IContractTemplateDao contractTemplateDao;
    @Resource
    private IUserSignatureDao userSignatureDao;
    @Resource
    private IUserSignatureOrgDao userSignatureOrgDao;
    @Resource
    private IUserSignaturePersonDao userSignaturePersonDao;
    @Resource
    private ICreatePersonalMemberDao createPersonalMemberDao;
    @Resource
    private ICreateEnterpriseMemberDao createEnterpriseMemberDao;
    
    public static String dealContractTemplateContent(String contractContent){
        Pattern pattern = Pattern.compile("[#{](.*?)[}]");
        return pattern.matcher(contractContent).replaceAll("___");
    }

    @SuppressWarnings("unused")
    @Override
    public int createCrontractPdf(String loanNo,String investNo,String tempInFilePath,String tempOutFilePath, HttpServletRequest request,
            HttpServletResponse response) {
        
        int pageNum = 1; //PDF页数，默认是1页
        
        String body = "";
        String content  = "";
        //借款的合同详细信息
        Map<String, Object> loanDetail = null;
        //合同编号
        String contractNo = "";
        //合同模版编号
        String contractTplNo = "";
        try{
            
             //查询当前投资信息
             CrowdfundingSupportModel crowSupport = this.crowdfundingSupportDao.getByOrderId(investNo);
             //查询当前项目信息
             CrowdfundingModel crow = this.crowdfundingDao.getByloanNo(loanNo);
            
             //定义参数
             String loanUserName = "";                   //项目方/融资人
             String loanUserCertificateNumber = "";      //身份证号/营业执照号
             String loanUserPhone = "";                  //项目方联系人电话号码
             String supportUserName = "";                //投资方/合伙人
             String supportUserCertificateNumber = "";   //身份证号/营业执照号
             String supportUserPhone = "";               //投资人手机号
             String supportAmt = String.valueOf(Arith.round(crowSupport.getSupportAmt()));    //支持金额
             String loanName = crow.getLoanName();  //项目名称
             String supportTime = DateUtil.date2Str(crowSupport.getCompleteTime(), DateUtil.CN_DATE_FORMAT);  //支持时间
             
             //查询项目方信息
             UserModel loanUser = this.userDao.findByUserId(crow.getLoanUser());
             String loanUserType = loanUser.getMemberType();
             if("0".equals(loanUserType)){  //个人会员、
                 CreatePersonalMember person = createPersonalMemberDao.selectByUserId(loanUser.getUserId());
                 loanUserName = loanUser.getRealName();
                 loanUserCertificateNumber = loanUser.getCertificateNo();
                 loanUserPhone = loanUser.getMobile();
             
             }else if("1".equals(loanUserType)){  //企业会员
                 CreateEnterpriseMember enterprise = createEnterpriseMemberDao.selectByUserId(loanUser.getUserId());
                 loanUserName = enterprise.getEnterpriseName();
                 loanUserCertificateNumber = enterprise.getLicenseNo(); 
                 if("".equals(loanUserCertificateNumber) || loanUserCertificateNumber==null){
                     loanUserCertificateNumber = enterprise.getOrganizationNo();
                 }        
                 loanUserPhone = loanUser.getMobile();
             }
             //查询当前投资人信息
             UserModel investUser = this.userDao.findByUserId(crowSupport.getSupportUser());
             String investorUserType =  investUser.getMemberType();
             if("0".equals(investorUserType)){  //个人会员
                 CreatePersonalMember person = createPersonalMemberDao.selectByUserId(investUser.getUserId());
                 supportUserName = loanUser.getRealName();
                 supportUserCertificateNumber = investUser.getCertificateNo();
                 supportUserPhone = investUser.getMobile();
             }else if("1".equals(investorUserType)){  //企业会员
                 CreateEnterpriseMember enterprise = createEnterpriseMemberDao.selectByUserId(investUser.getUserId());
                 supportUserName = enterprise.getEnterpriseName();
                 supportUserCertificateNumber = enterprise.getLicenseNo(); 
                 if("".equals(supportUserCertificateNumber) || supportUserCertificateNumber==null){
                     supportUserCertificateNumber = enterprise.getOrganizationNo();
                 }        
                 supportUserPhone = investUser.getMobile();
             }
             
             //还款计划列表
             List<Map<String, Object>> repayPlanList = new ArrayList<Map<String,Object>>();
             //出借人
             List<Map<String, Object>> investors = new ArrayList<Map<String,Object>>();
             if(loanNo!=null){
                  //借款的合同详细信息
                  loanDetail =this.contractTemplateDao.select4borrowContract(loanNo);
                  if (loanDetail!=null) {
                      //合同编号
                      contractNo = (String)loanDetail.get("contractNo");
                      //合同模版编号
                      contractTplNo = (String)loanDetail.get("contractTplNo");
                  }else{
                    //合同编号
                      contractNo = loanNo;
                      //合同模版编号
                      contractTplNo = loanNo;
                  }
                  //查询合同模板
                  Map<String,Object> map = this.contractTemplateDao.selectContractByLoanNo(contractTplNo);
                  content = map.get("templateContent").toString();
              } 
             
             content = content.replace("#{loanUserName}", loanUserName);
             content = content.replace("#{loanUserCertificateNumber}", loanUserCertificateNumber);
             content = content.replace("#{loanUserPhone}", loanUserPhone);
             content = content.replace("#{supportUserName}", supportUserName);
             content = content.replace("#{supportUserCertificateNumber}", supportUserCertificateNumber);
             content = content.replace("#{supportUserPhone}", supportUserPhone);
             content = content.replace("#{supportAmt}", supportAmt);
             content = content.replace("#{loanName}", loanName);
             content = content.replace("#{supportTime}", supportTime==null?"":supportTime);
             
            //替换合同模板中的内容
            body = dealContractTemplateContent(content).replace("&nbsp;", "&#160;");
            
            body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body style=\"font-family:'SimSun'\">"
                    + body + "</body></html>";
            //========================电子签章合同生成============================
            Document doc = new Document(PageSize.A4);
            PdfWriter pw = PdfWriter.getInstance(doc,new FileOutputStream(tempInFilePath));
            doc.open();
            HTMLUtils.bfCN=BaseFont.createFont(ContractUtils.class.getClassLoader().getResource("")+"SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            XMLWorkerHelper.getInstance().parseXHtml(pw,doc,new StringReader(body));
            //获取 pdf的总页数
            pageNum = pw.getPageNumber();
            doc.close();
            logger.info("==============开始调用签章服务器开始======================================");
            //将文件转换为base64未的字符串
            File signFile = new File(tempInFilePath);
            String signDoc = FileUtils.fileToBase64Str(signFile);
            
            Map<String,String> params = new HashMap<String,String>();
            Signature signature = new Signature();
            signature.setDoc(signDoc);
            signature.setPageNum(String.valueOf(pageNum));
            signature.setPositionX(200);
            signature.setPositionY(200);
            signature.setSignType("1");  //关键字签署
            signature.setKeyword("丙方签字/盖章");
            
            String requestStr = JsonHelper.getStrFromObject(signature);
            params.put("productCode",FbdCoreConstants.signature_product_code);
            params.put("signature", requestStr);
            logger.info("签章请求参数-params："+params);
            String result = HttpRequestUtils.callHttpPOST(FbdCoreConstants.signature_server_url+"tsaSignApi/signContract.html", params);
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
            if(!Boolean.parseBoolean(resultMap.get("success").toString())){
                throw new ApplicationException(resultMap.get("msg").toString());
            }
            String pdfDoc = resultMap.get("doc").toString();
            FileUtils.createPDF(pdfDoc, tempOutFilePath);
            
            logger.info("==============开始调用签章服务器结束======================================");
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("生成合同失败！");
        }
        return pageNum;
    }
    
    
    
    /**
     * 隐藏手机号
     * @param investor
     * @param isWeb
     * @return
     */
    private static String getMobile(String mobile, boolean isWeb){
        if(!isWeb || StringUtil.isEmpty(mobile)){
            return mobile;
        }
        int len = mobile.length();
        return mobile.substring(0, 3) + "*******" + mobile.substring(len - 1);
    }
    
    /**
     * 隐藏用户地址
     * @param investor
     * @param isWeb
     * @return
     */
    private static String getHomeAddress(String homeAddress, boolean isWeb){
        if(!isWeb || StringUtil.isEmpty(homeAddress)){
            return homeAddress;
        }
        int len = homeAddress.length();
        if(len <= 5 && len >= 3){
            return homeAddress.substring(0, 1) + "***" + homeAddress.substring(len - 1);
        } else if (len > 5){
            return homeAddress.substring(0, 3) + "***" + homeAddress.substring(len - 1);
        } else {
            return "***";
        }
    }
    
    /**
     * 隐藏用户名信息
     * @param investor
     * @param isWeb
     * @return
     */
    private static String getInvestor(String investor, boolean isWeb){
        if(!isWeb || StringUtil.isEmpty(investor)){
            return investor;
        }
        int len = investor.length();
        return investor.substring(0, 2) + "***" + investor.substring(len - 1);
    }
    /**
     * 隐藏借款人姓名
     * @param investor
     * @param isWeb
     * @return
     */
    private static String getRealName(String realName, boolean isWeb){
        if(!isWeb || StringUtil.isEmpty(realName)){
            return realName;
        }
        return realName.substring(0, 1) + "**";
    }
    /**
     * 隐藏借款人证件号
     * @param investor
     * @param isWeb
     * @return
     */
    private static String getCertificateNo(String certificateNo, boolean isWeb){
        if(!isWeb || StringUtil.isEmpty(certificateNo)){
            return certificateNo;
        }
        int len = certificateNo.length();
        return certificateNo.substring(0, 1) + "****************" + certificateNo.substring(len - 1);
    }
    
 
}
