

package com.zs.modules.sys.dao;

import com.zs.common.dao.BaseDao;
import com.zs.modules.sys.entity.SysRoleDataScopeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Mapper
public interface SysRoleDataScopeDao extends BaseDao<SysRoleDataScopeEntity> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> getDeptIdList(Long roleId);

    /**
     * 获取用户的部门数据权限列表
     */
    List<Long> getDataScopeList(Long userId);

    /**
     * 根据角色id，删除角色数据权限关系
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(Long[] roleIds);
}