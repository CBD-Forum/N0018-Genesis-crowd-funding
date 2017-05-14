package com.fbd.web.app.fileupload.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.app.fileupload.service.IFileUploadService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;

@Controller
@Scope("prototype")
@RequestMapping("/fileUpload")
public class FileUploadAction extends BaseAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private static final Logger logger = LoggerFactory.getLogger(FileUploadAction.class);
    
    
	@Resource
    private IFileUploadService fileUploadService;  
    
    /**
     * Description: 上传资料
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/uploadFile.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> uploadOrgLoanReceiveProve(@RequestParam("file") MultipartFile file, HttpServletRequest request,String parentId,String type) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        System.out.println("parentId:"+parentId);
        
        System.out.println("type:"+type);
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_AUTH_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".doc"); 
            allowSuffix.add(".docx");
            allowSuffix.add(".pdf");
            allowSuffix.add(".pdf");
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            allowSuffix.add(".xls");
            allowSuffix.add(".xlsx");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, "上传类型必须是doc,docx,pdf,jpg,png,gif,jpeg,xls,xlsx中的一种");
                return resultMap;
            }
            long size = file.getSize();
            logger.info("========>上传文件大小："+size+"========================");
            int mbSize = (int) (size/1024/2014);
            if(mbSize>30){
            	resultMap.put(SUCCESS,false);
                resultMap.put(MSG, "上传文件大小不能超过30MB！");
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String fileUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
            	//保存文件
            	FileUploadModel model = new FileUploadModel();
            	model.setFileName(fileName);
            	model.setFileExtension(suffix);
            	model.setFileUrl(fileUrl);
            	model.setParentId(parentId);
            	model.setType(type);
            	FileUploadModel returnModel = this.fileUploadService.save(model);
                resultMap.put("id",returnModel.getId());
                resultMap.put("fileName",fileName);
                resultMap.put("fileUrl",fileUrl);
                resultMap.put(SUCCESS,true);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,"上传失败");
            }
        } catch (Exception e) {  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"上传失败"+e.getMessage());
        }  
        return resultMap;   
    }  
    
    
    
    /**
     * 手机端上传图片
     * @param images
     * @param request
     * @param parentId
     * @param type
     * @param fileName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("restriction")
	@RequestMapping(value = "/uploadImageForBase64.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> uploadImageForBase64(String images, HttpServletRequest request,String parentId,String type,String fileName) throws Exception {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
            String path = "image/"+DateUtil.date2Str(new Date(), "yyyyMMdd");  
            byte[] buffer = new BASE64Decoder().decodeBuffer(images);  
            String basePath =  request.getSession().getServletContext().getRealPath(path);
            String suffix= ".jpg";
            String picId=PKGenarator.getId();
            String tempFileName=picId+suffix;
            File targetFile = new File(basePath, tempFileName);  
            if(!targetFile.exists()){  
                targetFile.mkdirs();  
            }  
            //保存在文件服务器上
            InputStream is = new ByteArrayInputStream(buffer); 
            boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, tempFileName,is);
            if (uploadResult) {
            	String fileUrl = path+"/"+tempFileName;
            	//保存文件
            	FileUploadModel model = new FileUploadModel();
            	model.setFileName(fileName);
            	model.setFileExtension(suffix);
            	model.setFileUrl(fileUrl);
            	model.setParentId(parentId);
            	model.setType(type);
            	FileUploadModel returnModel = this.fileUploadService.save(model);
                resultMap.put("id",returnModel.getId());
                resultMap.put("fileName",fileName);
                resultMap.put("fileUrl",fileUrl);
                resultMap.put("url",fileUrl);
                resultMap.put("state", "SUCCESS");
                resultMap.put(SUCCESS,true);
            }else{
                resultMap.put("state", "ERROR");
                resultMap.put(SUCCESS,false);
            }
    	}catch(Exception e){
    		resultMap.put("state", "ERROR");
    		resultMap.put("success", "false");
    	}
        return resultMap;
    }
 
    
    /**
     * 删除文件
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeFile.html", method = RequestMethod.POST) 
    public @ResponseBody Map<String,Object> removeFile(String id){
    	Map<String,Object> result = new HashMap<String,Object>();
    	try{
    		this.fileUploadService.deleteModel(id);
    		result.put(SUCCESS,true);
    	}catch(Exception e){
    		result.put(SUCCESS,false);
    	}
    	return result;
    }
}
