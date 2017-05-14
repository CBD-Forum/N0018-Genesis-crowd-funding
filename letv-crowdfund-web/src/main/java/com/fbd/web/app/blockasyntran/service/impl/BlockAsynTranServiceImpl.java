package com.fbd.web.app.blockasyntran.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;

@Service(value="blockAsynTranService")
public class BlockAsynTranServiceImpl implements IBlockAsynTranService {
	 @Resource
    private IBlockAsynTranDao  blockAsynTranDao;
	@Override
	public int save(BlockAsynTranModel blockAsynTran) {
		if(StringUtils.isEmpty(blockAsynTran.getParentId())){
			throw new ApplicationException("操作id不能为空");
		}
		if(StringUtils.isEmpty(blockAsynTran.getType())){
			throw new ApplicationException("类型不能为空");
		}
		blockAsynTran.setId(PKGenarator.getId());
		blockAsynTran.setCreateTime(new Date());
		// TODO Auto-generated method stub
		return blockAsynTranDao.save(blockAsynTran);
	}

	@Override
	public int update(BlockAsynTranModel blockAsynTran) {
		// TODO Auto-generated method stub
		blockAsynTran.setUpdateTime(new Date());
		return blockAsynTranDao.update(blockAsynTran);
	}

	@Override
	public BlockAsynTranModel getById(String id) {
		// TODO Auto-generated method stub
		return blockAsynTranDao.selectByPrimaryKey(id);
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
	   return blockAsynTranDao.selectByTranId(tranId);
   }

	@Override
	public BlockAsynTranModel selectByParentId(BlockAsynTranModel model) {
		// TODO Auto-generated method stub
		return blockAsynTranDao.selectByParentId(model);
	}
	public BlockAsynTranModel selectByModel(BlockAsynTranModel model){
		return blockAsynTranDao.selectByModel(model);
	}
	
   public BlockAsynTranModel selectByRequestId(String requestId){
	   return this.blockAsynTranDao.findByRequestId(requestId);
   }
}
