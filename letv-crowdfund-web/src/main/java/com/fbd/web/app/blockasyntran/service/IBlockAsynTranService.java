package com.fbd.web.app.blockasyntran.service;

import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;

public interface IBlockAsynTranService {
	 /**
     * 增加
     * Description: 
     *
     * @param 
     * @return int
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月20日 下午12:44:07
     */
    public int save(BlockAsynTranModel blockAsynTran);
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
   
   public BlockAsynTranModel selectByModel(BlockAsynTranModel model);
   
   public BlockAsynTranModel selectByRequestId(String requestId);
}
