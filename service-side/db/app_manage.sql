/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 8.0.22 : Database - app_manage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`app_manage` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `app_manage`;

/*Table structure for table `app_comment` */

DROP TABLE IF EXISTS `app_comment`;

CREATE TABLE `app_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app_id` bigint NOT NULL,
  `comment` varchar(1024) NOT NULL COMMENT '评论',
  `score` int NOT NULL DEFAULT '0' COMMENT '评分',
  `creator` bigint DEFAULT NULL COMMENT '评分人',
  `create_date` datetime DEFAULT NULL COMMENT '评分时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1800769622587146242 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app评论';

/*Data for the table `app_comment` */

/*Table structure for table `app_info` */

DROP TABLE IF EXISTS `app_info`;

CREATE TABLE `app_info` (
  `id` bigint NOT NULL COMMENT 'id',
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `name` varchar(256) DEFAULT NULL COMMENT 'app名称',
  `type` int DEFAULT '0' COMMENT '类型 0其他 1社交类、2资讯类、3游戏类、4购类、5工具类',
  `introduce` varchar(1024) DEFAULT NULL COMMENT '介绍',
  `size` varchar(125) DEFAULT NULL COMMENT '大小',
  `developer` varchar(256) DEFAULT NULL COMMENT '开发者',
  `url` varchar(256) DEFAULT NULL COMMENT '路径',
  `down_num` int DEFAULT '0' COMMENT '下载量',
  `status` int DEFAULT '0' COMMENT '状态 0待审核 1审核成功 2审核拒绝',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '上传日期',
  `updater` bigint DEFAULT NULL COMMENT '更新着',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='App管理';

/*Data for the table `app_info` */

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) DEFAULT NULL COMMENT '字典值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int unsigned DEFAULT NULL COMMENT '排序',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type_value` (`dict_type_id`,`dict_value`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据';

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`id`,`dict_type_id`,`dict_label`,`dict_value`,`remark`,`sort`,`creator`,`create_date`,`updater`,`update_date`) values 
(1160061112075464705,1160061077912858625,'男','0','',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1160061146967879681,1160061077912858625,'女','1','',1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1160061190127267841,1160061077912858625,'保密','2','',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1225814069634195457,1225813644059140097,'公告','0','',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1225814107559092225,1225813644059140097,'会议','1','',1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1225814271879340034,1225813644059140097,'其他','2','',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34');

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int unsigned DEFAULT NULL COMMENT '排序',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型';

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`id`,`dict_type`,`dict_name`,`remark`,`sort`,`creator`,`create_date`,`updater`,`update_date`) values 
(1160061077912858625,'gender','性别','',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34');

/*Table structure for table `sys_log_error` */

DROP TABLE IF EXISTS `sys_log_error`;

CREATE TABLE `sys_log_error` (
  `id` bigint NOT NULL COMMENT 'id',
  `request_uri` varchar(200) DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(20) DEFAULT NULL COMMENT '请求方式',
  `request_params` text COMMENT '请求参数',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `ip` varchar(32) DEFAULT NULL COMMENT '操作IP',
  `error_info` text COMMENT '异常信息',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='异常日志';

/*Data for the table `sys_log_error` */

/*Table structure for table `sys_log_login` */

DROP TABLE IF EXISTS `sys_log_login`;

CREATE TABLE `sys_log_login` (
  `id` bigint NOT NULL COMMENT 'id',
  `operation` tinyint unsigned DEFAULT NULL COMMENT '用户操作   0：用户登录   1：用户退出',
  `status` tinyint unsigned NOT NULL COMMENT '状态  0：失败    1：成功    2：账号已锁定',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `ip` varchar(32) DEFAULT NULL COMMENT '操作IP',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志';

/*Data for the table `sys_log_login` */

/*Table structure for table `sys_log_operation` */

DROP TABLE IF EXISTS `sys_log_operation`;

CREATE TABLE `sys_log_operation` (
  `id` bigint NOT NULL COMMENT 'id',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `request_uri` varchar(200) DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(20) DEFAULT NULL COMMENT '请求方式',
  `request_params` text COMMENT '请求参数',
  `request_time` int unsigned NOT NULL COMMENT '请求时长(毫秒)',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `ip` varchar(32) DEFAULT NULL COMMENT '操作IP',
  `status` tinyint unsigned NOT NULL COMMENT '状态  0：失败   1：成功',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志';

/*Data for the table `sys_log_operation` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL COMMENT 'id',
  `pid` bigint DEFAULT NULL COMMENT '上级ID，一级菜单为0',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `permissions` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:list,sys:user:save)',
  `menu_type` tinyint unsigned DEFAULT NULL COMMENT '类型   0：菜单   1：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `sort` int DEFAULT NULL COMMENT '排序',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单管理';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`pid`,`name`,`url`,`permissions`,`menu_type`,`icon`,`sort`,`creator`,`create_date`,`updater`,`update_date`) values 
(1067246875800000002,0,'权限管理',NULL,NULL,0,'icon-safetycertificate',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000003,1067246875800000055,'新增',NULL,'sys:user:save,sys:dept:list,sys:role:list',1,NULL,1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000004,1067246875800000055,'修改',NULL,'sys:user:update,sys:dept:list,sys:role:list',1,NULL,2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000005,1067246875800000055,'删除',NULL,'sys:user:delete',1,NULL,3,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000006,1067246875800000055,'导出',NULL,'sys:user:export',1,NULL,4,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000007,1067246875800000002,'角色管理','sys/role',NULL,0,'icon-team',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000008,1067246875800000007,'查看',NULL,'sys:role:page,sys:role:info',1,NULL,0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000009,1067246875800000007,'新增',NULL,'sys:role:save,sys:menu:select,sys:dept:list',1,NULL,1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000010,1067246875800000007,'修改',NULL,'sys:role:update,sys:menu:select,sys:dept:list',1,NULL,2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000011,1067246875800000007,'删除',NULL,'sys:role:delete',1,NULL,3,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000025,1067246875800000035,'菜单管理','sys/menu',NULL,0,'icon-unorderedlist',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000026,1067246875800000025,'查看',NULL,'sys:menu:list,sys:menu:info',1,NULL,0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000027,1067246875800000025,'新增',NULL,'sys:menu:save',1,NULL,1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000028,1067246875800000025,'修改',NULL,'sys:menu:update',1,NULL,2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000029,1067246875800000025,'删除',NULL,'sys:menu:delete',1,NULL,3,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000035,0,'系统设置',NULL,NULL,0,'icon-setting',1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000041,1067246875800000035,'字典管理','sys/dict-type',NULL,0,'icon-golden-fill',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000042,1067246875800000041,'查看',NULL,'sys:dict:page,sys:dict:info',1,NULL,0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000043,1067246875800000041,'新增',NULL,'sys:dict:save',1,NULL,1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000044,1067246875800000041,'修改',NULL,'sys:dict:update',1,NULL,2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000045,1067246875800000041,'删除',NULL,'sys:dict:delete',1,NULL,3,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000046,0,'日志管理',NULL,NULL,0,'icon-container',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000048,1067246875800000046,'登录日志','sys/log-login','sys:log:login',0,'icon-filedone',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000049,1067246875800000046,'操作日志','sys/log-operation','sys:log:operation',0,'icon-solution',1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000050,1067246875800000046,'异常日志','sys/log-error','sys:log:error',0,'icon-file-exception',2,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000055,1067246875800000002,'用户管理','sys/user',NULL,0,'icon-user',0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1067246875800000056,1067246875800000055,'查看',NULL,'sys:user:page,sys:user:info',1,NULL,0,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34'),
(1799706754282602497,0,'App管理','app/appinfo',NULL,0,'icon-build',0,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-10 21:45:54'),
(1799706754282602498,1799706754282602497,'查看',NULL,'app:appinfo:page,app:appinfo:info',1,NULL,0,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-09 15:40:27'),
(1799706754282602499,1799706754282602497,'新增',NULL,'app:appinfo:save',1,NULL,1,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-09 15:40:27'),
(1799706754282602500,1799706754282602497,'修改',NULL,'app:appinfo:update',1,NULL,2,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-09 15:40:27'),
(1799706754282602501,1799706754282602497,'删除',NULL,'app:appinfo:delete',1,NULL,3,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-09 15:40:27'),
(1799706754282602502,1799706754282602497,'导出',NULL,'app:appinfo:export',1,NULL,4,1067246875800000001,'2024-06-09 15:40:27',1067246875800000001,'2024-06-09 15:40:27'),
(1799812484224995330,1799706754282602497,'审核','','app:appinfo:audit',1,'',0,1067246875800000001,'2024-06-09 22:35:10',1067246875800000001,'2024-06-09 22:35:10');

/*Table structure for table `sys_oss` */

DROP TABLE IF EXISTS `sys_oss`;

CREATE TABLE `sys_oss` (
  `id` bigint NOT NULL COMMENT 'id',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件上传';

/*Data for the table `sys_oss` */

/*Table structure for table `sys_params` */

DROP TABLE IF EXISTS `sys_params`;

CREATE TABLE `sys_params` (
  `id` bigint NOT NULL COMMENT 'id',
  `param_code` varchar(32) DEFAULT NULL COMMENT '参数编码',
  `param_value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `param_type` tinyint unsigned DEFAULT '1' COMMENT '类型   0：系统参数   1：非系统参数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_param_code` (`param_code`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数管理';

/*Data for the table `sys_params` */

insert  into `sys_params`(`id`,`param_code`,`param_value`,`param_type`,`remark`,`creator`,`create_date`,`updater`,`update_date`) values 
(1067246875800000073,'CLOUD_STORAGE_CONFIG_KEY','{\"type\":1,\"qiniuDomain\":\"http://test.oss.renren.io\",\"qiniuPrefix\":\"upload\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"qiniuBucketName\":\"renren-oss\",\"aliyunDomain\":\"\",\"aliyunPrefix\":\"\",\"aliyunEndPoint\":\"\",\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\"}',0,'云存储配置信息',1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-09 11:49:34');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色管理';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`remark`,`dept_id`,`creator`,`create_date`,`updater`,`update_date`) values 
(100,'管理员',NULL,NULL,1067246875800000001,'2024-06-12 14:06:20',1067246875800000001,'2024-06-12 16:29:10'),
(1800417541950947329,'用户','',NULL,1067246875800000001,'2024-06-11 14:39:27',1067246875800000001,'2024-06-12 16:28:58');

/*Table structure for table `sys_role_data_scope` */

DROP TABLE IF EXISTS `sys_role_data_scope`;

CREATE TABLE `sys_role_data_scope` (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色数据权限';

/*Data for the table `sys_role_data_scope` */

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`,`creator`,`create_date`) values 
(1800807493335457793,1800417541950947329,1799706754282602497,1067246875800000001,'2024-06-12 16:28:58'),
(1800807493335457794,1800417541950947329,1799706754282602498,1067246875800000001,'2024-06-12 16:28:58'),
(1800807493402566658,1800417541950947329,1799706754282602499,1067246875800000001,'2024-06-12 16:28:58'),
(1800807493402566659,1800417541950947329,1799706754282602500,1067246875800000001,'2024-06-12 16:28:58'),
(1800807493402566660,1800417541950947329,1799706754282602501,1067246875800000001,'2024-06-12 16:28:58'),
(1800807493402566661,1800417541950947329,1799706754282602502,1067246875800000001,'2024-06-12 16:28:58'),
(1800807543428030465,100,1067246875800000002,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543428030466,100,1067246875800000055,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543428030467,100,1067246875800000056,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945025,100,1067246875800000003,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945026,100,1067246875800000004,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945027,100,1067246875800000005,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945028,100,1067246875800000006,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945029,100,1799706754282602497,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543490945030,100,1799706754282602498,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543549665282,100,1799812484224995330,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543549665283,100,1799706754282602499,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543549665284,100,1799706754282602500,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543549665285,100,1799706754282602501,1067246875800000001,'2024-06-12 16:29:10'),
(1800807543549665286,100,1799706754282602502,1067246875800000001,'2024-06-12 16:29:10');

/*Table structure for table `sys_role_user` */

DROP TABLE IF EXISTS `sys_role_user`;

CREATE TABLE `sys_role_user` (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色用户关系';

/*Data for the table `sys_role_user` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL COMMENT 'id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `head_url` varchar(200) DEFAULT NULL COMMENT '头像',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `super_admin` tinyint unsigned DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`real_name`,`head_url`,`gender`,`email`,`mobile`,`super_admin`,`status`,`creator`,`create_date`,`updater`,`update_date`) values 
(1067246875800000001,'admin','$2a$10$012Kx2ba5jzqr9gLlG4MX.bnQJTD9UWqF57XDo2N3.fPtLne02u/m','管理员',NULL,0,'root@renren.io','13612345677',1,1,1067246875800000001,'2024-06-09 11:49:34',1067246875800000001,'2024-06-11 21:23:58');

/*Table structure for table `sys_user_token` */

DROP TABLE IF EXISTS `sys_user_token`;

CREATE TABLE `sys_user_token` (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `token` varchar(100) NOT NULL COMMENT '用户token',
  `expire_date` datetime DEFAULT NULL COMMENT '过期时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户Token';

/*Data for the table `sys_user_token` */

insert  into `sys_user_token`(`id`,`user_id`,`token`,`expire_date`,`update_date`,`create_date`) values 
(1799657931248742402,1067246875800000001,'eed6ebfb44967c12dfdff857628427d8','2024-06-13 04:28:48','2024-06-12 16:28:48','2024-06-09 12:21:01'),
(1800711858179469314,1800711813535297538,'6e530d01f7f33872009a388d0008e58a','2024-06-12 23:56:44','2024-06-12 11:56:44','2024-06-12 10:08:57'),
(1800742956158619649,1800742917109649409,'d5a88aa5f3e7882437d01e6ea5c75b81','2024-06-13 03:03:46','2024-06-12 15:03:46','2024-06-12 12:12:32'),
(1800744114461806594,1800744062125281282,'92541ff80b2ec1b844d9737feecb84e2','2024-06-13 00:17:08','2024-06-12 12:17:08','2024-06-12 12:17:08');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
