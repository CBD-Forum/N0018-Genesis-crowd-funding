package com.fbd.core.common.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mybatis.spring.SqlSessionTemplate;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.core.web.MyRequestContextHolder;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.HTMLUtils;


public class ContractUtils {
    
    private static SqlSessionTemplate sqlSession = (SqlSessionTemplate)SpringUtil.getBean("sqlSessionTemplate");
    
    private static final String mapperSpace = "com.fbd.core.app.contract.model.ContractTemplateModelMapper.";
    private static final String LOANMODEL_MAPPER_MAPPERSPACE = "com.fbd.core.app.loan.model.LoanModelMapper.";
    
    private static final String nodeType_mapperSpace = "com.fbd.core.app.node.model.NodeTypeModelMapper.";
    
    public static String dealContractTemplateContent(String contractContent){
        Pattern pattern = Pattern.compile("[#{](.*?)[}]");
        return pattern.matcher(contractContent).replaceAll("___");
    }
    
	/**
	 * 默认请求来自后台(不隐藏投资人和借款人信息)
	 * @param response
	 * @param loanNo
	 */
    public static void borrowContract(HttpServletResponse response,String loanNo){
    	borrowContract(response, loanNo, false);
    }
    
    /**
     * 下载借款合同PDF
     * @param response
     * @param loanNo
     * @param type 请求来源，默认后台；  web：前台，admin：后台
     */
	public static void borrowContract(HttpServletResponse response, String loanNo, boolean isWeb) {
		try {
			HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
	        
	        //出借人
	        List<Map<String, Object>> investors = new ArrayList<Map<String,Object>>();
	        
	        //还款计划列表
	        List<Map<String, Object>> repayPlanList = new ArrayList<Map<String,Object>>();
	        
	        //合同的模版内容
	        String content = ""; 
	        //借款的合同详细信息
	        Map<String, Object> loanDetail = null;
	        //合同编号
	        String contractNo = "";
	        //合同模版编号
	        String contractTplNo = "";
	        
	        if (loanNo!=null && !"".equals(loanNo)) {
	            
	            //借款的合同详细信息
	            loanDetail = sqlSession.selectOne(mapperSpace+"select4borrowContract", loanNo);
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
	            //合同的详细信息
	            ContractTemplateModel contractTemplate = sqlSession.selectOne(mapperSpace+"selectByCode", contractTplNo);
	            
	            //合同的模版内容
	            content = contractTemplate.getTemplateContent();
	            
	            //出借人信息
	            investors = sqlSession.selectList(mapperSpace+"selectInvestor4borrowContract", loanNo);
	            
	            //还款计划列表
	            Map<String, Object> param = new HashMap<String, Object>();
	            param.put("loanNo", loanNo);
	            repayPlanList = sqlSession.selectList(LOANMODEL_MAPPER_MAPPERSPACE+"getRepayPlans4Loan", param);
	            
	        }
	        
	        
	        //出借人表格
	        StringBuilder borrowTable = new StringBuilder();
	        borrowTable.append("<table style=\"margin-left: 0px auto; border-collapse: collapse; border: 0.5px solid rgb(178, 178, 178); width: 100%; \">");
	        borrowTable.append("<tr>");
	        borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">用户名</td>");
	        borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">投标来源</td>");
	        borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">借出金额(元)</td>");
	        borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">借款期限(月)</td>");
	        borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">应收本息(元)</td>");
	        borrowTable.append("</tr>");
	        for (Map<String, Object> map : investors) {
	            borrowTable.append("<tr>");
	            borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+getInvestor(map.get("investor").toString(), isWeb)+"</td>");
	            borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">普通投标</td>");
	            borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("investAmt").toString()+"</td>");
	            String loanTerm = "&nbsp;";
	            if (loanDetail!=null&&loanDetail.get("loanTerm")!=null) {
	                loanTerm = loanDetail.get("loanTerm").toString();
	            }
	            borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+loanTerm+"</td>");
	            borrowTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("repayAmt")+"</td>");
	            borrowTable.append("</tr>");
	        }
	        borrowTable.append("</table>");
	        
	        //获取还款列表
	        String repayPlanTable = getRepayPlanTableStr(repayPlanList);
	        
	        //替换
	        if (loanDetail != null) {
	            content = content
	            		.replace("#{contractNo}", contractNo).replace("#{borrowlist}", borrowTable.toString())
	            		.replace("#{borrow_name}", getRealName(loanDetail.get("realName").toString(), isWeb))
	                    .replace("#{borrow_ID}", getCertificateNo(loanDetail.get("certificateNo").toString(), isWeb))
	                    .replace("#{loan_name}", loanDetail.get("loanName").toString())
	                    .replace("#{loan_amt}", loanDetail.get("loanAmt").toString())
	                    .replace("#{loan_interest_rate}", loanDetail.get("interestRate").toString())
	                    .replace("#{loan_term}", loanDetail.get("loanTerm").toString())
	                    .replace("#{invest_start_time}", loanDetail.get("investStartTime").toString())
	                    .replace("#{last_repay_date}", loanDetail.get("lastRepayDate").toString())
	                    .replace("#{repay_start_date}", loanDetail.get("repayStartDate").toString())
	                    .replace("#{loan_repay_day}", loanDetail.get("repayDay").toString())
	                    .replace("#{repayPlanList}", repayPlanTable)
	                    
	                    .replace("#{homeAddress}", getHomeAddress(loanDetail.get("homeAddress").toString(), isWeb))//借款人地址
	                    .replace("#{mobile}", getMobile(loanDetail.get("mobile").toString(), isWeb))//借款人联系电话
	                    .replace("#{postCode}", getHomeAddress(loanDetail.get("postCode").toString(), isWeb))//借款人邮编
	                    .replace("#{contactName}", loanDetail.get("contactName").toString())//保证人
	                    .replace("#{comAddress}", loanDetail.get("comAddress").toString())//保证人 地址
	                    .replace("#{corporator}", loanDetail.get("corporator").toString())//法定人代表
	                    .replace("#{Cmobile}", loanDetail.get("Cmobile").toString())//担保公司 联系方式 
	                    .replace("#{schemaName}", loanDetail.get("schemaName").toString())//付息方式
	                    .replace("#{loanAmtDoubleUpper}", AmountUpperUtils.digitUppercase((Double)(loanDetail.get("loanAmtDouble"))))//借款金额大写
	                    .replace("#{cashTime}", DateUtil.date2Str((Date)loanDetail.get("cashTime"), DateUtil.DEFAULT_TIME_FORMAT))//还款时间
	                    ;
	        }
	        
	        String body = dealContractTemplateContent(content).replace("&nbsp;", "&#160;");
	        body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body style=\"font-family:'SimSun'\">"
	                + body + "</body></html>";

            HttpServletUtils.setFileDownloadHeader(request, response, contractNo+".pdf");
            response.setContentType("application/pdf;charset=UTF-8");
            ServletOutputStream sos = response.getOutputStream();
            
            Document document = new Document(PageSize.A4);
            PdfWriter pdfwriter = PdfWriter.getInstance(document,sos);
            document.open();

            // html文件
            StringReader contentReader = new StringReader(body);
         
            HTMLUtils.bfCN=BaseFont.createFont(ContractUtils.class.getClassLoader().getResource("")+"SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 方法一：默认参数转换
            
            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, contentReader);

            document.close();
            sos.close();
        } catch (Exception e) {
        	System.out.println("-------下载借款合同PDF 报错--------");
            e.printStackTrace();
        }
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
	
	/**
     * 拼还款计划列表串
     * @param repayPlanList
     * @return
     */
    private static String getRepayPlanTableStr(List<Map<String, Object>> repayPlanList){
        StringBuilder repayPlanTable = new StringBuilder();
        repayPlanTable.append("<table style=\"margin-left: 0px auto; border-collapse: collapse; border: 0.5px solid rgb(178, 178, 178); width: 100%; \">");
        repayPlanTable.append("<tr>");
        repayPlanTable.append("<td width=\"13%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">当前期数</td>");
        repayPlanTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">应还利息(元)</td>");
        repayPlanTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">应还本金(元)</td>");
        repayPlanTable.append("<td width=\"20%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">应还总额(元)</td>");
        repayPlanTable.append("<td width=\"27%\" style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">还款截止日</td>");
        repayPlanTable.append("</tr>");
        for (Map<String, Object> map : repayPlanList) {
        	repayPlanTable.append("<tr>");
        	repayPlanTable.append("<td style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("repayPeriod")+"</td>");
        	repayPlanTable.append("<td style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("repayInterest")+"</td>");
        	repayPlanTable.append("<td style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("repayPrincipal")+"</td>");
        	repayPlanTable.append("<td style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+map.get("repayAmt")+"</td>");
        	repayPlanTable.append("<td style=\"text-align:center;border: 0.5px solid rgb(178, 178, 178);\">"+DateUtil.date2Str((Date)map.get("repayDate"), DateUtil.DEFAULT_DATE_TIME_FORMAT)+"</td>");
        	repayPlanTable.append("</tr>");
        }
        repayPlanTable.append("</table>");
    	return repayPlanTable.toString();
    }
    
    /**
     * 下载借款合同PDF
     * @param response
     * @param nodeType
     */
	public static void downloadContract(HttpServletResponse response, String content,String code) {
		try {
			HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
	        String body = dealContractTemplateContent(content).replace("&nbsp;", "&#160;");
	        body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body style=\"font-family:'SimSun'\">"
	                + body + "</body></html>";
	        
	        
	        NodeTypeModel nodeType = sqlSession.selectOne(nodeType_mapperSpace+"selectByCode", code);

            HttpServletUtils.setFileDownloadHeader(request, response, nodeType.getName()+".pdf");
            response.setContentType("application/pdf;charset=UTF-8");
            ServletOutputStream sos = response.getOutputStream();
            
            Document document = new Document(PageSize.A4);
            PdfWriter pdfwriter = PdfWriter.getInstance(document,sos);
            document.open();

            // html文件
            StringReader contentReader = new StringReader(body);
         
            HTMLUtils.bfCN=BaseFont.createFont(ContractUtils.class.getClassLoader().getResource("")+"SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 方法一：默认参数转换
            
            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, contentReader);

            document.close();
            sos.close();
        } catch (Exception e) {
        	System.out.println("-------下载借款合同PDF 报错--------");
            e.printStackTrace();
        }
	}
}
