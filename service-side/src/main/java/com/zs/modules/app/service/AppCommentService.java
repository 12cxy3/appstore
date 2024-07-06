package com.zs.modules.app.service;

import com.zs.common.page.PageData;
import com.zs.common.service.CrudService;
import com.zs.modules.app.dto.AppCommentDTO;
import com.zs.modules.app.entity.AppCommentEntity;

import java.util.Map;

/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
public interface AppCommentService extends CrudService<AppCommentEntity, AppCommentDTO> {

    int commitCount(Long appId, Long userId);

    PageData<AppCommentDTO> getPage(Map<String, Object> params);
}