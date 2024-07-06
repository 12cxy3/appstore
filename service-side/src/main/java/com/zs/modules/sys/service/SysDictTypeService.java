

package com.zs.modules.sys.service;

import com.zs.common.page.PageData;
import com.zs.common.service.BaseService;
import com.zs.modules.sys.dto.SysDictTypeDTO;
import com.zs.modules.sys.entity.DictType;
import com.zs.modules.sys.entity.SysDictTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageData<SysDictTypeDTO> page(Map<String, Object> params);

    SysDictTypeDTO get(Long id);

    void save(SysDictTypeDTO dto);

    void update(SysDictTypeDTO dto);

    void delete(Long[] ids);

    /**
     * 获取所有字典
     */
    List<DictType> getAllList();

}