package com.zs.modules.app.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@Data
public class AppCommentExcel {
    @ExcelProperty(value = "")
    private Long id;
    @ExcelProperty(value = "")
    private Long appId;
    @ExcelProperty(value = "评论")
    private String comment;
    @ExcelProperty(value = "评分")
    private Integer score;
    @ExcelProperty(value = "评分人")
    private Long creator;
    @ExcelProperty(value = "评分时间")
    private Date createDate;

}