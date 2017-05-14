/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundUserPrizeDao.java 
 *
 * Created: [2016-8-11 上午10:12:01] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.user.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.base.BaseDao;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICrowdfundUserPrizeDao extends BaseDao<CrowdfundUserPrizeModel>{

    /**
     * Description: 查询该项目一共参与抽奖人数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 上午10:12:43
     */
    
    long getCountPrize(String loanNo);

    /**
     * Description: 修改 根据项目编号和抽奖单号
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 上午10:13:09
     */
    
    void updatePrize(int firstNum, String loanNo);

    /**
     * Description:查询列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 下午1:34:07
     */
    
    List<Map<String, Object>> getUserPrizeList(CrowdfundUserPrizeModel model);

    /**
     * Description: 统计
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 下午1:34:23
     */
    
    long getUserPrizeCount(CrowdfundUserPrizeModel model);

    /**
     * Description: 查询抽奖编号
     *
     * @param 
     * @return wuwenbin
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午8:22:50
     */
    
    CrowdfundUserPrizeModel getUserPrize(String loanNo, String supportUser);
    /**
     * 
     * Description: 对指定的项目编号记录进行未中奖操作
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-28 上午11:19:23
     */
    void updateNoPrizeByLoanNo(String loanNo);
    /**
     * 
     * Description: 更具项目编号清掉数据
     *
     * @param 
     * @return int
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-28 下午2:07:24
     */
    public int deleteByLoanNo(String loanNo);
}
