package com.fbd.admin.app.blockasyntran.service;

import java.util.List;

import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;


public interface IBlockAsynTranService {

	int save(BlockAsynTranModel blockAsynTran);

	int update(BlockAsynTranModel blockAsynTran);

	BlockAsynTranModel getById(String id);

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

	public BlockAsynTranModel selectByModel(BlockAsynTranModel blockAsynTranModel);
	public long selectSuccessCount(BlockAsynTranModel model);
}
