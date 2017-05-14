package com.fbd.core.app.auth.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.auth.dao.IPermissionDao;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.core.base.BaseDaoImpl;

@Repository("permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<PermissionModel> implements IPermissionDao {

    private Logger logger = LoggerFactory.getLogger(PermissionDaoImpl.class);
    
    public int savePermission(PermissionModel permissionModel) {
        return this.save(permissionModel);
    }

    public List<PermissionModel> getPermissionList(
            PermissionModel permissionModel) {
        return this.selectByModel("select", permissionModel);
    }

    public List<PermissionModel> getPermission4Role(String roleId) {
        return this.selectByField("getPermission4Role", roleId);
    }

    public int removePermission(String id) {
        return this.deleteBatchById(id);
    }

    public int modifyPermission(PermissionModel permissionModel) {
        return this.updateBySelective(permissionModel);
    }

    /**
     * Description: 根据Id查询权限对象
     *
     * @param 
     * @return PermissionModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-12 下午4:30:49
     */
    public PermissionModel getPermissionById(String id) {
        return this.selectByPrimaryKey(id);
    }
    
    public PermissionModel getPermissionByCode(String code) {
    	return this.selectOneByField("selectByCode", code);
    }

    /**
     * Description: 排序权限列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-9 上午9:33:11
     */
    public void modifyOrderByPermission(String operate,Integer startSeqNum, Integer endSeqNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("operate", operate);
        map.put("startSeqNum", startSeqNum);
        map.put("endSeqNum", endSeqNum);
        this.sqlSession.update("updateOrderBy", map);
    }

    /**
     * Description: 得到父节点下最大的序列号
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-13 上午9:35:02
     */
    public int getMaxSeqNumByPid(String pid) {
        return this.sqlSession.selectOne("getMaxSeqNumByPid", pid);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.auth.dao.IPermissionDao#getLinkByCode(java.lang.String)
     */
    @Override
    public String getLinkByCode(String code) {
        // TODO Auto-generated method stub
        String result = "";
        if(StringUtils.isEmpty(code)){
            return result;
        }
        try {
            result = this.getPermissionByCode(code).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
