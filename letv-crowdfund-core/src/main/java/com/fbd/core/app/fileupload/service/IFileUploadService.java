/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IFileUploadService.java 
 *
 * Created: [2016-8-18 下午4:35:52] by haolingfeng
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

import java.util.List;
import com.fbd.core.app.fileupload.model.FileUploadModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IFileUploadService {
    
    
    public List<FileUploadModel>selectList(FileUploadModel model);
    
    public FileUploadModel save(FileUploadModel model);
    
    public void deleteModel(String id);

}
