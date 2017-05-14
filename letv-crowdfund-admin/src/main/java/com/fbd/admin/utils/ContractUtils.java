package com.fbd.admin.utils;

import java.io.StringReader;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;

import com.fbd.core.app.contract.model.ContractTemplateModel;
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
    
    public static String dealContractTemplateContent(String contractContent){
        Pattern pattern = Pattern.compile("[#{](.*?)[}]");
        return pattern.matcher(contractContent).replaceAll("___");
    }
    
	/**
	 * 默认请求来自后台(不隐藏投资人和借款人信息)
	 * @param response
	 * @param loanNo
	 */
    public static void borrowContract(HttpServletResponse response,String contractNo){
    	borrowContract(response, contractNo, false);
    }
    
    /**
     * 下载借款合同PDF
     * @param response
     * @param loanNo
     * @param type 请求来源，默认后台；  web：前台，admin：后台
     */
	public static void borrowContract(HttpServletResponse response, String contractNo, boolean isWeb) {
		HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        //合同的模版内容
        String content = ""; 
        
        if (contractNo!=null && !"".equals(contractNo)) {
            //合同的详细信息
            ContractTemplateModel contractTemplate = sqlSession.selectOne(mapperSpace+"selectByCode", contractNo);
            //合同的模版内容
            content = contractTemplate.getTemplateContent();
        }
        
        String body = dealContractTemplateContent(content).replace("&nbsp;", "&#160;");
        body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body style=\"font-family:'SimSun'\">"
                + body + "</body></html>";

        try {
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
            
        }
	}
}
