package com.fbd.admin.app.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.user.service.IUserService;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Service(value="userService")
//@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    public IUserDao userDao;
    
    //用户安全表
    @Resource
    private IUserSecurityDao userSecurityDao;
    //用户等级
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    @Resource
    private IFileUploadDao fileUploadDao;

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public List<Map<String, Object>> getUserList(UserModel user) {
        return userDao.getUserList(user);
    }

    /**
     * Description: 分页查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public SearchResult<Map<String, Object>> getUserPage(UserModel user) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(getUserCount(user));
        searchResult.setRows(getUserList(user));
        return searchResult;
    }
    
    /**
     * Description: 分页查询众筹用户列表
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public SearchResult<Map<String, Object>> getCrowdfundUserPage(UserModel user) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(userDao.getCrowdfundUserCount(user));
        searchResult.setRows(userDao.getCrowdfundUserList(user));
        return searchResult;
    }

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)的总条数
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public long getUserCount(UserModel user) {
        return userDao.getUserCount(user);
    }

    /**
     * 
     * Description: 根据userId查询用户
     * 
     * @param
     * @return UserModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 上午9:47:18
     */
    public UserModel getUser(String userId) {
        UserModel user = this.userDao.findByUserId(userId);
        return user;
    }

    /**
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-2 下午9:41:02
     */
    public int modifyUserSecurity(UserSecurityModel userSecurity) {
//        userSecurity.setUserType(FbdCoreConstants.userType.P);
        if (FbdCoreConstants.userStatus.disable.equals(userSecurity.getStatus())) {
            userSecurity.setDisableTime(new Date());
        }
        return userSecurityDao.modifyUserSecurity(userSecurity);
    }

    /**
     * Description: 查询用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    public Map<String, Object> getUserDetail(String userId) {
        return userDao.getUserDetail(userId);
    }

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 上午10:24:48
     */
    public int modifyUserPassword(String id) {
        int num = userDao.modifyUserPassword(id);
        if (num == 1) {
            //审计日志
            try {
                UserModel userModel = userDao.getUserById(id);
                AuditLogUtils.log(AuditLogConstants.MODEL_INVESTUSER, AuditLogConstants.TYPE_RESETPASSWORD, AuditLogConstants.RESULT_SUCCESS,"用户："+userModel.getUserId()+"，密码重置为身份证后六位。");
            } catch (Exception e) {
                
            }
        }
        return num;
    }

    /**
     * Description: 根据Id查询用户实体信息
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:16:03
     */
    public UserModel getUserById(String id) {
        return userDao.getUserById(id);
    }

    /**
     * Description: 修改用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:39:34
     */
    public int modifyUser(UserModel user) {
        int num = userDao.modifyUser(user);
        if (num == 1) {
            //审计日志
            try {
                AuditLogUtils.log(AuditLogConstants.MODEL_INVESTUSER, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"用户："+user.getUserId()+"，信息被修改。");
            } catch (Exception e) {
               
            }
        }
        
        //同步修改用户认证的信息
        CrowdfundUserStuffModel stuff = new CrowdfundUserStuffModel();
        stuff.setUserId(user.getUserId());
        stuff.setCompanyName(user.getRealName());
        stuff.setCertNo(user.getCertificateNo());
        stuff.setLendAuthPhoto(user.getPhoto());
        this.crowdfundUserStuffDao.updateByUserId(stuff);
        
        return num;
    }
    
   
    /**
     * Description: 分页查询用户的资金统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    public SearchResult<Map<String, Object>> getUserCaptialPage(UserModel user) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(this.userDao.getUserCapitalListCount(user));
        searchResult.setRows(this.userDao.getUserCapitalList(user));
        return searchResult;
    }
    
    /**
     * Description: 参数是map 的分页查询
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-5 上午11:48:20
     */
    public SearchResult<Map<String, Object>> getUserCaptialPage(Map<String, Object> param) {
        return userDao.getPage("getUserCapitalListCount", "getUserCapitalList", param);
    }

    /**
     * Description:根据邮箱查询用户对象 
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午4:24:59
     */
    public UserModel getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Description: 根据手机号查询用户对象
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午4:25:11
     */
    public UserModel getUserByMobile(String mobile) {
        return userDao.findUserByMobile(mobile);
    }

   
    
    /**
     * Description: 用户统计-重点客户统计
     */
    public SearchResult<Map<String, Object>> getVipCustomerPage(Map<String, Object> paramMap) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(this.userDao.getVipCustomerListCount(paramMap));
        searchResult.setRows(this.userDao.getVipCustomerList(paramMap));
        return searchResult;
    }

    public SearchResult<Map<String, Object>> getRechargStatisticlist(Map<String, Object> param) {
        //获取时间
        Calendar c = Calendar.getInstance();
        int year = 0;
        int month = 0;
        int day = 0;
        if (param.get("year")!=null) {
            year = Integer.parseInt(param.get("year").toString());
            c.set(Calendar.YEAR, year);
        }else{
            year= c.get(Calendar.YEAR);
        }
        
        if (param.get("month")!=null) {
            month = Integer.parseInt(param.get("month").toString());
            c.set(Calendar.MONTH, month-1);
        }else{
            month = c.get(Calendar.MONTH)+1;
        }
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
        result.setTotal(maxDay);
        List<Map<String, Object>>  resultList = new ArrayList<Map<String,Object>>();
        
        if (param.get("day")!=null) {
            day = Integer.parseInt(param.get("day").toString());
            c.set(Calendar.DAY_OF_MONTH, day);
        }else{
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        
        
        //每一页显示的条数
        String everyDay="";
        resultList.add(this.userDao.selectOneMapByField("getRechargStatisticlist", everyDay));
        for (int i = maxDay; i > 0; i--) {
            everyDay=year+"-"+month+"-"+i;
            resultList.add(this.userDao.selectOneMapByField("getRechargStatisticlist", everyDay));
        }
        result.setRows(resultList);
        
        return result;
    }
    
    /**
     * Description: 用户统计-潜在客户统计
     */
    public SearchResult<Map<String, Object>> getPotentialCustomerPage(Map<String, Object> paramMap) {
    	SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
    	searchResult.setTotal(this.userDao.getPotentialCustomerListCount(paramMap));
    	searchResult.setRows(this.userDao.getPotentialCustomerList(paramMap));
    	return searchResult;
    }
    
    /**
     * Description: 用户统计-按投资年龄范围统计
     */
    public SearchResult<Map<String, Object>> getInvestAge(Map<String, Object> paramMap) {
    	SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
    	searchResult.setRows(this.userDao.getInvestAgeList(paramMap));
    	return searchResult;
    }
    
    /**
     * Description: 查询众筹用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    public Map<String, Object> getCrowdfundUserDetail(String userId) {
        return userDao.getCrowdfundUserDetail(userId);
    }

    /**
     * Description: 删除用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public int removeUser(String id) {
		// TODO Auto-generated method stub
		return userDao.deleteByField("deleteUserById", id);
	}

	/**
     * Description: 修改用户等级
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
	public void modifyUserGrade(CrowdfundUserStuffModel model) {
		// TODO Auto-generated method stub
		this.crowdfundUserStuffDao.updateByUserId(model);
	}

	/**
     * Description: 修改用户类型（是否为明星）
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public void modifyUserIsCelebrity(UserModel model) {
		this.userDao.updateByUserId(model);
	}
	
    /**
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午3:18:47
     */
    
    public CrowdfundUserStuffModel getCrowdfundUserAuth(
            CrowdfundUserStuffModel model){
    	
    	CrowdfundUserStuffModel crowStuff = this.crowdfundUserStuffDao.getCrowdfundUserAuth(model);
    	
    	
		if(("orgLeadInvestor".equals(model.getAuthType()) || "orgInvestor".equals(model.getAuthType())) && crowStuff!=null){  //如果认证类型是机构的查询机构相关的文件信息
			//机构认证中机构证件
			FileUploadModel orgCardPhoto = new FileUploadModel();
			orgCardPhoto.setParentId(crowStuff.getId());
			orgCardPhoto.setType("orgCardPhoto");
    		List<FileUploadModel>  orgCardPhotoList = this.fileUploadDao.selectList(orgCardPhoto);
    		crowStuff.setOrgCardPhotoList(orgCardPhotoList);
    		
    		//机构资产认证资料
			FileUploadModel orgAssetsCredentials = new FileUploadModel();
			orgAssetsCredentials.setParentId(crowStuff.getId());
			orgAssetsCredentials.setType("orgAssetsCredentials");
    		List<FileUploadModel>  orgAssetsCredentialsList = this.fileUploadDao.selectList(orgAssetsCredentials);
    		crowStuff.setOrgAssetsCredentialsList(orgAssetsCredentialsList);
    		
    		//机构历史投资证明
			FileUploadModel orgHistoricalInvestMent = new FileUploadModel();
			orgHistoricalInvestMent.setParentId(crowStuff.getId());
			orgHistoricalInvestMent.setType("orgHistoricalInvestMent");
    		List<FileUploadModel>  orgHistoricalInvestMentList = this.fileUploadDao.selectList(orgHistoricalInvestMent);
    		crowStuff.setOrgHistoricalInvestMentList(orgHistoricalInvestMentList);
		}else if(("leadInvestor".equals(model.getAuthType()) || "investor".equals(model.getAuthType()))  && crowStuff!=null){
			
			//个人资产认证资料
			FileUploadModel assetsCredentials = new FileUploadModel();
			assetsCredentials.setParentId(crowStuff.getId());
			assetsCredentials.setType("assetsCredentials");
    		List<FileUploadModel>  assetsCredentialsList = this.fileUploadDao.selectList(assetsCredentials);
    		crowStuff.setAssetsCredentialsList(assetsCredentialsList);
    		
    		//历史投资资料
			FileUploadModel historicalInvestMent = new FileUploadModel();
			historicalInvestMent.setParentId(crowStuff.getId());
			historicalInvestMent.setType("historicalInvestMent");
    		List<FileUploadModel>  orgAssetsCredentialsList = this.fileUploadDao.selectList(historicalInvestMent);
    		crowStuff.setHistoricalInvestMentList(orgAssetsCredentialsList);
		}
		return crowStuff;
    }
    
    /**
     * 查询所有用户列表
     * Description: 
     * @param 
     * @return List<UserModel>
     * @throws 
     * Create Date: 2016-11-14 下午2:11:54
     */
    public List<UserModel> selectAllUser(UserModel user){
    	return this.userDao.selectAllUser(user);
    }
}
