

package com.zs.modules.sys.service;

import com.zs.common.page.PageData;
import com.zs.common.service.BaseService;
import com.zs.modules.sys.dto.SysDictDataDTO;
import com.zs.modules.sys.entity.SysDictDataEntity;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

    PageData<SysDictDataDTO> page(Map<String, Object> params);

    SysDictDataDTO get(Long id);

    void save(SysDictDataDTO dto);

    void update(SysDictDataDTO dto);

    void delete(Long[] ids);

}