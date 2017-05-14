package com.fbd.admin.app.crowdFund.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.crowdFund.service.ICrowdfundingBonusService;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.crowdfunding.model.CrowdfundBonusAuditModel;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;


@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingBonus")
public class CrowdfundingBonusAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingBonusAction.class);

    @Resource
    private ICrowdfundingBonusService crowdfundingBonusService;
    @Resource
    private IUserService userService;
    @Resource
    private IRewardAssignDao rewardAssignDao;
    @Resource
    private ICrowdfundBonusDao crowdfundBonusDao; 
    
    /**
     * 
     * Description: 查询分红项目待审核列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getLoanBonusList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getBonusAuditList(CrowdfundBonusModel model) {
        SearchResult<Map<String, Object>> aList = crowdfundingBonusService.getLoanBonusList(model);
        return aList;
    }
    
    /**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/getRewardAssignList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getRewardAssignList(RewardAssignModel rewardModel){
    	SearchResult<Map<String, Object>>  pageList= this.crowdfundingBonusService.getRewardPageList(rewardModel);
         return pageList;
    	
    }
    
    

    
    

    
    /**
     * 
     * Description:补发分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/sendRereissueBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> sendRereissueBonus(String id) {
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	try {
	    	this.crowdfundingBonusService.sendRereissueBonus(id);
	    	resultMap.put(SUCCESS, true);
	    } catch (Exception e) {
	        logger.error(e.getMessage());
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,"补发分红失败");
	    }
	    	return resultMap;
	}
    /**
     * 
     * Description: 查询分红项目    审核记录 列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getLoanAuditBonusPage.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getLoanAuditBonusPage(CrowdfundBonusAuditModel model) {
        SearchResult<Map<String, Object>> aList = crowdfundingBonusService.getLoanAuditBonusPage(model);
        return aList;
    }
    
    
    
    /**
     * Description:项目分红审核
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateLoanBonusState.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> updateLoanBonusState(CrowdfundBonusAuditModel model) {
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	try {
    		String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
    		model.setAuditor(operator);
	    	this.crowdfundingBonusService.updateLoanBonusState(model);
	    	resultMap.put(SUCCESS, true);
	    } catch (Exception e) {
	        logger.error(e.getMessage());
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,"项目分红审核失败");
	    }
	    	return resultMap;
	} 
    
    
    /** 
     * 查询分红审核处理情况
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectBounsAuditStatus.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectBounsAuditStatus(String id){
	   Map<String,Object> resultMap = new HashMap<String,Object>();
	   try{
//		   RewardAssignModel model = new RewardAssignModel();
//		   model.setLoanBonusId(id);
//		   model.setBonusState(FbdCoreConstants.bonusState.wait_bonus);
//		   List<Map<String,Object>> list = this.rewardAssignDao.getList(model);
//		   resultMap.put(MSG, list!=null&&l resultMap.put(SUCCESS, true);
		   
		   CrowdfundBonusModel bonusmodel = this.crowdfundBonusDao.selectByPrimaryKey(id);
		   if(FbdCoreConstants.bonusState.bonus_success.equals(bonusmodel.getBonusState())){
			   resultMap.put(MSG, true);
		   }else{
			   resultMap.put(MSG, false);
		   }
		   resultMap.put(SUCCESS, true);
	   }catch(Exception e){
		   e.printStackTrace();
		   resultMap.put(SUCCESS, false);
	   }
	   return resultMap;
   } 
    
    
    /** 
     * 查询分红审核拒绝处理情况
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectBounsAuditRefuseStatus.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectBounsAuditRefuseStatus(String id){
	   Map<String,Object> resultMap = new HashMap<String,Object>();
	   try{
		   CrowdfundBonusModel bonusmodel = this.crowdfundBonusDao.selectByPrimaryKey(id);
		   
		   resultMap.put(SUCCESS, true);
		   resultMap.put(MSG, "refuse".equals(bonusmodel.getBonusAuditState()));

	   }catch(Exception e){
		   e.printStackTrace();
		   resultMap.put(SUCCESS, false);
	   }
	   return resultMap;
   } 
    
      
    
    
	 
}
