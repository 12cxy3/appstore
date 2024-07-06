package com.zs.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@Data
@TableName("app_comment")
public class AppCommentEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 
     */
	private Long appId;
    /**
     * 评论
     */
	private String comment;
    /**
     * 评分
     */
	private Integer score;
    /**
     * 评分人
     */
	private Long creator;
    /**
     * 评分时间
     */
	private Date createDate;
}