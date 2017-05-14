/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CommonAction.java 
 *
 * Created: [2014-12-13 下午4:42:10] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.common.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fbd.core.base.BaseAction;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.seo.service.ISeoService;

import sun.misc.BASE64Decoder;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:页面跳转公共action
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/common")
public class CommonAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4552343571666176835L;
    
    @Resource
    private ISeoService seoService;

    @RequestMapping(value = "{pageName}.html", method = RequestMethod.GET)  
    public ModelAndView goToPage(@PathVariable("pageName") String pageName){
        Map<String, Object> result  = new HashMap<String, Object>();
        return new ModelAndView(pageName, result);
    }
    
    /**
     * Description: 通过FTP上传到文件服务器上
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-28 下午3:08:54
     */
    @RequestMapping(value = "/uploadByFTP.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> uploadFileByFTP(HttpServletRequest request,String path,String fileName) throws Exception {
        String errorMsg = "上传图片未成功，请检查图片格式和大小!";
        String basePath =  request.getSession().getServletContext().getRealPath("/");
        FileInputStream in=new FileInputStream(new File(basePath+path+"/"+fileName));  
        boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName, in);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (uploadResult) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, errorMsg);
        }
        return resultMap;
    }
    
    
    
    
    /**
     * Description: 上传图片(使用base64上传图片)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author zhangweilong 
     * Create Date: 2015-3-2 下午5:42:51
     */
    
    @SuppressWarnings("restriction")
	@RequestMapping(value = "/uploadImageForBase64.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> uploadImageForBase64(String images, HttpServletRequest request) throws Exception {
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
            String path = "image/"+DateUtil.date2Str(new Date(), "yyyyMMdd");  
            byte[] buffer = new BASE64Decoder().decodeBuffer(images);  
            String basePath =  request.getSession().getServletContext().getRealPath(path);
            String suffix= ".jpg";
            String picId=PKGenarator.getId();
            String fileName=picId+suffix;
            File targetFile = new File(basePath, fileName);  
            if(!targetFile.exists()){  
                targetFile.mkdirs();  
            }  
            //保存在文件服务器上
            InputStream is = new ByteArrayInputStream(buffer); 
            boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName,is);
            if (uploadResult) {
                resultMap.put("url",path+"/"+fileName);
                resultMap.put("state", "SUCCESS");
            }else{
                resultMap.put("state", "ERROR");
            }
    	}catch(Exception e){
    		resultMap.put("state", "ERROR");
    		resultMap.put("success", "false");
    	}
        return resultMap;
    }
    
    
    
    /**
     * Description: 获取临时项目编号
     * @param 
     * @return void
     * @throws 
     * Create Date: 2015-2-28 下午3:08:54
     */
    @RequestMapping(value = "/getTempLoanNo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getTempLoanNo(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS,true);
        resultMap.put("tempLoanNo",PKGenarator.getLoanId());
        return resultMap;
    }
    
    /**
     * Description: 获取系统时间
     * @param 
     * @return void
     * @throws 
     * Create Date: 2015-2-28 下午3:08:54
     */
    @RequestMapping(value = "/getSystemTime.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getSystemTime(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS,true);
        resultMap.put("systemTime",new Date());
        return resultMap;
    }
    
    /**
     * Description: 获取临时ID
     * @param 
     * @return void
     * @throws 
     * Create Date: 2015-2-28 下午3:08:54
     */
    @RequestMapping(value = "/getTempId.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getTempId(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS,true);
        resultMap.put("tempId",PKGenarator.getId());
        return resultMap;
    }
    
}
