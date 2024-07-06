package com.zs.modules.app.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zs.common.dao.BaseDao;
import com.zs.modules.app.dto.AppInfoDTO;
import com.zs.modules.app.entity.AppInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@Mapper
public interface AppInfoDao extends BaseDao<AppInfoEntity> {

    IPage<AppInfoDTO> getPage(IPage<AppInfoEntity> page, @Param("ew") QueryWrapper<AppInfoEntity> wrapper);
}