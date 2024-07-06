package com.zs.modules.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * app评论
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-10
 */
@Data
@ApiModel(value = "app评论")
public class AppCommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "")
	private Long appId;

	@ApiModelProperty(value = "评论")
	private String comment;

	@ApiModelProperty(value = "评分")
	private Integer score;

	@ApiModelProperty(value = "评分人")
	private Long creator;

	@ApiModelProperty(value = "评分时间")
	private Date createDate;

	private String headUrl;

	private String realName;


}