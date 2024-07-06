

package com.zs.modules.log.service;


import com.zs.common.page.PageData;
import com.zs.common.service.BaseService;
import com.zs.modules.log.dto.SysLogErrorDTO;
import com.zs.modules.log.entity.SysLogErrorEntity;

import java.util.List;
import java.util.Map;

/**
 * 异常日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public interface SysLogErrorService extends BaseService<SysLogErrorEntity> {

    PageData<SysLogErrorDTO> page(Map<String, Object> params);

    List<SysLogErrorDTO> list(Map<String, Object> params);

    void save(SysLogErrorEntity entity);

}