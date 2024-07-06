

package com.zs.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zs.common.constant.Constant;
import com.zs.common.page.PageData;
import com.zs.common.service.impl.BaseServiceImpl;
import com.zs.common.utils.ConvertUtils;
import com.zs.modules.security.password.PasswordUtils;
import com.zs.modules.security.user.SecurityUser;
import com.zs.modules.security.user.UserDetail;
import com.zs.modules.sys.dao.SysUserDao;
import com.zs.modules.sys.dto.SysUserDTO;
import com.zs.modules.sys.entity.SysUserEntity;
import com.zs.modules.sys.enums.SuperAdminEnum;
import com.zs.modules.sys.service.SysRoleUserService;
import com.zs.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    private final SysRoleUserService sysRoleUserService;

    @Override
    public PageData<SysUserDTO> page(Map<String, Object> params) {
        //转换成like
        paramsToLike(params, "username");

        //分页
        IPage<SysUserEntity> page = getPage(params, Constant.CREATE_DATE, false);

        //普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();
//        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
//            params.put("deptIdList", sysDeptService.getSubDeptIdList(user.getDeptId()));
//        }

        //查询
        List<SysUserEntity> list = baseDao.getList(params);

        return getPageData(list, page.getTotal(), SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();

        List<SysUserEntity> entityList = baseDao.getList(params);

        return ConvertUtils.sourceToTarget(entityList, SysUserDTO.class);
    }

    @Override
    public SysUserDTO get(Long id) {
        SysUserEntity entity = baseDao.getById(id);

        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        SysUserEntity entity = baseDao.getByUsername(username);
        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserDTO dto) {
        if (dto.getStatus() == null){
            dto.setStatus(1);
        }
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        String password = PasswordUtils.encode(entity.getPassword());
        entity.setPassword(password);

        //保存用户
        entity.setSuperAdmin(SuperAdminEnum.NO.value());
        insert(entity);

        List<Long> roleIdList = dto.getRoleIdList();
        if (roleIdList == null || roleIdList.size() ==0){
            roleIdList = new ArrayList<>();
            roleIdList.add(1800417541950947329L);
        }

        //保存角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), roleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        //密码加密
        if (StrUtil.isBlank(dto.getPassword())) {
//            entity.setPassword(null);
        } else {
            String password = PasswordUtils.encode(entity.getPassword());
            entity.setPassword(password);
        }

        //更新用户
        updateById(entity);

        if (dto.getRoleIdList() != null && dto.getRoleIdList().size() != 0){
            //更新角色用户关系
            sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
        }

    }

    @Override
    public void delete(Long[] ids) {
        //删除用户
        baseDao.deleteBatchIds(Arrays.asList(ids));

        //删除角色用户关系
        sysRoleUserService.deleteByUserIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long id, String newPassword) {
        newPassword = PasswordUtils.encode(newPassword);

        baseDao.updatePassword(id, newPassword);
    }

}
