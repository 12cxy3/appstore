package com.zs.modules.app.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@Data
public class AppInfoExcel {
    @ExcelProperty(value = "id")
    private Long id;
    @ExcelProperty(value = "app名称")
    private String name;
    @ExcelProperty(value = "类型 0其他 1社交类、2资讯类、3游戏类、4购类、5工具类")
    private Integer type;
    @ExcelProperty(value = "介绍")
    private String introduce;
    @ExcelProperty(value = "大小")
    private String size;
    @ExcelProperty(value = "开发者")
    private String developer;
    @ExcelProperty(value = "路径")
    private String url;
    @ExcelProperty(value = "下载量")
    private Integer downNum;
    @ExcelProperty(value = "状态 0待审核 1审核成功 2审核拒绝")
    private Integer status;
    @ExcelProperty(value = "创建者")
    private Long creator;
    @ExcelProperty(value = "上传日期")
    private Date createDate;
    @ExcelProperty(value = "更新着")
    private Long updater;
    @ExcelProperty(value = "更新日期")
    private Date updateDate;

}