

package com.zs.modules.log.service;

import com.zs.common.page.PageData;
import com.zs.common.service.BaseService;
import com.zs.modules.log.dto.SysLogOperationDTO;
import com.zs.modules.log.entity.SysLogOperationEntity;

import java.util.List;
import java.util.Map;

/**
 * 操作日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public interface SysLogOperationService extends BaseService<SysLogOperationEntity> {

    PageData<SysLogOperationDTO> page(Map<String, Object> params);

    List<SysLogOperationDTO> list(Map<String, Object> params);

    void save(SysLogOperationEntity entity);
}