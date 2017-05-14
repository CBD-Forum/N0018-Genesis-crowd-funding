package com.fbd.web.app.crowdfunding.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressFilesDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressFilesModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.crowdfunding.service.CrowdfundProgressService;


@Service(value="crowdfundProgressService")
public class CrowdfundProgressServiceImpl implements CrowdfundProgressService {

	
	@Resource
	private ICrowdfundingProgressDao crowdfundingProgressDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
	@Resource
	private ICrowdfundingProgressFilesDao crowdfundingProgressFilesDao;
  
	/**
	 * 分页查询
	 */
    public SearchResult<CrowdfundingProgressModel> getProgessList(CrowdfundingProgressModel model){
       
    	SearchResult<CrowdfundingProgressModel> result = new SearchResult<CrowdfundingProgressModel>();
        result.setRows(this.selectDetailList(model));
        result.setTotal(this.crowdfundingProgressDao.selectCount(model));
        return result;
    }
    
    
	/**
	 * 分页查询
	 */
    public SearchResult<Map<String,Object>> getProgessPageList(CrowdfundingProgressModel model){
       
    	SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.selectDetailPageList(model));
        result.setTotal(this.crowdfundingProgressDao.selectCount(model));
        return result;
    }
  
    
    
    private List<CrowdfundingProgressModel> selectDetailList(CrowdfundingProgressModel model){
    	List<Map<String,Object>> list = this.crowdfundingProgressDao.getList(model);
    	List<CrowdfundingProgressModel> resultList = new ArrayList<CrowdfundingProgressModel>();
    	
    	for(Map<String,Object> item :list){
    		String id = item.get("id").toString();
    		CrowdfundingProgressModel model1 = this.selectDetailById(id);
    		resultList.add(model1); 
    	}
    	return resultList;
    }
    
    
    private List<Map<String,Object>> selectDetailPageList(CrowdfundingProgressModel model){
    	List<Map<String,Object>> list = this.crowdfundingProgressDao.getList(model);
    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
    	
    	for(Map<String,Object> item :list){
    		String id = item.get("id").toString();
    		CrowdfundingProgressFilesModel model1 = new CrowdfundingProgressFilesModel();
    		model1.setType("img");
    		model1.setParentId(id);
    	    List<CrowdfundingProgressFilesModel> imgFileList = this.crowdfundingProgressFilesDao.selectList(model1);
    	    item.put("imgFileList", imgFileList);
    	    resultList.add(item);
    	}
    	return resultList;
    }
	
	 
 
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	public CrowdfundingProgressModel selectDetailById(String id){
		
		CrowdfundingProgressModel model = this.crowdfundingProgressDao.selectByPrimaryKey(id);
		CrowdfundingProgressFilesModel model1 = new CrowdfundingProgressFilesModel();
		model1.setType("img");
		model1.setParentId(id);
	    List<CrowdfundingProgressFilesModel> imgFileList = this.crowdfundingProgressFilesDao.selectList(model1);
		
	    CrowdfundingProgressFilesModel model2 = new CrowdfundingProgressFilesModel();
		model2.setType("vedio");
		model2.setParentId(id);
	    List<CrowdfundingProgressFilesModel> vedioFileList = this.crowdfundingProgressFilesDao.selectList(model2);
	    model.setImgFileList(imgFileList);
	    model.setVedioFileList(vedioFileList);
		return model;
	}
	
	
	@Override
	public void save(CrowdfundingProgressModel model,String opeartor) {
		
		String loanNo = model.getLoanNo();
		CrowdfundingModel crowdfundingModel = this.crowdfundingDao.getByloanNo(loanNo);
		if(crowdfundingModel==null){
			throw new ApplicationException("项目编号不存在！");
		}
		String id = PKGenarator.getId();
		model.setId(id);
		model.setEnterUser(opeartor);
		model.setEnterTime(new Date());
		model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
		this.crowdfundingProgressDao.save(model);
		
		String imgFiles = model.getImgFiles();
		if(imgFiles!=null && !"".equals(imgFiles)){
			String imgs[] = imgFiles.split(",");
			for(int i=0;i<imgs.length;i++){
				String url = imgs[i];
				if(url==null || "".equals(url)){
					continue;
				}
				CrowdfundingProgressFilesModel imgFile = new CrowdfundingProgressFilesModel();
				imgFile.setId(PKGenarator.getId());
				imgFile.setParentId(id);
				imgFile.setType("img");
				imgFile.setUrl(url);
				imgFile.setUpdateTime(new Date());
				imgFile.setCreateTime(new Date());
				this.crowdfundingProgressFilesDao.save(imgFile);
			}
		}
		
		
		String vedioFiles = model.getVedioFiles();
		if(vedioFiles!=null && !"".equals(vedioFiles)){
			String vedios[] = vedioFiles.split(",");
			for(int i=0;i<vedios.length;i++){
				String url = vedios[i];
				if(url==null || "".equals(url)){
					continue;
				}
				CrowdfundingProgressFilesModel imgFile = new CrowdfundingProgressFilesModel();
				imgFile.setId(PKGenarator.getId());
				imgFile.setParentId(id);
				imgFile.setType("vedio");
				imgFile.setUrl(url);
				imgFile.setUpdateTime(new Date());
				imgFile.setCreateTime(new Date());
				this.crowdfundingProgressFilesDao.save(imgFile);
			}
		}
		}



	@Override
	public Map<String, Object> selectList(CrowdfundingProgressModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
 
}
