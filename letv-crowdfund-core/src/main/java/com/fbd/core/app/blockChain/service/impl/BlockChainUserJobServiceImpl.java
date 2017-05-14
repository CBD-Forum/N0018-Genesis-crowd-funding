/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainUserJobServiceImpl.java 
 *
 * Created: [2016-10-24 下午3:32:45] by haolingfeng
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
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.blockChain.service.IBlockChainUserJobService;
import com.fbd.core.app.blockChain.service.BlockChainCore.BlockAsynSource;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.CrowdfundCoreConstants.crowdFundingState;
import com.fbd.core.util.DateUtil;
/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service(value="blockChainUserJobService")
public class BlockChainUserJobServiceImpl implements IBlockChainUserJobService{

    private static final Logger logger = LoggerFactory.getLogger(BlockChainUserJobServiceImpl.class);
    
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IBlockChainAccountService blockChainAccountService;
    @Resource
    private IUserDao userDao;
    
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanCreateAccount()
     */
    @Override
    public void modifyLoanCreateAccount() {
        String method="项目开户";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.OPEN_ACCOUNT_LOAN);
        model.setQueryLogic("userCreateAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                String notifyURL=null;
                String opType=null;
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
                    opType=crowdFundingState.preheat.getValue();
                    notifyURL="blockChainAccount/receiveLoanPreheatOpenAccountS2S.html";
                }else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
                    opType=crowdFundingState.funding.getValue();
                    notifyURL="blockChainAccount/receiveLoanOpenAccountS2S.html";
                }else{
                    break;
                }
                Map<String,Object> result=blockChainAccountService.getCreateLoanAccountBlockChainResult(loan, notifyURL, opType,BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userCreateAccount_notAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
        model.setQueryLogic("userCreateAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                String notifyURL=null;
                String opType=null;
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
                    opType=crowdFundingState.preheat.getValue();
                    notifyURL="blockChainAccount/receiveLoanPreheatOpenAccountS2S.html";
                }else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
                    opType=crowdFundingState.funding.getValue();
                    notifyURL="blockChainAccount/receiveLoanOpenAccountS2S.html";
                }else{
                    break;
                }
                Map<String,Object> result=blockChainAccountService.getCreateLoanAccountBlockChainResult(loan, notifyURL, opType,BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userCreateAccount_errorAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanActivationAccount()
     */
    @Override
    public void modifyLoanActivationAccount() {
        String method="项目激活";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.ACTIVATION_ACCOUNT_LOAN);
        model.setQueryLogic("userActivationAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                String resultStr=blockChainAccountService.userAccountQuery(loan.getBlockChainAddress(),m.getId(), "blockChainAccountJob/loanActivationAccountQueryNotify.html");
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userActivationAccount_notAsyn)同步返回结果:"+resultStr+" ------");
            }
        }
        model.setQueryLogic("userActivationAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                String resultStr=blockChainAccountService.userAccountQuery(loan.getBlockChainAddress(),m.getId(), "blockChainAccountJob/loanActivationAccountQueryNotify.html");
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userActivationAccount_errorAsyn)同步返回结果:"+resultStr+" ------");
            }
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanTrustAccount()
     */
    @Override
    public void modifyLoanTrustAccount() {
        // TODO Auto-generated method stub
        String method="项目信任";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.TRUST_ACCOUNT_LOAN);
        model.setQueryLogic("userTrustAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                Map<String,Object> result=null;
                Map<String,String> data=new HashMap<String,String>();
                data.put(BlockChainCore.ADDRESS, loan.getBlockChainAddress());
                if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
                    blockChainAccountService.updateLoanPreheatActivationAccount(data);
                    result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanPreheatTrustAccountS2S.html",BlockAsynSource.JOB,m);
                }else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
                    blockChainAccountService.updateLoanActivationAccount(data);
                    result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanTrustAccountS2S.html",BlockAsynSource.JOB,m);
                }else{
                    break;
                }
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userTrustAccount_notAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
        model.setQueryLogic("userTrustAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                CrowdfundingModel loan=crowdfundingDao.getByloanNo(m.getParentId());
                Map<String,Object> result=null;
                Map<String,String> data=new HashMap<String,String>();
                data.put(BlockChainCore.ADDRESS, loan.getBlockChainAddress());
                if(CrowdfundCoreConstants.crowdFundingState.preheat_locking.getValue().equals(loan.getLoanState())){
                    blockChainAccountService.updateLoanPreheatActivationAccount(data);
                    result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanPreheatTrustAccountS2S.html",BlockAsynSource.JOB,m);
                }else if(CrowdfundCoreConstants.crowdFundingState.funding_locking.getValue().equals(loan.getLoanState())){
                    blockChainAccountService.updateLoanActivationAccount(data);
                    result=blockChainAccountService.getTrustLoanAccountBlockChainResult(loan, "blockChainAccount/receiveLoanTrustAccountS2S.html",BlockAsynSource.JOB,m);
                }else{
                    break;
                }
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userTrustAccount_errorAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
    }

    
    
    
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanCreateAccount()
     */
    @Override
    public void modifyUserCreateAccount() {
        String method="用户开户";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.OPEN_ACCOUNT_USER);
        model.setQueryLogic("userCreateAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                Map<String,Object> result=blockChainAccountService.createUserAccount(m.getParentId(),BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userCreateAccount_notAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
        model.setQueryLogic("userCreateAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                Map<String,Object> result=blockChainAccountService.createUserAccount(m.getParentId(),BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userCreateAccount_errorAsyn)同步返回结果:"+result.toString()+" ------");                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userCreateAccount_errorAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanActivationAccount()
     */
    @Override
    public void modifyUserActivationAccount() {
        String method="用户激活";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.ACTIVATION_ACCOUNT_USER);
        model.setQueryLogic("userActivationAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                UserModel user=userDao.findByUserId(m.getParentId());
                String resultStr=blockChainAccountService.userAccountQuery(user.getBlockChainAddress(),m.getId(), "blockChainAccountJob/userActivationAccountQueryNotify.html");
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userActivationAccount_notAsyn)同步返回结果:"+resultStr+" ------");
            }
        }
        model.setQueryLogic("userActivationAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                UserModel user=userDao.findByUserId(m.getParentId());
                String resultStr=blockChainAccountService.userAccountQuery(user.getBlockChainAddress(),m.getId(), "blockChainAccountJob/userActivationAccountQueryNotify.html");
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userActivationAccount_notAsyn)同步返回结果:"+resultStr+" ------");
            }
        }
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainUserJobService#loanTrustAccount()
     */
    @Override
    public void modifyUserTrustAccount() {
        // TODO Auto-generated method stub
        String method="用户信任";
        logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
        // TODO Auto-generated method stub
        BlockAsynTranModel model=new BlockAsynTranModel();
        model.setType(BlockChainCore.Type.TRUST_ACCOUNT_USER);
        model.setQueryLogic("userTrustAccount_notAsyn");
        List<BlockAsynTranModel> list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                UserModel user=userDao.findByUserId(m.getParentId());
                Map<String,Object> result=null;
                result=blockChainAccountService.trustUserAccount(user.getBlockChainAddress(),BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userTrustAccount_notAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
        model.setQueryLogic("userTrustAccount_errorAsyn");
        list=blockAsynTranDao.findByModel(model);
        if(list!=null&&list.size()>0){
            for(BlockAsynTranModel m:list){
                UserModel user=userDao.findByUserId(m.getParentId());
                Map<String,Object> result=null;
                result=blockChainAccountService.trustUserAccount(user.getBlockChainAddress(),BlockAsynSource.JOB,m);
                logger.info("---------------执行"+method+"定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+") blockId:"+m.getId()+"(userTrustAccount_errorAsyn)同步返回结果:"+result.toString()+" ------");
            }
        }
    }
}
