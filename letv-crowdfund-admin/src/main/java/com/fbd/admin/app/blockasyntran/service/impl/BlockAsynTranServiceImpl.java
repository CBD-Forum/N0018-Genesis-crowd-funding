package com.fbd.admin.app.blockasyntran.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
@Service
public class BlockAsynTranServiceImpl implements IBlockAsynTranService{
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
		public BlockAsynTranModel selectByModel(BlockAsynTranModel blockAsynTranModel) {
			// TODO Auto-generated method stub
			return blockAsynTranDao.selectByModel(blockAsynTranModel);
		}
		@Override
		public BlockAsynTranModel selectByTranId(String tranId) {
			// TODO Auto-generated method stub
			return blockAsynTranDao.selectByTranId(tranId);
		}

		@Override
		public long selectFlowCount(String parentId) {
			// TODO Auto-generated method stub
			return blockAsynTranDao.selectFlowCount(parentId);
		}

		@Override
		public List<BlockAsynTranModel> selectFailFlowsList(String loanNo) {
			// TODO Auto-generated method stub
			return blockAsynTranDao.selectFailFlowsList(loanNo);
		}

		@Override
		public long selectSuccessCount(BlockAsynTranModel model) {
			// TODO Auto-generated method stub
			return blockAsynTranDao.selectSuccessCount(model);
		}
}
