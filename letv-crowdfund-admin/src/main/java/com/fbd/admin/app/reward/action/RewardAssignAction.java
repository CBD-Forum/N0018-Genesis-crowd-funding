/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RewardAction.java 
 *
 * Created: [2015-3-3 下午3:20:21] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.reward.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.reward.service.IRewardAssignService;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.util.report.util.ExcelReportUtil;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 奖励发放
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/rewardAssign")
public class RewardAssignAction extends BaseAction{

    /**
     * 
     */
    private static final long serialVersionUID = -703120637415398871L;
    @Resource
    private IRewardAssignService rewardAssignService;
    
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
    public @ResponseBody SearchResult<Map<String,Object>> getRewardAssignList(RewardAssignModel rewardModel){
        return this.rewardAssignService.getPageList(rewardModel);
    }
    /**
     * 
     * Description: 项目奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/getLoanRewardAssignDetail.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String,Object>> getLoanRewardAssignDetail(RewardAssignModel rewardModel){
        return this.rewardAssignService.getLoanRewardDetail(rewardModel);
    }
    
    /**
     * 
     * Description: 项目奖励发放列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/getLoanRewardAssignList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String,Object>> getLoanRewardAssignList(RewardAssignModel rewardModel){
        return this.rewardAssignService.getLoanRewardList(rewardModel);
    }
    
    /**
     * 
     * Description: 投标奖励发放
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/assignInvestReward.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> assignInvestReward(String loanNo,double ratio){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            this.rewardAssignService.saveInvestRewardAssign(loanNo, ratio);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            resultMap.put(MSG,e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 推荐奖励发放
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/assignRecommandReward.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> assignRecommandReward(String loanNo,double ratio,String recommandType){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            this.rewardAssignService.saveRecommendRewardAssign(loanNo, ratio, recommandType);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
        	e.printStackTrace();
            resultMap.put(MSG,e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 导出奖励发放明细
     */
    @RequestMapping(value = "/exportExcel.html", method = RequestMethod.GET)
    public void exportExcel(RewardAssignModel rewardModel, HttpServletRequest request, HttpServletResponse response){
        try {
        	
            SearchResult<Map<String,Object>> resultMap = this.rewardAssignService.getPageList(rewardModel);
        	
            String templateFilePath = FbdCoreConstants.REWARDASSIGN_EXPORT_CONFIG;
            HSSFWorkbook workbook = ExcelReportUtil.genExcelReport(templateFilePath, resultMap.getRows());
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename="+
                    URLEncoder.encode(FbdCoreConstants.REWARDASSIGN_EXPORT_FILE_NAME+".xls","UTF-8"));
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
}
