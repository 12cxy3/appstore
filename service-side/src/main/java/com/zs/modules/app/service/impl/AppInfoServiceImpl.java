package com.zs.modules.app.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zs.common.page.PageData;
import com.zs.common.service.impl.CrudServiceImpl;
import com.zs.modules.app.dao.AppInfoDao;
import com.zs.modules.app.dto.AppInfoDTO;
import com.zs.modules.app.entity.AppInfoEntity;
import com.zs.modules.app.service.AppInfoService;
import cn.hutool.core.util.StrUtil;
import com.zs.modules.security.user.SecurityUser;
import com.zs.modules.security.user.UserDetail;
import com.zs.modules.sys.dao.SysRoleUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@Service
public class AppInfoServiceImpl extends CrudServiceImpl<AppInfoDao, AppInfoEntity, AppInfoDTO> implements AppInfoService {

    @Resource
    private SysRoleUserDao sysRoleUserDao;

    @Override
    public QueryWrapper<AppInfoEntity> getWrapper(Map<String, Object> params){
        String status = (String)params.get("status");
        String appName = (String)params.get("appName");
        String type = (String)params.get("type");
        Boolean allShow = MapUtil.getBool(params,"allShow");
        if (allShow == null){
            allShow = false;
        }

        QueryWrapper<AppInfoEntity> wrapper = new QueryWrapper<>();
        UserDetail user = SecurityUser.getUser();
        final List<Long> roleIdList = sysRoleUserDao.getRoleIdList(user.getId());
        if (user.getSuperAdmin() != 1 && !allShow && !roleIdList.contains(100L)){
            wrapper.eq("t1.creator",user.getId());
        }
        wrapper.eq(StrUtil.isNotBlank(status), "t1.status", status)
                .like(StrUtil.isNotBlank(appName),"t1.name",appName)
                .eq(StrUtil.isNotBlank(type),"t1.type",type)
                .groupBy("t1.id")
                .orderByAsc("t1.status")
                .orderByDesc("t1.down_num");

        return wrapper;
    }


    @Override
    public PageData<AppInfoDTO> getPage(Map<String, Object> params) {
        IPage<AppInfoDTO> page = baseDao.getPage(getPage(params,null,false),getWrapper(params));
        return getPageData(page,currentDtoClass());
    }

    @Override
    public void audit(AppInfoDTO dto) {
        Long id = dto.getId();
        Integer status = dto.getStatus();
        UpdateWrapper<AppInfoEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id)
                        .set("status",status);
        baseDao.update(new AppInfoEntity(),wrapper);
    }
}