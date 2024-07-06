
package com.zs.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *  字典数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class DictData {
    @JsonIgnore
    private Long dictTypeId;
    private String dictLabel;
    private String dictValue;
}
