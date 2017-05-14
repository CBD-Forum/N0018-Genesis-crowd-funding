/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserSecurityLevelDaoImpl.java 
 *
 * Created: [2015-2-14 上午10:36:32] by haolingfeng
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

package com.fbd.core.app.user.dao.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserSecurityLevelDao;
import com.fbd.core.app.user.model.UserSecurityLevelModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.FbdCoreConstants;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户安全级别
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("userSecurityLevelDao")
public class UserSecurityLevelDaoImpl extends BaseDaoImpl<UserSecurityLevelModel> 
implements IUserSecurityLevelDao{

    /**
     * 
     * Description: 根据用户id查询用户安全级别
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-14 上午11:17:33
     */
    public UserSecurityLevelModel getByUserId(String userId,String userType){
        UserSecurityLevelModel model = new UserSecurityLevelModel();
        model.setUserId(userId);
        model.setUserType(userType);
        List<UserSecurityLevelModel> result = this.selectByModel("selectByModel", model);
        if(result.size()>0){
            return result.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 
     * Description: 查询安全级别信息，信息比较多
     *
     * @param 
     * @return UserSecurityLevelModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-14 上午11:32:24
     */
    public UserSecurityLevelModel getByModel(String userId,String userType){
        UserSecurityLevelModel model = new UserSecurityLevelModel();
        model.setUserId(userId);
        model.setUserType(userType);
        List<UserSecurityLevelModel> result = this.selectByModel("selectMoreByModel", model);
        if(result.size()>0){
            UserSecurityLevelModel obj =  result.get(0);
            if(StringUtils.isEmpty(obj.getThirdAccount())){
                obj.setThirdAccountState(FbdCoreConstants.userSecurityState.noopen);
                obj.setCertificateNoState(FbdCoreConstants.userSecurityState.noAuth);
            }else{
                obj.setCertificateNoState(FbdCoreConstants.userSecurityState.authed);
                obj.setThirdAccountState(FbdCoreConstants.userSecurityState.authed);
            }
            
            if(!StringUtils.isEmpty(obj.getIsAuth()) && "1".equals(obj.getIsAuth())){
                obj.setThirdAccountState(FbdCoreConstants.userSecurityState.authed);
            }
            
            if(StringUtils.isEmpty(obj.getEmergencyContact())){
                obj.setEmergencyState(FbdCoreConstants.userSecurityState.noAuth);
            }else{
                obj.setEmergencyState(FbdCoreConstants.userSecurityState.authed);
            }
            
            if(StringUtils.isEmpty(obj.getHomeAddress())){
                obj.setHomeAddressState(FbdCoreConstants.userSecurityState.noAuth);
            }else{
                obj.setHomeAddressState(FbdCoreConstants.userSecurityState.authed);
            }
            return obj;
            
        }else{
            return null;
        }
    }
    
}
