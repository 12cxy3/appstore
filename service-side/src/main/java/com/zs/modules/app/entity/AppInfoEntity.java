package com.zs.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * App管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2024-06-09
 */
@Data
@TableName("app_info")
public class AppInfoEntity {

    /**
     * id
     */
	private Long id;
    /**
     * app名称
     */
	private String name;

    private String icon;
    /**
     * 类型 0其他 1社交类、2资讯类、3游戏类、4购类、5工具类
     */
	private Integer type;
    /**
     * 介绍
     */
	private String introduce;
    /**
     * 大小
     */
	private String size;
    /**
     * 开发者
     */
	private String developer;
    /**
     * 路径
     */
	private String url;
    /**
     * 下载量
     */
	private Integer downNum;
    /**
     * 状态 0待审核 1审核成功 2审核拒绝
     */
	private Integer status;
    /**
     * 创建者
     */
	private Long creator;
    /**
     * 上传日期
     */
	private Date createDate;
    /**
     * 更新着
     */
	private Long updater;
    /**
     * 更新日期
     */
	private Date updateDate;
}