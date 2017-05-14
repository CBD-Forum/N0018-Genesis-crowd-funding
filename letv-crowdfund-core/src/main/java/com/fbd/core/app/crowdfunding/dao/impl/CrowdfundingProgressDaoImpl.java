/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingProgressDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:32:02] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.message.dao.IMessageNodeDao;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹进展
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingProgressDao")
public class CrowdfundingProgressDaoImpl extends BaseDaoImpl<CrowdfundingProgressModel>
implements ICrowdfundingProgressDao{
    @Resource
    private IMessageNodeDao messageNodeDao;
    
    /**
     * 
     * Description: 查询项目进展
     *
     * @param 
     * @return List<CrowdfundingProgressModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午3:10:02
     */
    public List<Map<String,Object>> getList(CrowdfundingProgressModel model){
        return this.selectMapByFields("selectList", model);
    }
    
    
    
    public long selectCount(CrowdfundingProgressModel model){
        return this.getCount("selectCount",model);
    }
    
    /**
     * 
     * Description: 根据node查询进展是否插入
     *
     * @param 
     * @return List<CrowdfundingProgressModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午3:10:02
     */
    public boolean isNeedAdd(String loanNo,String node){
        CrowdfundingProgressModel model = new CrowdfundingProgressModel();
        model.setLoanNo(loanNo);
        model.setEnterNode(node);
        long counts = this.getCount("getCountByNode", model);
        if(counts > 0){
            return false;
        }else{
            return true;
        }
        
    }
    
    /**
     * 
     * Description: 保存项目进展
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-13 下午5:21:13
     */
    public void saveProgress(String nodeCode,String loanNo,Map<String,String> params){
        try{
            boolean isOn = messageNodeDao.getNodeStatus(nodeCode);
            if(!isOn){
                return;
            }
            String  content = "";
            try{
                content = MessageUtils.getContent(nodeCode, params);
            }catch(Exception ew){
                ew.printStackTrace();
            }
            CrowdfundingProgressModel model = new CrowdfundingProgressModel();
            model.setId(PKGenarator.getId());
            model.setEnterNode(nodeCode);
            model.setLoanNo(loanNo);
            model.setEnterTime(new Date());
            model.setEnterContent(content);
            model.setTimeNode(new Date());
            model.setEnterUser(FbdCoreConstants.SYSTEM_OPERATOR);
            model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
            this.save(model);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
