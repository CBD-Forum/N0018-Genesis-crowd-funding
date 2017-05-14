package com.fbd.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: HttpServlt工具类
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class HttpServletUtils {
    private static Logger logger = LoggerFactory
            .getLogger(HttpServletUtils.class);

    // -- Content Type 定义 --//
    public static final String TEXT_TYPE = "text/plain";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";
    public static final String CSV_TYPE = "text/csv";
    public static final String FILE_TYPE = "application/octet-stream";
    public static final String IMG_TYPE_JPG = "image/jpg";
    public static final String IMG_TYPE_JPEG = "image/jpeg";

    
    /**
     * Description: 判断请求是否为ajax请求
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:07
     */
    public static boolean isAjaxRequest(final HttpServletRequest request) {
        String ajaxRequest = request.getHeader("X-Requested-With");
        if (StringUtils.hasText(ajaxRequest)) {
            return ajaxRequest.equals("XMLHttpRequest");
        }
        return false;
    }

   
    /**
     * Description: 往response流中写入字符串
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:27
     */
    public static void outString(HttpServletResponse response, String str) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
    
    /**
     * Description: 往response流中写入字符串
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:27
     */
    public static void outString(HttpServletResponse response, String str,String charset) {
        response.setContentType("text/html;charset="+charset);
        PrintWriter out = null;
        try {
            System.out.println("后台异步打印SUCCESS");
            out = response.getWriter();
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
    
    
    /**
     * Description: 往response流中写入字符串
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:27
     */
    public static void printString(HttpServletResponse response, String str) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    
    /**
     * Description: 获取绝对路径
     *
     * @param 
     * @return String
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:43
     */
    public static String getRealyPath(HttpServletRequest request, String path) {
        return request.getSession().getServletContext().getRealPath(path);
    }

    
    /**
     * Description: 文件下载时设置头
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:12:58
     */
    public static void setFileDownloadHeader(HttpServletRequest request,
            HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String charset = request.getHeader("Accept-Charset");
            charset = charset == null || charset.length() == 0 ? request
                    .getCharacterEncoding() : charset.substring(0,
                    charset.indexOf(",") < charset.indexOf(";") ? charset.indexOf(",")
                            : charset.indexOf(";"));
            if (new BrowserInfo(request).isIE()) {
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + URLEncoder.encode(fileName, charset) + "\"");
                return;
            }
            String encodedFileName = new String(fileName.getBytes(charset),
                    "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + encodedFileName + "\"");
        } catch (UnsupportedEncodingException e) {
            logger.error("setFileDownloadHeader error", e);
        }
    }

    
    /**
     * Description: 获取IP地址
     *
     * @param 
     * @return String
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-19 上午11:13:15
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            // 这里主要是获取本机的ip,可有可无
            if (ipAddress.equals("127.0.0.1") || ipAddress.endsWith("0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                                                                                                                // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        // 或者这样也行,对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // return
        // ipAddress!=null&&!"".equals(ipAddress)?ipAddress.split(",")[0]:null;
        return ipAddress;
    }

    
    /** 
     * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
     * 
     * Description: 浏览器信息
     *
     * @author dongzhongwei
     * @version 1.0
     *
     */
    
    public static class BrowserInfo {
        private String browser;

        private String version;

        private HttpServletRequest request;

        public BrowserInfo(final HttpServletRequest request) {
            this.request = request;
            final String browserType = request.getHeader("User-Agent").toLowerCase();
            if (browserType != null) {
                if ((browserType.indexOf("msie") != -1)) {
                    browser = "ie";
                    final String tempStr = browserType.substring(
                            browserType.indexOf("msie"), browserType.length());
                    version = tempStr.substring(4, tempStr.indexOf(";"));
                } else if (browserType.indexOf("chrome") != -1) {
                    browser = "chrome";
                } else if (browserType.indexOf("firefox") != -1) {
                    browser = "firefox";
                } else if (browserType.indexOf("opera") != -1) {
                    browser = "opera";
                } else if (browserType.indexOf("safari") != -1) {
                    browser = "safari";
                }
                //判断是否是IE11：trident是IE9以后引入的一个字符串
                else if (browserType.indexOf("trident") > -1) {
                     int rvIndex=browserType.indexOf("rv");
                     String rvAfterStr=browserType.substring(rvIndex);
                     int rvAfterPoint=rvAfterStr.indexOf(".");
                     version=rvAfterStr.substring(3,rvAfterPoint);
                     browser = "ie";
                }
            }
        }

        @SuppressWarnings("unchecked")
        public Map<String, String> getHeaders() {
            Enumeration<String> nameEnumer = request.getHeaderNames();
            Map<String, String> headers = new HashMap<String, String>();
            while (nameEnumer.hasMoreElements()) {
                String key = nameEnumer.nextElement();
                headers.put(key, request.getHeader(key));
            }
            return headers;
        }

        public boolean isIE() {
            return browser != null && browser.equals("ie");
        }

        public boolean isFirefox() {
            return browser != null && browser.equals("firefox");
        }

        public boolean isChrome() {
            return browser != null && browser.equals("chrome");
        }

        public String getVersion() {
            return version;
        }

        public String createPostfix(final String filename) {
            if (isIE()) {
                return filename;
            } else {
                final int dotIndex = filename.lastIndexOf(".");
                final boolean dot = dotIndex != -1;
                final String s1 = dot ? filename.substring(0, dotIndex) : filename;
                final String s2 = dot ? filename.substring(dotIndex) : "";
                if (isFirefox()) {
                    return s1 + "_ff" + s2;
                }
            }
            return null;
        }
    }
}