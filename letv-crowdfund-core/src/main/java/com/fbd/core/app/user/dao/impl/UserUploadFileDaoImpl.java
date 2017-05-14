/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserUploadFileDaoImpl.java 
 *
 * Created: [2015-5-28 上午11:07:54] by haolingfeng
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserUploadFileDao;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("userUploadFileDao")
public class UserUploadFileDaoImpl extends BaseDaoImpl<UserUploadFileModel>
 implements IUserUploadFileDao{

    
    public UserUploadFileModel getByFileType(String userId,String fileType){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId",userId);
        paramMap.put("fileType", fileType);
       List<UserUploadFileModel> files = this.selectByFields("selectByFileType", paramMap);
       if(files.size()>0){
           return files.get(0);
       }else{
           return null;
       }
    }
    
    public void updateByFileType(UserUploadFileModel model){
        this.update("updateByFileType",model);
    }
    
    public List<Map<String,Object>> getFilesByUserId(String userId){
        return this.selectMapByFields("selectUserFiles", userId);
    }

    public UserUploadFileModel selectByTypeAndUserId(UserUploadFileModel model) {
        return this.selectOneByField("selectByTypeAndUserId", model);
    }
}
