/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserBillAction.java 
 *
 * Created: [2015-1-8 下午2:06:43] by haolingfeng
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

package com.fbd.web.app.bill.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
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

import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.bill.service.impl.UserBillServiceImpl;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.util.report.util.ExcelReportUtil;
import com.fbd.web.app.common.FbdConstants;

/**
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bill")
public class UserBillAction extends BaseAction {
    @Resource
    private IUserBillService userBillService;

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return SearchResult<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/getUserBill.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserBill(HttpServletRequest request,
            UserBillModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	if(!"withdraw_freeze".equals(model.getTradeType())){
	            String userId = this.getUserId(request);
	            model.setUserId(userId);
	            SearchResult<UserBillModel> data =  this.userBillService.getUserBillList(model);
	            resultMap.put(MSG, data);
        	}
        	resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(MSG, "获取数据失败！");
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return UserBillModel
     * @throws
     */
    @RequestMapping(value = "/getUserBillDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserBillDetail(HttpServletRequest request,
            UserBillModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            UserBillModel data =  this.userBillService.getUserBillDetail(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, data);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, UserBillModel> test(HttpServletRequest request) {
        Map<String, UserBillModel> resultMap = new HashMap<String, UserBillModel>();
         UserBillModel latestBil= userBillService.getLatestBillByRedis("zbu9719502");
        		 // userBillService.getLatestBill("zbu9719502");
         resultMap.put(latestBil.getUserId(), latestBil);
	    
	    return resultMap;
    }
    /**
     * 
     * Description: 查询用户余额
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-13 下午2:10:32
     */
    @RequestMapping(value = "/getUserBalance.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserBillBalance(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String userId = this.getUserId(request);
            UserBillModel model = this.userBillService.getLatestBill(userId);
            double balance = model.getBalance();
            resultMap.put(MSG,balance);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 导出交易明细
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-28 上午9:56:15
     */
    @RequestMapping(value = "/exportExcel.html", method = RequestMethod.GET)
    public void exportExcel(UserBillModel model,HttpServletRequest request,
            HttpServletResponse response){
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            List<UserBillModel> resultList = this.userBillService.getList(model);
            String templateFilePath = FbdConstants.USERBILL_EXPORT_CONFIG;
            HSSFWorkbook workbook = ExcelReportUtil.genExcelReport(templateFilePath, resultList);
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename="+
                    URLEncoder.encode(FbdConstants.USERBILL_EXPORT_FILE_NAME+".xls","UTF-8"));
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
