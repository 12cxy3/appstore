package com.zs.modules.app.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zs.common.dao.BaseDao;
import com.zs.modules.app.dto.AppCommentDTO;
import com.zs.modules.app.entity.AppCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@Mapper
public interface AppCommentDao extends BaseDao<AppCommentEntity> {

    int selectByUserIdAndAppId(@Param("userId") Long userId, @Param("appId") Long appId);

    IPage<AppCommentDTO> getPage(IPage<AppCommentEntity> page, @Param("ew") QueryWrapper<AppCommentEntity> wrapper);
}