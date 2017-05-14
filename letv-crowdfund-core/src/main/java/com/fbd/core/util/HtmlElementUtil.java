package com.fbd.core.util;

import java.net.URLEncoder;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * Company: jdp2p <br/>
 * Copyright: Copyright (c)2013 <br/>
 * Description: html 元素工具，例如打包form表单之类
 * 
 * @author: wangzhi
 * @version: 1.0 Create at: 2014-4-3 下午4:09:33
 * 
 *           Modification History: <br/>
 *           Date Author Version Description
 *           ------------------------------------------------------------------
 *           2014-4-3 wangzhi 1.0
 */
public class HtmlElementUtil {
	/**
	 * 构造一个在页面加载完成自动提交的表单，可用于post表单数据（请求支付）等等。
	 * 
	 * @param params
	 *            提交参数
	 * @param actionUrl
	 *            form action url
	 * @param charset
	 *            mete content charset
	 */
	public static String createAutoSubmitForm(Map<String, String> params,
			String actionUrl, String charset) {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>跳转......</title>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ charset + "\">");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<form action=\"" + actionUrl
				+ "\" method=\"post\" id=\"frm1\" style=\"display:none;\">");
		for (String key : params.keySet()) {
			sb.append("<input type=\"hidden\" name=" + key + " value=\""
					+ StringEscapeUtils.escapeHtml(params.get(key)) + "\">");
		}
		sb.append("</form>");
		sb.append("<script type=\"text/javascript\">document.getElementById(\"frm1\").submit()</script>");
		sb.append("</body>");
		return sb.toString();
	}
	/**
     * 构造一个在页面加载完成自动提交的表单，可用于post表单数据（请求支付）等等。
     * 
     * @param params
     *            提交参数
     * @param actionUrl
     *            form action url
     * @param charset
     *            mete content charset
     */
    public static String createAutoSubmitForm(Map<String, String> params,
            String actionUrl) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>跳转......</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<form action=\"" + actionUrl
                + "\" method=\"post\" id=\"frm1\" style=\"display:none;\">");
            for (String key : params.keySet()) {
                sb.append("<input type=\"hidden\" name=" + key + " value=\""
                        +  params.get(key) + "\">");
            }
        sb.append("</form>");
        sb.append("<script type=\"text/javascript\">document.getElementById(\"frm1\").submit()</script>");
        sb.append("</body>");
        return sb.toString();
    }
	
	/**
	 * 
	 * Description: 错误信息页面
	 *
	 * @param 
	 * @return String
	 * @throws 
	 * @Author haolingfeng
	 * Create Date: 2015-3-16 下午12:15:07
	 */
	public static String createErrorPage(String errorInfo) {
	    String charset = "utf-8";
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>跳转......</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                + charset + "\">");
        sb.append("<script type=\"text/javascript\">");
        sb.append("var path = \"<%=path%>\";");
        sb.append("</script>");
        sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"<%=path%>/style/main.css\" />");
        sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"<%=path%>/style/common.css\" />");
        sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"<%=path%>/style/style.css\" />");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<div class=\"syserror_div\">");
        sb.append("<div class=\"syserror_con\">");
        sb.append("<span>"+errorInfo+"</span>");
        sb.append("<img src=\"<%=path%>/images/error_1.jpg\"/>");
        sb.append("<div class=\"errorBtn\">");
        sb.append("<a href=\"<%=path%>/common/index.html\" class=\"goBack\">返回首页</a>");
        sb.append("<a href=\"<%=path%>/common/loanList.html\">查看项目</a>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        return sb.toString();
    }
	
	
	public static String genSubmitUril(String requestUrl,Map<String, String> params)
    {
        StringBuilder sbURL = new StringBuilder();
        sbURL.append(requestUrl+"?");
        sbURL.append("version=" + params.get("version"));
        sbURL.append("&pay_type=" + params.get("pay_type"));
        sbURL.append("&pay_code=" + params.get("pay_code"));
        sbURL.append("&agent_id=" + params.get("agent_id"));
        sbURL.append("&agent_bill_id=" + params.get("agent_bill_id"));
        sbURL.append("&pay_amt=" + params.get("pay_amt"));
        if (params.get("pay_flag") != null && params.get("pay_flag").trim().length() > 0)
        {
            sbURL.append("&pay_flag=" +params.get("pay_flag"));
        }
        sbURL.append("&notify_url=" +  params.get("notify_url"));
        sbURL.append("&return_url=" +  params.get("return_url"));
        sbURL.append("&user_ip=" +  params.get("user_ip"));
        sbURL.append("&agent_bill_time=" +  params.get("agent_bill_time"));
        sbURL.append("&goods_name=" + URLEncoder.encode(params.get("goods_name")));
        sbURL.append("&remark=" + URLEncoder.encode(params.get("remark")));
        if (params.get("is_test") != null && params.get("is_test").trim().length() > 0)
        {
            sbURL.append("&is_test=" + params.get("is_test"));
        }
        sbURL.append("&sign=" + params.get("sign"));
        return createAutoSubmitForm(sbURL.toString(),"utf-8");
    }
	
	public static String createAutoSubmitForm(String actionUrl, String charset) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>跳转......</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                + charset + "\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<form action=\"" + actionUrl
                + "\" method=\"post\" id=\"frm1\" style=\"display:none;\">");
        sb.append("</form>");
        sb.append("<script type=\"text/javascript\">document.getElementById(\"frm1\").submit()</script>");
        sb.append("</body>");
        return sb.toString();
    }
}
