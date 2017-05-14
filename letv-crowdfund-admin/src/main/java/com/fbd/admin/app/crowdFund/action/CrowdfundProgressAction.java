package com.fbd.admin.app.crowdFund.action;

import java.io.File;
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

import com.fbd.admin.app.crowdFund.service.CrowdfundProgressService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundProgress")
public class CrowdfundProgressAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CrowdfundProgressService crowdfundProgressService;
	
	 
    /**
     * 
     * Description: 查询项目进展
     * @param 
     * @return Map<String,Object>
     * Create Date: 2015-3-28 下午3:43:10
     */
    @RequestMapping(value = "/getProgessList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model) {
           return this.crowdfundProgressService.getProgessList(model);
    }
	   /**
     * 
     * Description: 项目进度保存
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:44:32
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> saveModel(CrowdfundingProgressModel model,String loanType){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
        	crowdfundProgressService.save(model,operator);
        	resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "保存失败");
        }
        return resultMap;
    }	
    
    
    
    
	 /**
	  * 
	  * Description: 删除项目进度
	  *
	  * @param 
	  * @return Map<String,Object>
	  * @throws 
	  * @Author haolingfeng
	  * Create Date: 2015-6-2 下午7:44:32
	  */
	 @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
	 public @ResponseBody  Map<String, Object> remove(String id){
	     Map<String, Object> resultMap = new HashMap<String, Object>();
	     try { 
	     	crowdfundProgressService.deleteprogress(id);
	     	resultMap.put(SUCCESS, true);
	     } catch (ApplicationException e) {
	         e.printStackTrace();
	         resultMap.put(SUCCESS, false);
	         resultMap.put(MSG, e.getMessage());
	     }catch (Exception e) {
	         e.printStackTrace();
	         resultMap.put(SUCCESS, false);
	         resultMap.put(MSG, "删除失败");
	     }
	     return resultMap;
	 }	
	
	
	
	
 /**
  * Description: 上传视频
  * @param 
  * @return Map<String,Object>
  * @throws 
  * @Author dongzhongwei
  * Create Date: 2015-3-2 下午5:42:51
  */
	 @RequestMapping(value = "/uploadVideo.html", method = RequestMethod.POST)
	 public @ResponseBody Map<String, Object> uploadVideo(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
	     Map<String, Object> resultMap = new HashMap<String, Object>();
	     String path = "video/"+DateUtil.date2Str(new Date(), "yyyyMMdd");  
	     String basePath =  request.getSession().getServletContext().getRealPath(path);
	     String fileName = file.getOriginalFilename();
	     String suffix=fileName.substring(fileName.indexOf("."));
	     
	     List<String> allowSuffix = new ArrayList<String>();
	     allowSuffix.add(".mp4");
	     allowSuffix.add(".m4v");
	     allowSuffix.add(".mov");
	     if (!allowSuffix.contains(suffix.toLowerCase())) {
	         resultMap.put(SUCCESS,false);
	         resultMap.put(MSG, "上传类型必须是.mp4,m4v,mov中的一种");
	         return resultMap;
	     }
	     
	     
	     String picId=PKGenarator.getId();
	     fileName=picId+suffix;
	     File targetFile = new File(basePath, fileName);  
	     if(!targetFile.exists()){  
	         targetFile.mkdirs();  
	     }  
	     //保存在文件服务器上
	     boolean uploadResult = FTPManagerFactory.getFtpManager().uploadFile(path, fileName, file.getInputStream());
	     if (uploadResult) {
	         resultMap.put("original", file.getOriginalFilename());
	         resultMap.put("size", file.getSize());
	         resultMap.put("id",picId);
	         resultMap.put("type", suffix);
	         resultMap.put("url", path+"/"+fileName);
	         resultMap.put("title", fileName);
	         resultMap.put("state", "SUCCESS");
	         resultMap.put(SUCCESS,true);
	     }else{
	         resultMap.put("state", "ERROR");
	         resultMap.put(SUCCESS,false);
	         resultMap.put(MSG, "上传FTP异常");
	     }
	     return resultMap;
	 }	
	
	 /**
	  * 
	  * Description: 查看进度
	  *
	  * @param 
	  * @return Map<String,Object>
	  * @throws 
	  * @Author haolingfeng
	  * Create Date: 2015-6-2 下午7:44:32
	  */
	 @RequestMapping(value = "/selectDetailById.html", method = RequestMethod.POST)
	 public @ResponseBody  CrowdfundingProgressModel  selectDetailById(String id){
		 CrowdfundingProgressModel model = null;
	     try { 
	         model = this.crowdfundProgressService.selectDetailById(id);
	     }catch (Exception e) {
	       e.printStackTrace();
	     }
	     return model;
	 }		
}
