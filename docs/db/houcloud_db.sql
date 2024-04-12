/*
 Navicat Premium Data Transfer

 Source Server         : Houcloud MySQL
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 139.196.88.155:13306
 Source Schema         : houcloud_db

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 26/02/2023 14:49:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码(密文)',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `last_live_at` datetime DEFAULT NULL COMMENT '最后活跃时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  `is_creator` tinyint(1) DEFAULT '0' COMMENT '是否创始人',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
BEGIN;
INSERT INTO `t_admin` (`id`, `username`, `mobile`, `password`, `email`, `avatar`, `nickname`, `created_at`, `updated_at`, `last_live_at`, `deleted_at`, `is_creator`, `locked`, `creator_id`) VALUES (1, 'houcloud', '180****9670', '$2a$10$MeLZiTn6rUJ5TlLXv7ajtuo5kO45LpXJ5m0x17rTRE8BTbqV7IX96', 'yunhouhuang@gmail.com', 'https://houcloud.oss-cn-guangzhou.aliyuncs.com/1234d4110c0f41b8bf860579d2aa5a3a.png', 'HouCloud', '2023-01-18 01:03:34', '2023-01-18 01:03:31', '2023-02-26 14:45:22', NULL, 1, 0, NULL);
INSERT INTO `t_admin` (`id`, `username`, `mobile`, `password`, `email`, `avatar`, `nickname`, `created_at`, `updated_at`, `last_live_at`, `deleted_at`, `is_creator`, `locked`, `creator_id`) VALUES (66, 'admin', NULL, '$2a$10$6ysASY4lpNLMIh1GUVVWY.GPG7.qVEmxT213KWhkkXrFqd8z7IQtW', NULL, 'https://houcloud.oss-cn-guangzhou.aliyuncs.com/1234d4110c0f41b8bf860579d2aa5a3a.png', 'Admin', '2023-02-26 12:11:48', NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `t_admin` (`id`, `username`, `mobile`, `password`, `email`, `avatar`, `nickname`, `created_at`, `updated_at`, `last_live_at`, `deleted_at`, `is_creator`, `locked`, `creator_id`) VALUES (67, 'peng', NULL, '$2a$10$gDWvEkkqy36na.RrzySyw.qK27s2v8Lbk4Agz94Gb7eTDIKbos5.K', NULL, 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/7c77305dbe3b4362856abcb7c873f744.jpeg', '运营鹏哥', '2023-02-26 14:29:41', NULL, NULL, NULL, 0, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_admin_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_notice`;
CREATE TABLE `t_admin_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态 0 未读 1 已读',
  `ref_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联类型',
  `ref_id` bigint DEFAULT NULL COMMENT '关联ID',
  `admin_id` bigint DEFAULT NULL COMMENT '管理员ID',
  `global` tinyint(1) DEFAULT '0' COMMENT '是否全局消息',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员消息通知';

-- ----------------------------
-- Records of t_admin_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_admin_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role_ref`;
CREATE TABLE `t_admin_role_ref` (
  `admin_id` bigint NOT NULL COMMENT '管理员ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`admin_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员角色关联';

-- ----------------------------
-- Records of t_admin_role_ref
-- ----------------------------
BEGIN;
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (1, 1);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (63, 2);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (64, 4);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (65, 5);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (65, 6);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (66, 1);
INSERT INTO `t_admin_role_ref` (`admin_id`, `role_id`) VALUES (67, 4);
COMMIT;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `type` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类类型 0 文件分类 1 ...',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类图片',
  `weight` int NOT NULL DEFAULT '0' COMMENT '权重排序(从大到小)',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='分类';

-- ----------------------------
-- Records of t_category
-- ----------------------------
BEGIN;
INSERT INTO `t_category` (`id`, `created_at`, `updated_at`, `name`, `type`, `image`, `weight`, `deleted_at`) VALUES (1, '2023-01-18 01:43:19', '2023-01-21 12:46:11', '营销专用图片', '0', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/5f64742ae864446e98fab50a200ae054.jpg', 0, NULL);
INSERT INTO `t_category` (`id`, `created_at`, `updated_at`, `name`, `type`, `image`, `weight`, `deleted_at`) VALUES (3, '2023-01-22 02:48:44', '2023-01-22 02:49:37', '科技狠活', '0', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/d342b7bbd5dc463b889ddf73c5b0abac.png', 999, NULL);
INSERT INTO `t_category` (`id`, `created_at`, `updated_at`, `name`, `type`, `image`, `weight`, `deleted_at`) VALUES (4, '2023-01-22 02:49:32', '2023-02-11 16:44:58', '宝藏收藏', '1', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/c68dd95050a146efbd386de9385d4e3a.png', 999, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '反馈内容',
  `images` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '反馈图片',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '反馈类型',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系方式',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0处理中 1 待确认 2 已完结',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='意见反馈';

-- ----------------------------
-- Records of t_feedback
-- ----------------------------
BEGIN;
INSERT INTO `t_feedback` (`id`, `created_at`, `updated_at`, `content`, `images`, `type`, `user_id`, `contact`, `status`, `deleted_at`) VALUES (3, '2023-02-01 23:37:11', '2023-02-05 17:57:27', '没什么', NULL, 'BUG反馈', 65, '', '2', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_issue
-- ----------------------------
DROP TABLE IF EXISTS `t_issue`;
CREATE TABLE `t_issue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '角色编码',
  `helpful` bigint DEFAULT NULL,
  `no_help` bigint DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否显示',
  `admin_id` int DEFAULT NULL COMMENT '创建/最后编辑人ID',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='常见问题';

-- ----------------------------
-- Records of t_issue
-- ----------------------------
BEGIN;
INSERT INTO `t_issue` (`id`, `created_at`, `updated_at`, `title`, `answer`, `helpful`, `no_help`, `enabled`, `admin_id`, `deleted_at`) VALUES (3, '2023-02-01 23:26:06', '2023-02-01 23:31:15', '【新手教学】如何绑定达人？', '<p>前往我的 -&gt; 达人管理 -&gt; 点击添加达人 -&gt; 显示二维码时使用抖音app左上角+号中的扫一扫授权绑定即可，支持截图扫码。</p>', NULL, NULL, NULL, NULL, '2023-02-13 08:20:16');
INSERT INTO `t_issue` (`id`, `created_at`, `updated_at`, `title`, `answer`, `helpful`, `no_help`, `enabled`, `admin_id`, `deleted_at`) VALUES (4, '2023-02-01 23:28:11', '2023-02-01 23:31:34', '【预防诈骗】收到了抖带选的客服私聊？', '<p>都带选客服不会主动向用户发送任何消息，请不要相信除了小程序客服以外的聊天。</p>', NULL, NULL, NULL, NULL, '2023-02-13 08:20:14');
INSERT INTO `t_issue` (`id`, `created_at`, `updated_at`, `title`, `answer`, `helpful`, `no_help`, `enabled`, `admin_id`, `deleted_at`) VALUES (5, '2023-02-01 23:31:01', '2023-02-13 16:20:12', '【二次开发】购买抖带选系统做二次开发？', '<p>请确保您有充分的准备，比如抖音开放平台工具型应用的资质，如果没有则无法完整使用该系统。申请需要一定的门槛，详情请到抖音精选联盟开放平台官方网站了解。</p>', NULL, NULL, NULL, NULL, '2023-02-13 08:20:12');
INSERT INTO `t_issue` (`id`, `created_at`, `updated_at`, `title`, `answer`, `helpful`, `no_help`, `enabled`, `admin_id`, `deleted_at`) VALUES (6, '2023-02-01 23:33:33', '2023-02-13 16:20:08', 'Houcloud收费吗', '<p>目前开源的Houcloud不收费，MTI协议开源</p>', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_issue` (`id`, `created_at`, `updated_at`, `title`, `answer`, `helpful`, `no_help`, `enabled`, `admin_id`, `deleted_at`) VALUES (7, '2023-02-01 23:35:43', '2023-02-13 16:19:25', '怎么部署？', '<p>houcloud.com 后续会完善并出相关视频</p>', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前端路由Name',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编码',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前端跳转路径',
  `parent_id` bigint DEFAULT NULL COMMENT '上级ID',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
  `weight` bigint NOT NULL DEFAULT '0' COMMENT '排序权重（越大越优先）',
  `keep_alive` tinyint(1) DEFAULT NULL COMMENT '是否持久页',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型（1按钮 0菜单）',
  `white` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否白名单',
  `hidden` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  `single` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏子菜单',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前端组件地址',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '重定向',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (1, '2023-01-18 11:52:56', '2023-01-19 21:34:55', '组织与权限', 'GroupPage', '', '/group', 0, 'icon-zuzhijigou', 3, 0, '0', 0, 0, 0, 'layouts/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (2, '2023-01-18 13:59:56', '2023-02-06 23:17:17', '菜单与权限', 'MenuPage', 'menu:list', '/group/menu', 1, '', 2, 1, '0', 0, 0, 0, 'pages/group/menu/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (3, '2023-01-18 22:21:41', '2023-01-20 01:56:14', '添加菜单', '', 'menu:add', '', 2, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (4, '2023-01-18 22:49:18', '2023-02-06 23:17:07', '组织成员', 'AdminPage', 'admin:list', '/admin', 1, '', 6, 1, '0', 0, 0, 0, 'pages/group/admin/list/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (5, '2023-01-18 22:49:59', '2023-01-22 00:16:19', '新增管理员', '', 'admin:add', '', 4, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (6, '2023-01-18 22:50:24', '2023-01-22 00:16:12', '修改管理员', '', 'admin:update', '', 4, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (7, '2023-01-18 22:51:35', '2023-01-22 00:16:03', '锁定管理员', '', 'admin:lock', '', 4, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (8, '2023-01-18 22:52:01', '2023-01-22 00:15:46', '删除管理员', '', 'admin:delete', '', 4, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (9, '2023-01-18 22:53:54', '2023-01-22 00:15:53', '管理员详情', '', 'admin:detail', '', 4, '', 1, 0, '1', 0, 0, 0, NULL, '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (10, '2023-01-19 01:27:06', '2023-01-20 19:55:54', '控制台', 'Index', '', '/', 0, 'icon-shouye', 999, 1, '0', 0, 0, 0, 'layouts/index.vue', '/dashboard', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (11, '2023-01-19 01:36:06', '2023-01-19 01:46:16', '控制台', 'Dashboard', 'dashboard', '/dashboard', 10, '', 1, 0, '0', 0, 0, 0, 'pages/dashboard/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (15, '2023-01-19 13:46:11', '2023-01-20 00:07:26', '经营概览', 'Dashboard', 'dashboard', '/dashboard', 10, '', 999, 1, '0', 0, 0, 0, 'pages/dashboard/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (17, '2023-01-19 15:39:29', '2023-01-19 23:00:16', '用户管理', 'UserPage', '', '/user', 0, 'icon-renqun', 33, 1, '0', 0, 0, 0, 'layouts/index.vue', '/user/list', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (18, '2023-01-19 15:50:18', '2023-01-24 16:14:27', '注册用户', 'UserList', 'user:list', '/user/list', 17, '', 1, 1, '0', 0, 0, 0, 'pages/user/list/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (20, '2023-01-19 16:05:24', '2023-02-06 23:17:11', '角色管理', 'RoleListPage', 'role:list', '/role/list', 1, '', 5, 1, '0', 0, 0, 0, 'pages/group/role/list/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (23, '2023-01-19 20:23:33', NULL, '新增角色', '', 'role:add', '', 20, '', 1, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (24, '2023-01-19 20:26:17', NULL, '删除菜单', '', 'menu:delete', '', 2, '', 1, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (25, '2023-01-19 20:26:40', NULL, '修改菜单', '', 'menu:update', '', 2, '', 1, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (26, '2023-01-19 20:35:45', '2023-01-21 15:41:34', '系统与维护', 'OpsPage', '', '/ops', 0, 'icon-KJ_005', 3, 0, '0', 0, 0, 0, 'layouts/index.vue', '/ops/runtime', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (29, '2023-01-19 20:50:13', '2023-01-21 22:25:54', '服务运行状况', 'ServerInfo', 'server:info', '/ops/server', 26, 'icon-liulan1', 996, 0, '0', 0, 0, 0, 'pages/ops/server/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (30, '2023-01-19 20:54:03', '2023-01-20 13:28:32', '运营中心', 'Operation', '', '/operation', 0, 'icon-huodong', 64, 0, '0', 0, 0, 0, 'layouts/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (32, '2023-01-19 21:05:50', '2023-01-20 13:29:48', '常见问题', 'OperationQuestion', 'operation:question', '/operation/question', 30, 'icon-help1', 1030, 0, '0', 0, 0, 0, 'pages/operation/question/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (33, '2023-01-19 21:10:44', '2023-01-19 21:31:32', '文件管理', 'FileManage', '', '/file', 0, 'icon-wenjianguanli-tongyong-2', 30, 0, '0', 0, 0, 0, 'layouts/index.vue', '/file/list', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (36, '2023-01-19 21:22:08', NULL, '平台协议', 'PlatformAgree', 'operation:agree', '/operation/agree', 30, 'icon-dingdandingdanchaxun', 999, 0, '0', 0, 0, 0, 'pages/operation/agree/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (37, '2023-01-19 21:24:19', NULL, '平台信息', 'PlatformInfo', 'operation:platform', '/operation/platform', 30, 'icon-fangwenliang', 999, 0, '0', 0, 0, 0, 'pages/operation/platform/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (38, '2023-01-19 21:27:14', '2023-01-20 13:29:29', '问题反馈', 'Feedback', 'feedback', '/feedback', 30, 'icon-feedback-line', 1000, 0, '0', 0, 0, 0, 'pages/operation/feedback/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (39, '2023-01-19 21:31:22', '2023-01-19 21:32:02', '文件列表', 'FileList', 'file:list', '/file/list', 33, '', 999, 0, '0', 0, 1, 1, 'pages/file/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (40, '2023-01-19 21:34:16', '2023-01-20 01:34:18', '系统安全', 'SysSecurity', '', '/security/index', 0, 'icon-anquan', 2, 0, '0', 0, 0, 0, 'layouts/index.vue', '/security/logs', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (41, '2023-01-19 21:43:52', '2023-01-20 01:24:18', '操作日志', 'Syslog', 'security:logs', '/security/logs', 40, 'icon-wiappfangwenliang', 999, 0, '0', 0, 0, 0, 'pages/security/logs/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (44, '2023-01-20 15:06:48', '2023-01-21 15:44:00', '接口文档', 'ApiDocs', '', 'http://127.0.0.1:11027/doc.html', 26, 'icon-jiechu', 999, 0, '0', 0, 0, 0, 'http://127.0.0.1:11027/doc.html', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (45, '2023-01-21 15:43:42', '2023-01-21 22:19:16', '缓存监控', 'OpsCacheInfo', 'cache:info', '/ops/cache', 26, 'icon-yly_zhuanjifen', 998, 0, '0', 0, 0, 0, 'pages/ops/cache/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (46, '2023-01-22 00:20:08', '2023-01-22 00:25:16', '分类列表', 'Category', 'category:list', '/category', 47, 'icon-liebiao', 12, 0, '0', 0, 0, 0, 'pages/category/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (47, '2023-01-22 00:24:42', '2023-01-22 00:26:12', '分类管理', 'CategoryMange', '', '/category/index', 0, 'icon-liebiao', 12, 0, '0', 0, 0, 1, 'layouts/index.vue', '/category', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (55, '2023-02-06 23:31:56', '2023-02-06 23:34:31', '用户详情', 'UserDetail', 'user:detail', '/user/detail/:id', 17, '', 999, 0, '0', 0, 1, 0, 'pages/user/detail/index.vue', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (59, '2023-02-08 00:38:16', '2023-02-08 00:38:42', '修改角色', '', 'role:update', '', 20, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (60, '2023-02-08 00:38:56', NULL, '删除角色', '', 'role:delete', '', 20, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (62, '2023-02-11 16:15:53', NULL, '订单发货', '', 'product:order:delivery', '', 49, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (63, '2023-02-11 16:16:18', NULL, '审核订单', '', 'product:order:audit', '', 49, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (65, '2023-02-11 16:53:44', NULL, '上传文件', '', 'file:upload', '', 33, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (66, '2023-02-11 16:54:01', NULL, '删除页面', '', 'file:delete', '', 33, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
INSERT INTO `t_menu` (`id`, `created_at`, `updated_at`, `title`, `name`, `permission`, `path`, `parent_id`, `icon`, `weight`, `keep_alive`, `type`, `white`, `hidden`, `single`, `component`, `redirect`, `deleted_at`) VALUES (67, '2023-02-11 16:54:27', NULL, '修改文件', '', 'file:update', '', 33, '', 999, 0, '1', 0, 0, 0, '', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色编码',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (1, '2023-01-18 01:43:19', '2023-02-08 00:39:36', '超级管理员', 'role_super_admin', NULL, NULL);
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (2, '2023-01-21 12:47:58', '2023-02-08 00:39:25', '演示人员', 'role_viewer', NULL, NULL);
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (3, '2023-02-08 00:37:09', NULL, '财务管理', 'role_finance', NULL, NULL);
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (4, '2023-02-08 00:37:40', NULL, '运营人员', 'role_operation', NULL, NULL);
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (5, '2023-02-08 00:41:32', NULL, '合作商家', 'role_shop', NULL, NULL);
INSERT INTO `t_role` (`id`, `created_at`, `updated_at`, `name`, `code`, `deleted_at`, `description`) VALUES (6, '2023-02-08 00:42:03', NULL, '物流仓储部门', 'role_pw', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_role_menu_ref
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu_ref`;
CREATE TABLE `t_role_menu_ref` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色菜单关联';

-- ----------------------------
-- Records of t_role_menu_ref
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 5);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 6);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 7);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 8);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 9);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 10);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 12);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 13);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 14);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 15);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 16);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 17);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 18);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 19);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 20);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 21);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 22);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 23);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 24);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 25);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 26);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 27);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 28);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 29);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 30);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 32);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 33);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 34);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 36);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 37);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 38);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 39);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 40);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 41);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 42);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 43);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 44);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 45);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 46);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 47);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 48);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 49);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 50);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 51);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 52);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 53);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 54);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 55);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 56);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 57);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 58);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 59);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 60);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 61);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 62);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 63);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 64);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 65);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 66);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 67);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 70);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 71);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 72);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 73);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 74);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (1, 75);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (2, 15);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 15);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 18);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 19);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 22);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 28);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 43);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 55);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 56);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 57);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (3, 58);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 13);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 15);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 18);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 19);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 32);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 34);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 36);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 37);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 38);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 43);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 50);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 51);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 52);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 53);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 54);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 55);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (4, 56);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (5, 13);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (5, 50);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (5, 51);
INSERT INTO `t_role_menu_ref` (`role_id`, `menu_id`) VALUES (5, 54);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一键',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '变量值',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统变量配置';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_config` (`id`, `created_at`, `updated_at`, `key`, `value`, `name`, `deleted_at`) VALUES (3, '2023-02-01 23:20:59', NULL, 'platform-info', '{\"logo\":\"https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/3ddb2b87d6f7436482e0f7e73ada5a41.png\",\"name\":\"Houcloud\",\"subtitle\":\"一个Springboot3.x+Vue3 TS TDesign腾讯风格的快速开发框架\",\"intro\":\"一个Springboot3.x+Vue3 TS TDesign腾讯风格的快速开发框架\",\"officialUrl\":\"houcloud.com\",\"phone\":\"\",\"icp\":\"粤ICP备xxx\",\"sysVersion\":\"v2023.1\",\"uscCode\":\"\",\"businessLicense\":\"https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/80679b631fdc42cfb26f475df9383b63.jpg\"}', '平台信息', NULL);
INSERT INTO `t_sys_config` (`id`, `created_at`, `updated_at`, `key`, `value`, `name`, `deleted_at`) VALUES (4, NULL, NULL, 'user-agreement', '', '用户协议', NULL);
INSERT INTO `t_sys_config` (`id`, `created_at`, `updated_at`, `key`, `value`, `name`, `deleted_at`) VALUES (5, NULL, NULL, 'privacy-agreement', '', '隐私协议', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_file`;
CREATE TABLE `t_sys_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `suffix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后缀名',
  `size` bigint DEFAULT NULL COMMENT '大小(单位字节)',
  `cate_id` bigint DEFAULT NULL COMMENT '分类ID',
  `admin_id` bigint DEFAULT NULL COMMENT '管理员ID',
  `old_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件原名',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'oss桶名称',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统文件';

-- ----------------------------
-- Records of t_sys_file
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (7, '2023-01-23 16:04:37', '2023-02-26 11:44:45', '%26]KOPP[~D`BGPWUJ$G)OC.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/d342b7bbd5dc463b889ddf73c5b0abac.png', 'png', 5790, NULL, 1, '%26]KOPP[~D`BGPWUJ$G)OC.png', 'doudaixuan', '2023-02-26 03:44:45');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (8, '2023-02-04 19:55:58', '2023-02-26 11:44:50', 'logo.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/d8c54bcc0a914c6d8ff1b281a110eb7e.png', 'png', 119662, NULL, 1, 'logo.png', 'doudaixuan', '2023-02-26 03:44:49');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (9, '2023-02-04 19:57:56', '2023-02-13 16:15:55', 'applet-qrcode.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/5f64742ae864446e98fab50a200ae054.jpg', 'jpg', 59706, NULL, 1, 'applet-qrcode.jpg', 'doudaixuan', '2023-02-13 08:15:55');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (10, '2023-02-04 19:57:56', '2023-02-13 16:15:53', '65f034c0f853471ed478ceb34164523b_20220911023334.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/c68dd95050a146efbd386de9385d4e3a.png', 'png', 24573, NULL, 1, '65f034c0f853471ed478ceb34164523b_20220911023334.png', 'doudaixuan', '2023-02-13 08:15:53');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (11, '2023-02-04 19:57:56', NULL, '4297f44b13955235245b2497399d7a93_20221228154030.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/80679b631fdc42cfb26f475df9383b63.jpg', 'jpg', 272479, NULL, 1, '4297f44b13955235245b2497399d7a93_20221228154030.jpg', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (12, '2023-02-04 19:58:13', '2023-02-13 16:15:49', 'f5d7e2532cc9ad16bc2a41222d76f269_20221228154956.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/4d347fa6230149fab12808c20c38f7f7.jpg', 'jpg', 74647, NULL, 1, 'f5d7e2532cc9ad16bc2a41222d76f269_20221228154956.jpg', 'doudaixuan', '2023-02-13 08:15:49');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (13, '2023-02-05 15:59:57', '2023-02-13 16:16:16', 'ff764677ca8559788957e7c7dac048ac.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/f54b8b747f534041b8e123f5f7fb9ef7.jpg', 'jpg', 52708, NULL, 1, 'ff764677ca8559788957e7c7dac048ac.jpg', 'doudaixuan', '2023-02-13 08:16:16');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (14, '2023-02-05 17:58:06', '2023-02-13 16:16:15', '微信图片_20230205175649.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/f84813e61e144ac28fb2b60d9948e1fa.jpg', 'jpg', 112012, NULL, 1, '微信图片_20230205175649.jpg', 'doudaixuan', '2023-02-13 08:16:14');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (15, '2023-02-08 00:14:54', '2023-02-13 16:16:13', '84BCA1823ADB6B4422E0200F46EF582E.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/dd73e21d48574d37be440d17aac79b52.jpg', 'jpg', 309138, NULL, 1, '84BCA1823ADB6B4422E0200F46EF582E.jpg', 'doudaixuan', '2023-02-13 08:16:12');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (16, '2023-02-08 00:14:54', '2023-02-13 16:16:02', '674BD94BF9A97860AC71868980136698.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/528838d1ca334e63970d922e960182dd.jpg', 'jpg', 507111, NULL, 1, '674BD94BF9A97860AC71868980136698.jpg', 'doudaixuan', '2023-02-13 08:16:02');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (17, '2023-02-11 17:13:27', '2023-02-13 16:16:00', '9513317a5a73068f4e7de3354d074d6f.jpg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/b8004e59fee148b29baa3098d9590b85.jpg', 'jpg', 26740, NULL, 1, '9513317a5a73068f4e7de3354d074d6f.jpg', 'doudaixuan', '2023-02-13 08:15:59');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (18, '2023-02-11 17:13:41', NULL, '家居.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/aa40992adcad4186b6a5fb0b106d361d.png', 'png', 236715, NULL, 1, '家居.png', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (19, '2023-02-11 17:13:41', '2023-02-13 16:16:07', '母婴.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/524645d7ffe0423ba399151b8fd65fec.png', 'png', 131956, NULL, 1, '母婴.png', 'doudaixuan', '2023-02-13 08:16:06');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (20, '2023-02-11 17:13:41', NULL, '美妆.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/fdfc8f0776724ca4a9a7bb3a3b9ada63.png', 'png', 58649, NULL, 1, '美妆.png', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (21, '2023-02-11 17:13:41', NULL, '微信截图_20230211171126.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/3ddb2b87d6f7436482e0f7e73ada5a41.png', 'png', 239648, NULL, 1, '微信截图_20230211171126.png', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (22, '2023-02-11 17:13:41', '2023-02-13 16:16:10', '玩具.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/967fd9df95a84bffb0ef38a38aa9b148.png', 'png', 259119, NULL, 1, '玩具.png', 'doudaixuan', '2023-02-13 08:16:10');
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (23, '2023-02-11 17:15:01', NULL, '微信截图_20230211171445.png', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/098053a4a07f4b86adaaed2c43f105a5.png', 'png', 359513, NULL, 1, '微信截图_20230211171445.png', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (24, '2023-02-11 17:16:21', NULL, '6fa11879f88f924d9f8d8676b682ad49.jpeg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/62efab73f78947a18cbe378d5292cb0f.jpeg', 'jpeg', 22181, NULL, 1, '6fa11879f88f924d9f8d8676b682ad49.jpeg', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (25, '2023-02-12 20:41:29', NULL, 'u=767518313,183318574&fm=253&app=138&f=JPEG&fmt=auto&q=75.jpeg', 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/7c77305dbe3b4362856abcb7c873f744.jpeg', 'jpeg', 7318, NULL, 1, 'u=767518313,183318574&fm=253&app=138&f=JPEG&fmt=auto&q=75.jpeg', 'doudaixuan', NULL);
INSERT INTO `t_sys_file` (`id`, `created_at`, `updated_at`, `name`, `url`, `suffix`, `size`, `cate_id`, `admin_id`, `old_name`, `bucket`, `deleted_at`) VALUES (26, '2023-02-26 12:11:33', NULL, '切片.png', 'https://houcloud.oss-cn-guangzhou.aliyuncs.com/1234d4110c0f41b8bf860579d2aa5a3a.png', 'png', 11756, NULL, 1, '切片.png', 'houcloud', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志标题',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志描述内容',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
  `uri` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求URI',
  `host` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求HOST',
  `ua` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求UA',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地区',
  `tag` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
  `admin_id` bigint DEFAULT NULL COMMENT '管理员ID',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志类型 0 接口日志  1 登录日志',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志';

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (247, '2023-02-25 20:19:55', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (248, '2023-02-25 21:43:43', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (249, '2023-02-25 21:55:18', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'OSX|10_15_7|Mac|Safari', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (250, '2023-02-25 22:09:48', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (251, '2023-02-25 22:17:39', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (252, '2023-02-25 22:37:55', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'OSX|10_15_7|Mac|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (253, '2023-02-25 23:18:24', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '61.144.118.122', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (254, '2023-02-26 00:21:26', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '61.144.118.122', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (255, '2023-02-26 10:28:14', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|MSEdge', '123.139.105.242', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (256, '2023-02-26 11:09:20', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'Windows 10 or Windows Server 2016|10.0|Windows|Chrome', '61.144.118.122', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (257, '2023-02-26 11:44:27', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '127.0.0.1', 'OSX|10_15_7|Mac|Chrome', '0.0.0.0', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (258, '2023-02-26 13:52:59', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'OSX|10_15_7|Mac|Safari', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (259, '2023-02-26 14:15:31', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '127.0.0.1', 'OSX|10_15_7|Mac|Chrome', '0.0.0.0', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (260, '2023-02-26 14:27:08', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '172.19.0.1', 'OSX|10_15_7|Mac|Chrome', '113.109.24.180', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (261, '2023-02-26 14:43:24', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '127.0.0.1', 'OSX|10_15_7|Mac|Chrome', '0.0.0.0', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (262, '2023-02-26 14:45:22', NULL, '登录成功', NULL, 'POST', '/api/admin/login', '127.0.0.1', 'OSX|10_15_7|Mac|Chrome', '0.0.0.0', NULL, NULL, 1, '1', NULL);
INSERT INTO `t_sys_log` (`id`, `created_at`, `updated_at`, `title`, `content`, `method`, `uri`, `host`, `ua`, `ip`, `ip_address`, `tag`, `admin_id`, `type`, `deleted_at`) VALUES (263, '2023-02-26 14:45:38', NULL, '锁定或解除锁定', NULL, 'PUT', '/api/admin/admin/lock', '127.0.0.1', 'OSX|10_15_7|Mac|Chrome', '0.0.0.0', NULL, '正常', 1, '0', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码(密文)',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `wechat_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信小程序openid',
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `last_live_at` datetime DEFAULT NULL COMMENT '最近活跃时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `wallet` bigint DEFAULT NULL COMMENT '钱包余额 CNY（分）',
  `wallet_mark` tinyint(1) DEFAULT NULL COMMENT '是否提示钱包红点标记',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1男2女',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `invite_id` bigint DEFAULT NULL COMMENT '邀请人ID',
  `blacklist` tinyint(1) DEFAULT '0' COMMENT '是否黑名单',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否已锁定',
  `id_card_no` varchar(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号（实名信息）',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名（实名信息）',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  `pay_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户支付密码(密文)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `wechat_openid` (`wechat_openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `username`, `password`, `avatar`, `nickname`, `wechat_openid`, `created_at`, `last_live_at`, `updated_at`, `mobile`, `email`, `wallet`, `wallet_mark`, `sex`, `birthday`, `invite_id`, `blacklist`, `locked`, `id_card_no`, `real_name`, `deleted_at`, `pay_password`) VALUES (65, '8899922fd2a84f6486d1ecc8432bcfd4', NULL, 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/8ae6f20b1235405eb1ca744efa6edde0.jpeg', '一旦', 'omtkj42g5i15KXGHLZRubd1iNuXQ', '2023-01-29 18:20:13', '2023-02-12 15:02:54', NULL, '18579429670', NULL, 8845600, NULL, NULL, NULL, NULL, 0, 0, '452226199610285400', '黄运厚', NULL, NULL);
INSERT INTO `t_user` (`id`, `username`, `password`, `avatar`, `nickname`, `wechat_openid`, `created_at`, `last_live_at`, `updated_at`, `mobile`, `email`, `wallet`, `wallet_mark`, `sex`, `birthday`, `invite_id`, `blacklist`, `locked`, `id_card_no`, `real_name`, `deleted_at`, `pay_password`) VALUES (66, '7dd06970e09a4ea4ab49709ee5ef22e2', NULL, 'https://doudaixuan.oss-cn-guangzhou.aliyuncs.com/8879f30c88df4b6f9421b691917ddd5c.jpg', 'CatAndMe', 'omtkj46R3lbQeLXgnyRhLrl0UeJI', '2023-02-05 02:57:51', '2023-02-06 00:18:08', NULL, '18054219670', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '315552198606095468', '刘勇', NULL, NULL);
INSERT INTO `t_user` (`id`, `username`, `password`, `avatar`, `nickname`, `wechat_openid`, `created_at`, `last_live_at`, `updated_at`, `mobile`, `email`, `wallet`, `wallet_mark`, `sex`, `birthday`, `invite_id`, `blacklist`, `locked`, `id_card_no`, `real_name`, `deleted_at`, `pay_password`) VALUES (67, '5eb13c5b45164c34987c354f04064d3f', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/VqvOaHcbyUtXDBmAqUpKTsFY59zVAj9PyuC1uwCjClia1lrINQ6sAOapDeRibzwPiazfC3nKn9Qic7vJqwgpKCichhg/132', '低调', 'omtkj44G6Ubqz2s_X9N1_yHuJFsA', '2023-02-05 23:53:18', '2023-02-12 00:02:18', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` (`id`, `username`, `password`, `avatar`, `nickname`, `wechat_openid`, `created_at`, `last_live_at`, `updated_at`, `mobile`, `email`, `wallet`, `wallet_mark`, `sex`, `birthday`, `invite_id`, `blacklist`, `locked`, `id_card_no`, `real_name`, `deleted_at`, `pay_password`) VALUES (74, '3520a93d069d4ebdae88b0f0c8e4972d', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/1Wuy9nbyBLfwJucgIXCq9rvtAP0L1ick46oTADTdgB5c4oFG6n8IJc7bMiatf1n3aSmRk3nLqyyjuYG9ibgKDCPpA/132', '福星高照', 'omtkj4w_AYr2AAqmB8Edt8dLDuDA', '2023-02-06 00:25:15', '2023-02-06 00:25:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` (`id`, `username`, `password`, `avatar`, `nickname`, `wechat_openid`, `created_at`, `last_live_at`, `updated_at`, `mobile`, `email`, `wallet`, `wallet_mark`, `sex`, `birthday`, `invite_id`, `blacklist`, `locked`, `id_card_no`, `real_name`, `deleted_at`, `pay_password`) VALUES (84, 'd2bbac1277b04c01a6de7faf6b2f3d8d', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJhF2keTvcWiaUtDib3Gs5XsmxeD1rGIsqvib5uFwam3A8671o6f8vxu6rCHkGlPicoSicOiatPFYGE2PHQ/132', '10.11', 'omtkj49rDY6xneoQMLKMhNQPmRJE', '2023-02-12 14:20:11', '2023-02-12 14:20:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user_file
-- ----------------------------
DROP TABLE IF EXISTS `t_user_file`;
CREATE TABLE `t_user_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `suffix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后缀名',
  `size` bigint DEFAULT NULL COMMENT '大小(单位字节)',
  `cate_id` bigint DEFAULT NULL COMMENT '分类ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `old_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件原名',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'oss桶名称',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户文件';

-- ----------------------------
-- Records of t_user_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user_log
-- ----------------------------
DROP TABLE IF EXISTS `t_user_log`;
CREATE TABLE `t_user_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志标题',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志描述内容',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
  `uri` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求URI',
  `host` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求HOST',
  `ua` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求UA',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地区',
  `tag` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志类型 0 接口日志  1 登录日志',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户日志';

-- ----------------------------
-- Records of t_user_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_user_notice`;
CREATE TABLE `t_user_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL COMMENT '行创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '行更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态 0 未读 1 已读',
  `ref_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联类型',
  `ref_id` bigint DEFAULT NULL COMMENT '关联ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `global` tinyint(1) DEFAULT '0' COMMENT '是否全局消息',
  `deleted_at` datetime DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户消息通知';

-- ----------------------------
-- Records of t_user_notice
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
