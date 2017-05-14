/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainServiceImpl.java 
 *
 * Created: [2016-8-26 上午9:43:34] by haolingfeng
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

package com.fbd.core.app.blockChain.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.auth.dao.IPermissionDao;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.BlockChainCore.BlockAsynSource;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockChain.service.BlockChainCore.UserAuth;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.todo.dao.ITodoDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.CrowdfundCoreConstants.crowdFundingState;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.spring.SpringUtil;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="blockChainAccountService")
public class BlockChainAccountServiceImpl implements IBlockChainAccountService {
    
    private static final Logger logger = LoggerFactory
            .getLogger(BlockChainAccountServiceImpl.class);
    
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBlockChainCrowdfundingService blockChainCrowdfundingService;
    @Resource
    private ICrowdfundAuditDao crowdfundAuditDao;
    @Resource
    private ITodoDao todoDao;
    @Resource
    private IPermissionDao permissionDao;
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> createUserAccount(String accountId,String opSource,BlockAsynTranModel blockAsynTran) {
        
        UserModel user = this.userDao.findByUserId(accountId);
        if(user == null){
            throw new ApplicationException("用户区块链开户失败！");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        //用户未开户
        if(StringUtils.isEmpty(user.getBlockChainState()) || !UserAuth.END.equals(user.getBlockChainState())){
            //区块链开户
            String requestId=PKGenarator.getId();
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID","createAccount");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("UID", accountId);
            params.put(BlockChainCore.REQUEST_ID, requestId);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainAccount/receiveUserOpenAccountS2S.html");
            logger.info("用户开户请求参数:"+params.toString());
            //添加一条事务通知数据
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                //定时任务
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(accountId);  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.OPEN_ACCOUNT_USER);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setStatus(BlockChainCore.ResultStatus.add); //状态为新建状态
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);   
            }  
            logger.info("==========>区块链开户更新用户区块链账户状态为开户中状态=========");
            user.setBlockChainState(UserAuth.CREATE);
            this.userDao.updateByUserId(user);
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
            logger.info("===============>用户开户同步返回结果:"+resultStr);
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            String status = resultMap.get(BlockChainCore.STATUS).toString();
            String message = resultMap.get(BlockChainCore.MESSAGE).toString();
           
            if(ResultStatus.ServiceSuccess.equals(status)){ //业务提交成功
                //等待状态
                BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestId);
                if(blockAsynTranModel!=null)
                    blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
                blockAsynTranModel.setUpdateTime(new Date());
                this.blockAsynTranDao.update(blockAsynTranModel);
            }else{
/*                user.setBlockChainState(null);
                this.userDao.update(user);
                //因有事物，按理来说应该回滚 
*/                
                throw new ApplicationException(message);
            }
            return resultMap;
        }else{//已开户
            Map<String,Object> resultMap=new HashMap<String,Object>();
            resultMap.put(BlockChainCore.STATUS, ResultStatus.ServiceFail);
            resultMap.put(BlockChainCore.MESSAGE, "block类型不在处理范围内");
            return resultMap;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> activeUserAccount(String accountId,String opSource,BlockAsynTranModel blockAsynTran) {
        UserModel user = this.userDao.findByUserId(accountId);
        if(user == null){
            logger.info("===============区块链激活用户，用户【"+accountId+"】信息不存在============");
            throw new ApplicationException("激活时候没找到该用户");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        //用户未开户
        if(UserAuth.ACTIVE.equals(user.getBlockChainState())){
            //区块链开户
            //String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL+"/activeAccount";
            Map<String,String> params = new HashMap<String,String>();
            String requestId=PKGenarator.getId();
            params.put("serviceID","activeAccount");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("address", user.getBlockChainAddress());
            params.put("amount", FbdCoreConstants.BLOCK_CHAIN_ACTIVATED_AMT);//保证金
            params.put(BlockChainCore.REQUEST_ID, requestId);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainAccount/receiveUserActivationAccountS2S.html");   
            logger.info("==============>用户【"+accountId+"】激活账户请求参数:"+params);
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                //添加一条事务通知数据
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(accountId);  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setUpdateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.ACTIVATION_ACCOUNT_USER);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);   
            }
            //等待状态
            user.setBlockChainState(UserAuth.TRUST);
            this.userDao.updateByUserId(user);
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
            logger.info("=============>用户【"+accountId+"】激活同步返回结果:"+resultStr);
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            String state = resultMap.get(BlockChainCore.STATUS).toString();
            if(ResultStatus.ServiceSuccess.equals(state)){  //激活账户提交成功
                logger.info("=========>用户【"+accountId+"】激活账户提交成功=======");
                BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestId);
                blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
                blockAsynTranModel.setUpdateTime(new Date());
                this.blockAsynTranDao.update(blockAsynTranModel);
            }else{
/*                //还原
                user.setBlockChainState(UserAuth.CREATE);
                this.userDao.update(user);*/
                throw new ApplicationException("用户区块链激活用户失败！");
            }
            return resultMap;
        }else{//已开户
            Map<String,Object> resultMap=new HashMap<String,Object>();
            resultMap.put(BlockChainCore.STATUS, ResultStatus.ServiceFail);
            resultMap.put(BlockChainCore.MESSAGE, "block类型不在处理范围内");
            return resultMap;
        }
    }
    
    /**
     * 项目区块链开户
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean createLoanAccount(String accountId,String operate) {
        
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(accountId);
        crowd.setOperate(operate);
        logger.info("=======================开始调用区块链开户，项目编号["+accountId+"]===============");
        Map<String,Object> resultMap= getCreateLoanAccountBlockChainResult(crowd, "blockChainAccount/receiveLoanOpenAccountS2S.html",crowdFundingState.funding.getValue(),BlockAsynSource.NORMAL,null);
        String status = resultMap.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.ServiceSuccess.equals(status)){ //开户成功
            logger.info("===================开始调用区块链开户请求成功，项目编号["+accountId+"]============");
            
/*            crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue());
            crowd.setBlockChainState(UserAuth.CREATE);
            this.crowdfundingDao.updateBySelective(crowd);*/
            return true;
        }else{
            logger.info("===================开始调用区块链开户请求提交失败，还原项目到上一个状态，项目编号["+accountId+"]============");
/*            if("stock".equals(crowd.getLoanType())||"product".equals(crowd.getLoanType())){
                //股权或产品还原预热
                crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.preheat.getValue());
            }else{
                crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
            }
            crowd.setBlockChainState(null);
            this.crowdfundingDao.updateBySelective(crowd);*/
            
            throw new ApplicationException("项目区块链开户失败！");
        }
    }
 
    /**
     * 
     * Description: 获取项目激活区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getActiveLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opSource,BlockAsynTranModel blockAsynTran){
        if(crowd == null){
            throw new ApplicationException("项目信息不存在！");
        }
        if(StringUtils.isEmpty(crowd.getLoanNo())){
            throw new ApplicationException("项目编号不能为空");
        }
        if(StringUtils.isEmpty(crowd.getBlockChainAddress())){
            throw new ApplicationException("项目编号:"+crowd.getLoanNo()+"的区块链地址为空");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        //用户已开户
        if(UserAuth.ACTIVE.equals(crowd.getBlockChainState())){
            //区块链开户
            //String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL+"/activeAccount";
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID","activeAccount");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("address", crowd.getBlockChainAddress());
            params.put("amount", FbdCoreConstants.BLOCK_CHAIN_ACTIVATED_AMT);//保证金
            String requestId=PKGenarator.getId();
            params.put(BlockChainCore.REQUEST_ID, requestId);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+notifyURL);
            
            logger.info("项目激活请求参数:"+params.toString());
            
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                //定时任务
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                //添加一条事务通知数据
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(crowd.getBlockChainAddress());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.ACTIVATION_ACCOUNT_LOAN);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);   
            }
            
            crowd.setBlockChainState(UserAuth.TRUST);
            this.crowdfundingDao.update(crowd);
            
            String resultStr = MockUtils.opBlockChain(params);
            
            logger.info("项目激活同步返回结果:"+resultStr);
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            return resultMap;
        }else{
            StringBuffer str=new StringBuffer();
            str.append("该项目:").append(crowd.getLoanNo()).append("的区块链状态:").append(crowd.getBlockChainState()).append("不能进行激活操作");
            throw new ApplicationException(str.toString());
        }
    }
    
    /**
     * 项目开户激活
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean activeLoanAccount(String accountId) {        
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(accountId);
        Map<String,Object> resultMap=getActiveLoanAccountBlockChainResult(crowd, "blockChainAccount/receiveLoanActivationAccountS2S.html",BlockAsynSource.NORMAL,null);
        String status = resultMap.get(BlockChainCore.STATUS).toString();
        String message=resultMap.get(BlockChainCore.MESSAGE).toString();
        if(ResultStatus.ServiceSuccess.equals(status)){
/*            crowd.setBlockChainState(UserAuth.TRUST);
            this.crowdfundingDao.updateBySelective(crowd);*/
            return true;            
        }else{
/*            crowd.setBlockChainState(UserAuth.ACTIVE);
            this.crowdfundingDao.updateBySelective(crowd);*/
            throw new ApplicationException(message);
        }
    }
    @Override
    public void updateUserAccount(Map<String, String> result) {
        // TODO Auto-generated method stub
        
        String uid =result.get(BlockChainCore.UID).toString();
        String address = result.get(BlockChainCore.ADDRESS).toString();
        String key = result.get(BlockChainCore.KEY).toString();
        UserModel user=userDao.findByUserId(uid);
        user.setBlockChainAddress(address);
        user.setBlockChainSecretKey(key);
        user.setBlockChainState(UserAuth.ACTIVE);
        this.userDao.updateByUserId(user);
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#updateUserActivationAccount(java.util.Map)
     */
    @Override
        // TODO Auto-generated method stub
    public void updateUserActivationAccount(Map<String, String> result) {
        String address =result.get(BlockChainCore.ADDRESS).toString();
        UserModel user=userDao.findByBlockChainAddress(address);
        user.setBlockChainState(UserAuth.TRUST);
        this.userDao.updateByUserId(user);
        
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#updateLoanAccount(java.util.Map)
     */
    @Override
    public void updateLoanAccount(Map<String, String> result) {
        // TODO Auto-generated method stub
        String uid =result.get(BlockChainCore.UID).toString();
        String address = result.get(BlockChainCore.ADDRESS).toString();
        String key = result.get(BlockChainCore.KEY).toString();
        CrowdfundingModel loan=crowdfundingDao.getByloanNo(uid);
        loan.setBlockChainAddress(address);
        loan.setBlockChainSecretKey(key);
        loan.setBlockChainState(UserAuth.ACTIVE);
        this.crowdfundingDao.update(loan);
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#updateLoanActivationAccount(java.util.Map)
     */
    @Override
    public void updateLoanActivationAccount(Map<String, String> result) {
        String address =result.get(BlockChainCore.ADDRESS).toString();
        CrowdfundingModel model=crowdfundingDao.getByBlockChainAddress(address);
        model.setBlockChainState(UserAuth.TRUST);
        this.crowdfundingDao.updateByLoanNo(model);
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#modifyCrowdFundOpLogTimmer(java.lang.String)
     */
    @Override
    public void modifyCrowdFundOpLogTimmer(String loanNo,String operator,String operateType) {
        // TODO Auto-generated method stub
        Timer timer=new Timer();
        timer.schedule(new CrowdFundOpLogTask(timer,loanNo,operator,operateType), 20000, 20000);
    }
    static class CrowdFundOpLogTask extends java.util.TimerTask{
        private Timer timer;
        String loanNo;
        String operator;
        String operateType;
        
        public CrowdFundOpLogTask(Timer timer,String loanNo,String operator,String operateType){
            this.timer=timer;
            this.loanNo=loanNo;
            this.operator=operator;
            this.operateType=operateType;
        }
        
        @Override
        public void run() {
            try{
                System.out.println("----------------------------------------");
                ICrowdfundingDao loanDao=(ICrowdfundingDao) SpringUtil.getBean("crowdfundingDao");
                CrowdfundingModel loan=loanDao.getByloanNo(loanNo);
                if(UserAuth.END.equals(loan.getBlockChainState())){
                    
                    ICrowdfundAuditDao crowdfundAuditDao=(ICrowdfundAuditDao) SpringUtil.getBean("crowdfundAuditDao");
                    crowdfundAuditDao.addLoanAudit(operator, loanNo, 
                            CrowdfundCoreConstants.loanAuditAction.release.getValue(),
                            operator, CrowdfundCoreConstants.crowdFundingState.funding.getValue());
                    ITodoDao todoDao=(ITodoDao) SpringUtil.getBean("todoDao");
                    todoDao.modifyTodoByEventObj(FbdCoreConstants.crowdFundingState.passed.getValue(),
                            loanNo, 
                            operator,
                            operateType);
                    
                    AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                            AuditLogConstants.RESULT_SUCCESS,"项目编号"+loanNo); 
                    
                    timer.cancel();
                }else{
                    if("re_passed".equals(loan.getLoanState())){
                        timer.cancel();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
    }
    /**
     * 
     * Description: 获取项目开户区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getCreateLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opType,String opSource,BlockAsynTranModel blockAsynTran){
        if(crowd == null){
            throw new ApplicationException("项目信息不存在！");
        }
        if(StringUtils.isEmpty(crowd.getLoanNo())){
            throw new ApplicationException("项目编号不能为空");
        }
        if(StringUtils.isEmpty(opType)){
            throw new ApplicationException("操作类型不能为空");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        //项目未开户
        if(StringUtils.isEmpty(crowd.getBlockChainState())){
            //区块链开户
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID","createAccount");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("UID", crowd.getLoanNo());
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+notifyURL);
            String requestId=PKGenarator.getId();
            params.put(BlockChainCore.REQUEST_ID, requestId);
            logger.info("===============项目["+crowd.getLoanNo()+"]开户请求参数:"+params.toString());
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                //定时任务
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                //添加一条事务通知数据
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(crowd.getLoanNo());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.OPEN_ACCOUNT_LOAN);
                blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);
            }
            
            if(crowdFundingState.preheat.getValue().equals(opType)){
                //预热
/*                if(crowd.getPreheatEndTime()==null){
                    throw new ApplicationException("预热截止日期不能为空");
                }*/
                crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue());
/*                //预热截止时间
                crowd.setPreheatEndTime(preheatEndTime);*/
                crowd.setBlockChainState(UserAuth.CREATE);
                this.crowdfundingDao.updateBySelective(crowd);
            }else if(crowdFundingState.funding.getValue().equals(opType)){
                //发布
                crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue());
                crowd.setBlockChainState(UserAuth.CREATE);
                this.crowdfundingDao.updateBySelective(crowd);
            }else{
                throw new ApplicationException("该操作类型异常");
            }
            String resultStr =  HttpRequestUtils.callHttpPOST(requestUrl, params);
            logger.info("===============区块链-项目【"+crowd.getLoanNo()+"】开户请求相应数据："+resultStr);
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            String status = resultMap.get("status")==null?"":resultMap.get("status").toString();
            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){
                BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestId);
                blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
                blockAsynTranModel.setUpdateTime(new Date());
                this.blockAsynTranDao.update(blockAsynTranModel);
            }
            return resultMap;
        }else{//已开户
            logger.info("=============项目【"+crowd.getLoanNo()+"】区块链已经开户成功，无需重新开户==================");
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("status", ResultStatus.ServiceSuccess);
            return resultMap;
        }
    }
     
    @Override
    public boolean createLoanPreheatAccount(String accountId,Date preheatEndTime,String operate) {
        // TODO Auto-generated method stub        
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(accountId);
        crowd.setPreheatEndTime(preheatEndTime);
        crowd.setPreheatTime(new Date());
        crowd.setOperate(operate);
        this.crowdfundingDao.update(crowd);
        Map<String,Object> resultMap=getCreateLoanAccountBlockChainResult(crowd, "blockChainAccount/receiveLoanPreheatOpenAccountS2S.html",crowdFundingState.preheat.getValue(),BlockAsynSource.NORMAL,null);
        logger.info("项目开户同步返回结果:"+resultMap.toString());
        String status = resultMap.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.ServiceSuccess.equals(status)){ //开户成功
            return true;
        }else{
            throw new ApplicationException("项目区块链开户失败！");
        }
    }
    @Override
    public void modifyCrowdPreheatOpLogTimmer(String loanNo, String operator,
            String operateType) {
        // TODO Auto-generated method stub
        Timer timer=new Timer();
        timer.schedule(new CrowdPreheatOpLogTask(timer,loanNo,operator,operateType), 2000, 2000);
    }
    static class CrowdPreheatOpLogTask extends java.util.TimerTask{
        private Timer timer;
        String loanNo;
        String operator;
        String operateType;
        
        public CrowdPreheatOpLogTask(Timer timer,String loanNo,String operator,String operateType){
            this.timer=timer;
            this.loanNo=loanNo;
            this.operator=operator;
            this.operateType=operateType;
        }
        
        @Override
        public void run() {
            try{
                System.out.println("----------------------------------------");
                ICrowdfundingDao loanDao=(ICrowdfundingDao) SpringUtil.getBean("crowdfundingDao");
                CrowdfundingModel loan=loanDao.getByloanNo(loanNo);
                if(UserAuth.END.equals(loan.getBlockChainState())){
                    ICrowdfundAuditDao crowdfundAuditDao=(ICrowdfundAuditDao) SpringUtil.getBean("crowdfundAuditDao");
                    crowdfundAuditDao.addLoanAudit(operator, loanNo, 
                            CrowdfundCoreConstants.loanAuditAction.review_pass.getValue(),
                            null, CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
                    ITodoDao todoDao=(ITodoDao) SpringUtil.getBean("todoDao");
                    todoDao.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.passed.getValue(),
                            loanNo, 
                            operator,
                            CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
                    IPermissionDao permissionDao=(IPermissionDao)SpringUtil.getBean("permissionDao");
                    String link = permissionDao.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_PREHEAT);
                    todoDao.saveTodo(CrowdfundCoreConstants.crowdFundingState.re_passed.getValue(), loanNo, 
                            operator+"审核项目："+loanNo, link, 
                            FbdCoreConstants.todoPost.yrfb);  
                    AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                            AuditLogConstants.RESULT_SUCCESS,"项目编号"+loanNo);   
                    timer.cancel();
                }else{
                    if("re_passed".equals(loan.getLoanState())){
                        timer.cancel();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#activeLoanPreheatAccount(java.lang.String)
     */
    @Override
    public boolean activeLoanPreheatAccount(String accountId) {
        // TODO Auto-generated method stub
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(accountId);
        Map<String,Object> resultMap=getActiveLoanAccountBlockChainResult(crowd, "blockChainAccount/receiveLoanPreheatActivationAccountS2S.html",BlockAsynSource.NORMAL,null);
        String status = resultMap.get(BlockChainCore.STATUS).toString();
        String message = resultMap.get(BlockChainCore.MESSAGE).toString();
        if(ResultStatus.ServiceSuccess.equals(status)){
            return true;            
        }else{
            throw new ApplicationException(message);
        }
    }
 
    @Override
    public void updateLoanPreheatActivationAccount(Map<String, String> result) {
        // TODO Auto-generated method stub
        String address =result.get(BlockChainCore.ADDRESS).toString();
        CrowdfundingModel model=crowdfundingDao.getByBlockChainAddress(address);
        model.setBlockChainState(UserAuth.TRUST);
        this.crowdfundingDao.updateByLoanNo(model);
    }
 
    @Override
    public String userAccountQuery(String address, String requestId) {
        // TODO Auto-generated method stub
        String resultStr = "";
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "accountXRPQuery");
            //params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put(BlockChainCore.ADDRESS,address);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainAccount/userAccountQueryNotify.html");
            params.put(BlockChainCore.REQUEST_ID,requestId);
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            resultStr =   HttpRequestUtils.callHttpPOST(requestUrl, params);
            
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#trustUserAccount(java.lang.String, java.lang.String)
     */
    @Override
    public Map<String,Object> trustUserAccount(String sourceAddress,String opSource,BlockAsynTranModel blockAsynTran) {
        // TODO Auto-generated method stub
        
        UserModel user = this.userDao.findByBlockChainAddress(sourceAddress);
        if(user == null){
            throw new ApplicationException("不存在该地址:"+sourceAddress+"用户");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        if(UserAuth.TRUST.equals(user.getBlockChainState())){
            //String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL+"/activeAccount";
            Map<String,String> params = new HashMap<String,String>();
            String requestId=PKGenarator.getId();
            params.put("serviceID","setTrust");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("sourceAddress", user.getBlockChainAddress());
            params.put("sourceKey", user.getBlockChainSecretKey());
            params.put(BlockChainCore.REQUEST_ID, requestId);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainAccount/receiveUserTrustAccountS2S.html");  
            
            logger.info("=============>用户信任请求参数:"+params.toString());
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                //定时任务
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                //添加一条事务通知数据
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(user.getBlockChainAddress());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.TRUST_ACCOUNT_USER);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);
   
            }
            logger.info("=============>更新用户区块链状态为添加信任处理中===============");
            //等待状态
            user.setBlockChainState(UserAuth.TRUST_OP);
            this.userDao.updateByUserId(user);
            String resultStr = MockUtils.opBlockChain(params);
            logger.info("用户信任同步返回结果:"+resultStr);
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            String state = resultMap.get(BlockChainCore.STATUS).toString();
            if(ResultStatus.ServiceSuccess.equals(state)){ //提交成功
                BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestId);
                blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
                blockAsynTranModel.setUpdateTime(new Date());
                this.blockAsynTranDao.update(blockAsynTranModel);
            }else{
/*                user.setBlockChainState(UserAuth.TRUST);
                this.userDao.update(user);*/
                throw new ApplicationException("用户区块链激活用户失败！");
            }
            return resultMap;
        }else{//已开户
            Map<String,Object> resultMap=new HashMap<String,Object>();
            resultMap.put(BlockChainCore.STATUS, ResultStatus.ServiceFail);
            resultMap.put(BlockChainCore.MESSAGE, "block类型不在处理范围内");
            return resultMap;
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#updateUserTrustAccount(java.util.Map)
     */
    @Override
    public void updateUserTrustAccount(String userAddress) {
        // TODO Auto-generated method stub
        UserModel user=userDao.findByBlockChainAddress(userAddress);
        user.setBlockChainState(UserAuth.END);
        this.userDao.updateByUserId(user);
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#updateLoanTrustAccount(java.lang.String)
     */
    @Override
    public void updateLoanTrustAccount(String userAddress) {
        CrowdfundingModel loan=crowdfundingDao.getByBlockChainAddress(userAddress);
        loan.setBlockChainState(UserAuth.END);
        this.crowdfundingDao.update(loan);
        blockChainCrowdfundingService.updateCrowdFundAfterRelease(loan);
        this.modifyLoanFundingLogMsg(loan.getLoanNo(), loan.getOperate());
        
        //添加信任后项目发布成功,发送消息
        
    }
 
    @Override
    public void updateLoanPreheatTrustAccount(String userAddress) {
        // TODO Auto-generated method stub
        CrowdfundingModel loan=crowdfundingDao.getByBlockChainAddress(userAddress);
        loan.setBlockChainState(UserAuth.END);
        loan.setPreheatTime(new Date());
        this.crowdfundingDao.update(loan);
        blockChainCrowdfundingService.updateCrowdFundAfterPreheat(loan.getLoanNo(), loan.getPreheatEndTime());
        this.modifyLoanPreaheatLogMsg(loan.getLoanNo(), loan.getOperate());
    }
    
    /**
     * 
     * Description: 获取项目信任区块链同步结果
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-10 上午11:03:01
     */
    public Map<String,Object> getTrustLoanAccountBlockChainResult(CrowdfundingModel crowd,String notifyURL,String opSource,BlockAsynTranModel blockAsynTran){
        if(crowd == null){
            throw new ApplicationException("项目信息不存在！");
        }
        if(StringUtils.isEmpty(crowd.getLoanNo())){
            throw new ApplicationException("项目编号不能为空");
        }
        if(StringUtils.isEmpty(opSource)){
            throw new ApplicationException("操作来源不能为空");
        }
        if(StringUtils.isEmpty(crowd.getBlockChainAddress())){
            throw new ApplicationException("项目编号:"+crowd.getLoanNo()+"的区块链地址为空");
        }
        if(UserAuth.TRUST.equals(crowd.getBlockChainState())){
            Map<String,String> params = new HashMap<String,String>();
            String requestId=PKGenarator.getId();
            params.put("serviceID","setTrust");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("sourceAddress", crowd.getBlockChainAddress());
            params.put("sourceKey", crowd.getBlockChainSecretKey());
            params.put(BlockChainCore.REQUEST_ID, requestId);
          
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+notifyURL);   
            
            logger.info("项目信任请求参数:"+params.toString());
            
            if(BlockAsynSource.JOB.equals(opSource)){
                if(blockAsynTran==null||StringUtils.isEmpty(blockAsynTran.getId())){
                    throw new ApplicationException("当操作来源是job的时候blockAsynTran不能为空或是数据异常");
                }
                //定时任务
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.update(blockAsynTran);
            }else{
                //添加一条事务通知数据
                blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(crowd.getBlockChainAddress());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.TRUST_ACCOUNT_LOAN);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setSource(opSource);
                this.blockAsynTranDao.save(blockAsynTran);   
            }
          
            
            String resultStr = MockUtils.opBlockChain(params);
            logger.info("项目信任同步返回结果:"+resultStr);
         
            crowd.setBlockChainState(UserAuth.TRUST_OP);
            this.crowdfundingDao.updateByLoanNo(crowd);   
            
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
            return resultMap;
        }else{
            StringBuffer str=new StringBuffer();
            str.append("该项目:").append(crowd.getLoanNo()).append("的区块链状态:").append(crowd.getBlockChainState()).append("不能进行信任操作");
            throw new ApplicationException(str.toString());
        }
    }
    
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#trustLoanAccount(java.lang.String)
     */
    @Override
    public boolean trustLoanAccount(String sourceAddress) {
        CrowdfundingModel loan = this.crowdfundingDao.getByBlockChainAddress(sourceAddress);
        Map<String,Object> resultMap=getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanTrustAccountS2S.html",BlockAsynSource.NORMAL,null);
        String state = resultMap.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.ServiceSuccess.equals(state)){
            //等待状态
/*            loan.setBlockChainState(UserAuth.TRUST_OP);
            this.crowdfundingDao.updateByLoanNo(loan);*/
            return true;
        }else{
/*            loan.setBlockChainState(UserAuth.TRUST);
            this.crowdfundingDao.update(loan);*/
            
            throw new ApplicationException("区块链添加信息失败！");
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#trustLoanPreheatAccount(java.lang.String)
     */
    @Override
    public boolean trustLoanPreheatAccount(String sourceAddress) {
        // TODO Auto-generated method stub
        CrowdfundingModel loan = this.crowdfundingDao.getByBlockChainAddress(sourceAddress);
        Map<String,Object> resultMap=getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanPreheatTrustAccountS2S.html",BlockAsynSource.NORMAL,null);
        String state = resultMap.get(BlockChainCore.STATUS).toString();
        if(ResultStatus.ServiceSuccess.equals(state)){
            //等待状态
/*            loan.setBlockChainState(UserAuth.TRUST_OP);
            this.crowdfundingDao.updateByLoanNo(loan);*/
            return true;
        }else{
/*            loan.setBlockChainState(UserAuth.TRUST);
            this.crowdfundingDao.updateByLoanNo(loan);*/
            
            throw new ApplicationException("区块链添加信任失败！");
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainAccountService#userAccountQuery(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String userAccountQuery(String address, String requestId,
            String notifyURL) {
        // TODO Auto-generated method stub
        String resultStr = "";
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "accountXRPQuery");
            //params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put(BlockChainCore.ADDRESS,address);
            //params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainAccount/userAccountQueryNotify.html");
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+notifyURL);
            params.put(BlockChainCore.REQUEST_ID,requestId);
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            resultStr =   HttpRequestUtils.callHttpPOST(requestUrl, params);           
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }
    @Override
    public void modifyLoanPreaheatLogMsg(String loanNo,String operator){
        // TODO Auto-generated method stub
        String operateType = AuditLogConstants.TYPE_PREHEAT;
        crowdfundAuditDao.addLoanAudit(operator, loanNo, 
                CrowdfundCoreConstants.loanAuditAction.review_pass.getValue(),
                null, CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
        todoDao.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.passed.getValue(),
                loanNo, 
                operator,
                CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
        String link = permissionDao.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_PREHEAT);
        todoDao.saveTodo(CrowdfundCoreConstants.crowdFundingState.re_passed.getValue(), loanNo, 
                operator+"审核项目："+loanNo, link, 
                FbdCoreConstants.todoPost.yrfb);  
        AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                AuditLogConstants.RESULT_SUCCESS,"项目编号"+loanNo);   
    }
    @Override
    public void modifyLoanFundingLogMsg(String loanNo, String operator) {
        // TODO Auto-generated method stub
        String operateType = AuditLogConstants.TYPE_RELEASE;
        crowdfundAuditDao.addLoanAudit(operator, loanNo, 
                CrowdfundCoreConstants.loanAuditAction.release.getValue(),
                operator, CrowdfundCoreConstants.crowdFundingState.funding.getValue());
        todoDao.modifyTodoByEventObj(FbdCoreConstants.crowdFundingState.passed.getValue(),
                loanNo, 
                operator,
                operateType);        
        AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                AuditLogConstants.RESULT_SUCCESS,"项目编号"+loanNo); 
    }
}
