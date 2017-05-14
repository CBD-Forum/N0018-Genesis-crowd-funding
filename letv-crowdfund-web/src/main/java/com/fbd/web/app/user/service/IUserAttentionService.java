/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserAttentionService.java 
 *
 * Created: [2015-6-23 上午10:24:15] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.user.service;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.user.model.UserAttentionModel;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IUserAttentionService {
    
    /**
     * 
     * Description:保存关注关系表 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-23 上午10:31:31
     */
    public void saveAttention(UserAttentionModel model);
    
    
    /**
     * 
     * Description: 查询关注了哪些人与哪些人关注了我
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-23 上午10:38:00
     */
    public List<Map<String,Object>> getAttentionUser(UserAttentionModel model);

}
