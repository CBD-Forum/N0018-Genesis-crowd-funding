package com.fbd.admin.app.withdraw.service;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.common.model.SearchResult;

public interface ISystemWithDrawService {

    public int saveSystemWithDraw(SystemWithdrawModel model);
    /**
     * 
     * Description: 查询提现列表
     *
     * @param 
     * @return List<WithDrawModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-27 上午10:25:20
     */
    public List<SystemWithdrawModel> getList(SystemWithdrawModel model);
    /**
     * 
     * Description: 取现列表条数
     *
     * @param 
     * @return Long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-27 上午10:26:28
     */
    public Long getListCounts(SystemWithdrawModel model);
    public SearchResult<Map<String, Object>> getSystemWithDrawPage(SystemWithdrawModel systemWithdrawModel);
    public long updateById(SystemWithdrawModel model);
    public SystemWithdrawModel selectByOrderId(String orderId);


}
