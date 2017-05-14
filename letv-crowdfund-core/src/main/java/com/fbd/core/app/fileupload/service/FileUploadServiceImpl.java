/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FileUploadServiceImpl.java 
 *
 * Created: [2016-8-18 下午4:37:24] by haolingfeng
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

package com.fbd.core.app.fileupload.service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="fileUploadService")
public class FileUploadServiceImpl implements IFileUploadService {
    
    
    @Resource
    private IFileUploadDao fileUploadDao;
 
    @Override
    public List<FileUploadModel> selectList(FileUploadModel model) {
        return this.fileUploadDao.selectList(model);
    }
 
    @Override
    public FileUploadModel save(FileUploadModel model) {
        
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        this.fileUploadDao.save(model);
        return model;
    }
 
    @Override
    public void deleteModel(String id) {
        this.fileUploadDao.deleteByPrimaryKey(id);
    }

}
