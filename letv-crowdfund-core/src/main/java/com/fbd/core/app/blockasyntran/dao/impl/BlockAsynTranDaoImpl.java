/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockAsynTranDaoImpl.java 
 *
 * Created: [2016年9月20日 下午12:41:08] by haolingfeng
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

package com.fbd.core.app.blockasyntran.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository(value="blockAsynTranDao")
public class BlockAsynTranDaoImpl extends BaseDaoImpl<BlockAsynTranModel> implements IBlockAsynTranDao{
	    
	    public int update(BlockAsynTranModel blockAsynTran){
	        return this.updateBySelective(blockAsynTran);
	    }
	    
	    public BlockAsynTranModel getById(String id){
	        return this.selectByPrimaryKey(id);
	    }
	    @Override
	    public BlockAsynTranModel selectByModel(BlockAsynTranModel model) {
	        return this.selectOneByField("selectByModel",model);
	    }
	   
	    public List<BlockAsynTranModel> selectFailFlowsList(String loanNo){
	        return this.selectByField("selectFailFlowsList", loanNo);
	    }
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
   public BlockAsynTranModel selectByTranId(String tranId){
       return this.selectOneByField("selectByTranId", tranId);
   }
   public long selectFlowCount(String parentId){
       return this.getCount("selectFlowCount", parentId);
   }
   public long selectSuccessCount(BlockAsynTranModel model){
       return this.getCount("selectSuccessCount", model);
   }
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
   public BlockAsynTranModel selectByParentId(BlockAsynTranModel model){
       return this.selectOneByField("selectByParentId", model);
   }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao#findByRequestId(java.lang.String)
     */
    @Override
    public BlockAsynTranModel findByRequestId(String requestId) {
        return this.selectOneByField("findByRequestId", requestId);
        
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao#findByQueryRequestId(java.lang.String)
     */
    @Override
    public BlockAsynTranModel findByQueryRequestId(String queryRequestId) {
        // TODO Auto-generated method stub
        return this.selectOneByField("findByQueryRequestId", queryRequestId);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao#findByModel(com.fbd.core.app.blockasyntran.model.BlockAsynTranModel)
     */
    @Override
    public List<BlockAsynTranModel> findByModel(BlockAsynTranModel model) {
        // TODO Auto-generated method stub
        return this.selectByField("findByModel", model);
    }
}
