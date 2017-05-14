/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FileUploadDaoImpl.java 
 *
 * Created: [2016-8-18 下午4:26:02] by haolingfeng
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

package com.fbd.core.app.fileupload.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository(value="fileUploadDao")
public class FileUploadDaoImpl extends BaseDaoImpl<FileUploadModel> implements IFileUploadDao {
    
    
    public List<FileUploadModel>selectList(FileUploadModel model){
        return this.selectByField("selectList",model);
    }
    
    public int deleteByParentId(String parentId){
        return this.deleteByField("deleteByParentId",parentId);
    }
    
    public void updateParentIdByParentId(FileUploadModel model){
         this.update("updateParentIdByParentId", model);
    }
 

}
