package com.zs.modules.app.service;

import com.zs.common.page.PageData;
import com.zs.common.service.CrudService;
import com.zs.modules.app.dto.AppInfoDTO;
import com.zs.modules.app.entity.AppInfoEntity;

import java.util.Map;

/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
public interface AppInfoService extends CrudService<AppInfoEntity, AppInfoDTO> {

    PageData<AppInfoDTO> getPage(Map<String, Object> params);

    void audit(AppInfoDTO dto);
}