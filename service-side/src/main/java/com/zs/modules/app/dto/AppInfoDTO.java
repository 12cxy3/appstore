package com.zs.modules.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@Data
@ApiModel(value = "App管理")
public class AppInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "app名称")
	private String name;

	@ApiModelProperty(value = "图标")
	private String icon;


	@ApiModelProperty(value = "类型 0其他 1社交类、2资讯类、3游戏类、4购类、5工具类")
	private Integer type;

	@ApiModelProperty(value = "介绍")
	private String introduce;

	@ApiModelProperty(value = "大小")
	private String size;

	@ApiModelProperty(value = "开发者")
	private String developer;

	@ApiModelProperty(value = "路径")
	private String url;

	@ApiModelProperty(value = "下载量")
	private Integer downNum;

	private Double scoreNum;

	private Integer commentNum;

	@ApiModelProperty(value = "状态 0待审核 1审核成功 2审核拒绝")
	private Integer status;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建者")
	private String createName;


	@ApiModelProperty(value = "上传日期")
	private Date createDate;

	@ApiModelProperty(value = "更新着")
	private Long updater;

	@ApiModelProperty(value = "更新日期")
	private Date updateDate;


}