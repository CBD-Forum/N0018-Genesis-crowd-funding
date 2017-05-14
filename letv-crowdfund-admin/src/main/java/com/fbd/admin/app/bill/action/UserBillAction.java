/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
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

package com.fbd.admin.app.bill.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
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
    SearchResult<UserBillModel> getUserBill(UserBillModel model) {
        SearchResult<UserBillModel> data =  this.userBillService.getUserBillList(model);
        return data;
    }
    
    /**
     * 
     * Description: 查询用户最新资金情况
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-13 下午2:10:32
     */
    @RequestMapping(value = "/getUserLatestBill.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserLatestBill(UserBillModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            UserBillModel userBill = this.userBillService.getLatestBill(model.getUserId());
            resultMap.put(MSG,userBill);
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

}
