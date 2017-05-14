package com.fbd.admin.common.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fbd.admin.app.seo.service.ISeoTdkService;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.common.utils.SysConfigCache;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.core.util.report.util.ExcelReportUtil;
import com.fbd.core.util.securityCode.SecurityCode;
import com.fbd.core.util.securityCode.SecurityImage;
import com.fbd.core.util.spring.SpringUtil;

@Controller
public class CommonAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1288807841287928711L;
    
    @Resource
    private ISeoTdkService seoTdkService;

    /**
     * Description: 生成验证码图片
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-13 下午4:39:21
     */
    @RequestMapping(value = "/securityCodeImage.html", method = RequestMethod.GET)
    public void getSecurityCode(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) throws Exception {
        // 获取默认难度和长度的验证码
        String securityCode = SecurityCode.getSecurityCode();
        BufferedImage image = SecurityImage.createImage(securityCode);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(image, "JPEG", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        // 放入session中
        session.setAttribute("codeImage", securityCode);
    }
    
    /**
     * Description: 验证输入的验证码是否正确
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-13 下午4:39:40
     */
    @RequestMapping(value = "/checkSecurityCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkSecurityCode(String valiCode, HttpSession session) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (valiCode.equals(session.getAttribute("codeImage"))) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
       return resultMap;
    }
    
    /**
     * Description: 用于跳转页面
     *
     * @param 
     * @return ModelAndView
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-13 下午4:40:20
     */
    @RequestMapping(value = "{pageName}.html", method = RequestMethod.GET)  
    public ModelAndView goToPage(@PathVariable("pageName") String pageName,HttpServletRequest request){
        Map<String, Object> result  = new HashMap<String, Object>();
        Iterator<?> iter            = request.getParameterMap().entrySet().iterator(); 
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next(); 
            String key      = entry.getKey().toString();
            Object value    = request.getParameter(key);
            result.put(key,  value);
        }
        if (result.get("tdk")==null) {
            SeoTdkModel tdk=seoTdkService.getTDKBycode(pageName);
            if (tdk==null) {
                tdk = new SeoTdkModel();
            }
            if (StringUtils.isEmpty(tdk.getTitle())) {
                tdk.setTitle(SpringPropertiesHolder.getProperty("SEO.TDK.title"));
            }
            if (StringUtils.isEmpty(tdk.getDescription())) {
                tdk.setDescription(SpringPropertiesHolder.getProperty("SEO.TDK.description"));
            }
            if (StringUtils.isEmpty(tdk.getKeyword())) {
                tdk.setKeyword(SpringPropertiesHolder.getProperty("SEO.TDK.keyword"));
            }
            result.put("tdk", tdk);
        }
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
        String basePath =  request.getSession().getServletContext().getRealPath("/");
        FileInputStream in=new FileInputStream(new File(basePath+path+"/"+fileName));  
        boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName, in);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (uploadResult) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 上传图片
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 下午5:42:51
     */
    
    @RequestMapping(value = "/uploadImage.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String path = "image/"+DateUtil.date2Str(new Date(), "yyyyMMdd");  
        String basePath =  request.getSession().getServletContext().getRealPath(path);
        String fileName = file.getOriginalFilename();
        String suffix=fileName.substring(fileName.indexOf("."));
        String picId=PKGenarator.getId();
        fileName=picId+suffix;
        File targetFile = new File(basePath, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存到本服务器上
//        file.transferTo(targetFile);
        //保存在文件服务器上
        boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName, file.getInputStream());
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (uploadResult) {
            resultMap.put("original", file.getOriginalFilename());
            resultMap.put("size", file.getSize());
            resultMap.put("type", suffix);
            resultMap.put("url", path+"/"+fileName);
            resultMap.put("title", fileName);
            resultMap.put("state", "SUCCESS");
        }else{
            resultMap.put("state", "ERROR");
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/exportExcel.html", method = RequestMethod.GET)
    public void exportExcel(String excelId,HttpServletResponse response){
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.putAll(this.getRequestParam());
            //读取导出的xml
            String templateFilePath = "export_"+excelId+"_excel.xml";
            File templateFile = ResourceUtils.getFile("classpath:"+FbdCoreConstants.EXPORT_TEMP_PATH+templateFilePath);
            SAXReader reader = new SAXReader();    
            Document doc = reader.read(templateFile); 
            
            Element root = doc.getRootElement();
            String fileName = root.attributeValue("fileName");
            String seriveClassId = root.element("dataSource").attributeValue("serviceClassId");   
            String serviceMethod =  root.element("dataSource").attributeValue("serviceMethod");
            
            Object serviceClass = SpringUtil.getBean(seriveClassId);
            Class c = serviceClass.getClass();  
            Class types[] =new Class[1];  
            types[0]=Class.forName("java.util.Map"); 
            Method m = c.getMethod(serviceMethod,types);
            SearchResult<Map<String, Object>> resultMap = (SearchResult<Map<String, Object>>)m.invoke(serviceClass, param);
            
            HSSFWorkbook workbook = ExcelReportUtil.genExcelReport(templateFilePath, resultMap.getRows());
            OutputStream out = response.getOutputStream();
            //判断当前浏览器类型
            if("fireFox".equals((String)param.get("appCodeName"))){
            	response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
            }else{
            	response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls","UTF-8"));
            }
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            try {
                workbook.write(out);    
            } finally{
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 后台系统图片配置:logo、后台首页图片
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/uploadSysImage.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadSysImage(
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String imgType = request.getParameter("imgType");
		resultMap.put("result", false);
		if (StringUtil.isEmpty(imgType)) {
			resultMap.put("msg", "上传参数错误(imgType为空)");
			return resultMap;
		}
		String path = "";
		String fileName = "";
		try {

			path = "image_sys/" + imgType;
			String basePath = request.getSession().getServletContext().getRealPath(path);
			fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.indexOf("."));
			fileName = imgType + suffix;
			File targetFile = new File(basePath, fileName);
			File targetFiles = new File(basePath);
			if (targetFiles.exists()) {
				deleteFile(targetFiles);
			}
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存到本服务器上
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			resultMap.put("msg", "上传异常");
			return resultMap;
		}
		String url = path + "/" + fileName;
		
		//更新系统缓存
		SysConfigModel s = new SysConfigModel();
		s.setDisplayName("admin_"+imgType+"_url");
		s.setCode("/" + url);
		SysConfigCache.getInstance().update(s);
		
		resultMap.put("url", url);
		resultMap.put("result", true);
		resultMap.put("msg", "上传成功");
		return resultMap;
	}
	
	 /**
     * 删除文件夹下的所有文件
     * @param oldPath
     */
    public void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
	         File[] files = oldPath.listFiles();
	         for (File file : files) {
	        	 deleteFile(file);
	         }
         }else{
        	 oldPath.delete();
         }
    }
    
    /**
     * Description: 获取ID
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-28 下午3:08:54
     */
    @RequestMapping(value = "/getId.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getId(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS,true);
        resultMap.put("id",PKGenarator.getId());
        return resultMap;
    }
    
}
