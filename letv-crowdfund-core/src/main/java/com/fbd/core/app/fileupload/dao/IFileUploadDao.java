package com.fbd.core.app.fileupload.dao;

import java.util.List;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.base.BaseDao;

public interface IFileUploadDao extends BaseDao<FileUploadModel> { 
    
    public List<FileUploadModel>selectList(FileUploadModel model);
    
    
    public int deleteByParentId(String parentId);
    
    public void updateParentIdByParentId(FileUploadModel model);
}