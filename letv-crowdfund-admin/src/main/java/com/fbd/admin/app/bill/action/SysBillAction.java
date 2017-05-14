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
import com.fbd.core.app.bill.model.SystemBillModel;
import com.fbd.core.app.bill.service.ISystemBillService;
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
@RequestMapping("/sysbill")
public class SysBillAction extends BaseAction {
    @Resource
    private ISystemBillService sysBillService;

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return SearchResult<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午2:07:41
     */
    @RequestMapping(value = "/getSysBill.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<SystemBillModel> getSysBill(SystemBillModel model) {
        SearchResult<SystemBillModel> data =  this.sysBillService.getSysBillList(model);
        return data;
    }
    
    @RequestMapping(value = "/getSysBillTotal.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getSysBillTotal(SystemBillModel model) {
        Map<String,Object> data =  this.sysBillService.getTotalData();
        return data;
    }
    
    
  
}
