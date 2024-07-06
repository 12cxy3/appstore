package com.zs.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zs.common.page.PageData;
import com.zs.common.service.impl.CrudServiceImpl;
import com.zs.modules.app.dao.AppCommentDao;
import com.zs.modules.app.dto.AppCommentDTO;
import com.zs.modules.app.entity.AppCommentEntity;
import com.zs.modules.app.service.AppCommentService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@Service
public class AppCommentServiceImpl extends CrudServiceImpl<AppCommentDao, AppCommentEntity, AppCommentDTO> implements AppCommentService {

    @Override
    public QueryWrapper<AppCommentEntity> getWrapper(Map<String, Object> params){
        String appId = (String)params.get("appId");

        QueryWrapper<AppCommentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(appId), "app_id", appId);
        return wrapper;
    }


    @Override
    public int commitCount(Long appId, Long userId) {
        return baseDao.selectByUserIdAndAppId(userId,appId);
    }

    @Override
    public PageData<AppCommentDTO> getPage(Map<String, Object> params) {
        IPage<AppCommentDTO> page = baseDao.getPage(getPage(params,null,false),getWrapper(params));
        return getPageData(page,currentDtoClass());
    }
}