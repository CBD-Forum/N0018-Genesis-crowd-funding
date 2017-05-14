package com.fbd.admin.app.crowdFund.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.crowdFund.service.CrowdfundProgressService;
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


@Service(value="crowdfundProgressService")
public class CrowdfundProgressServiceImpl implements CrowdfundProgressService {

	
	
	@Resource
	private ICrowdfundingProgressDao crowdfundingProgressDao;
	
	@Resource
	private ICrowdfundingProgressFilesDao crowdfundingProgressFilesDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
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
		model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
		model.setEnterTime(new Date());
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

	
	
	/**
	 * 删除进展
	 * @param id
	 */
	public void deleteprogress(String id){
		
		this.crowdfundingProgressDao.deleteByPrimaryKey(id);
		try{
			this.crowdfundingProgressFilesDao.deleteByParentId(id);
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException("删除失败!");
		}
		
	}
	
	/**
	 * 分页查询
	 */
    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingProgressDao.getList(model));
        result.setTotal(this.crowdfundingProgressDao.selectCount(model));
        return result;
    }
	
	
	/**
	 * 编辑项目进度
	 * @param model
	 */
	public void updateProgress(CrowdfundingProgressModel model){
		
	}
	
	
	/**
	 * 查询列表
	 * @param model
	 * @return
	 */
	public Map<String,Object>selectList(CrowdfundingProgressModel model){
		return null;
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
	
	
	
 
}
