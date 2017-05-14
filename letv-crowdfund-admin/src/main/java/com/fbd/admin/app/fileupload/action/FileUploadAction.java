package com.fbd.admin.app.fileupload.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.app.fileupload.service.IFileUploadService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtils;

@Controller
@Scope("prototype")
@RequestMapping("/fileUpload")
public class FileUploadAction extends BaseAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
                resultMap.put(SUCCESS,true);
                resultMap.put(MSG,fileUrl);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,"上传失败");
            }
        } catch (Exception e) {  
        	e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"上传失败"+e.getMessage());
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
