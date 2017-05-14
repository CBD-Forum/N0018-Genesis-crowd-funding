/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockAsynTranDao.java 
 *
 * Created: [2016年9月20日 下午12:40:57] by haolingfeng
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

package com.fbd.core.app.blockasyntran.dao;

import java.util.List;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
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

public interface IBlockAsynTranDao extends BaseDao<BlockAsynTranModel> {
    /**
     * 
     * Description:根据指定查询获取数据 
     *
     * @param 
     * @return List<BlockAsynTranModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-24 下午4:10:16
     */
    public List<BlockAsynTranModel> findByModel(BlockAsynTranModel model);
    
    /**
     * 
     * Description: 根据请求id获取对方
     *
     * @param 
     * @return BlockAsynTranModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-8 下午4:31:26
     */
    public BlockAsynTranModel findByRequestId(String requestId);
    /**
     * 修改
     * Description: 
     *
     * @param 
     * @return int
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月20日 下午12:44:33
     */
    public int update(BlockAsynTranModel blockAsynTran);
    /**
     * 查询
     * Description: 
     *
     * @param 
     * @return BlockAsynTran
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月20日 下午12:44:55
     */
    public BlockAsynTranModel getById(String id);
    
    /**
     * 根据model查询对象
     * Description: 
     *
     * @param 
     * @return BlockAsynTranModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-22 下午2:40:41
     */
    public BlockAsynTranModel selectByModel(BlockAsynTranModel model);
 
    /**
     * 通过tranId查询
     * Description: 
     *
     * @param 
     * @return BlockAsynTran
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月22日 下午2:52:07
     */
   public BlockAsynTranModel selectByTranId(String tranId);
   
   public long selectFlowCount(String parentId);
   public List<BlockAsynTranModel> selectFailFlowsList(String loanNo);
   /**
    * 通过类型和父 Id 进行查询
    * Description: 
    *
    * @param 
    * @return BlockAsynTranModel
    * @throws 
    * @Author haolingfeng
    * Create Date: 2016年9月26日 下午7:05:19
    */
   public BlockAsynTranModel selectByParentId(BlockAsynTranModel model);
   public long selectSuccessCount(BlockAsynTranModel model);
   
   public BlockAsynTranModel findByQueryRequestId(String queryRequestId);
}
