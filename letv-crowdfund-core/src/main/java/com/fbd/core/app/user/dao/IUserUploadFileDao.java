package com.fbd.core.app.user.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.base.BaseDao;

public interface IUserUploadFileDao extends BaseDao<UserUploadFileModel>{
    public UserUploadFileModel getByFileType(String userId,String fileType);
    
    public void updateByFileType(UserUploadFileModel model);
    
    public List<Map<String,Object>> getFilesByUserId(String userId);

    /**
     * Description: 
     *
     * @param 
     * @return UserUploadFileModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-3-11 下午12:08:46
     */
    
    public UserUploadFileModel selectByTypeAndUserId(UserUploadFileModel model);
}