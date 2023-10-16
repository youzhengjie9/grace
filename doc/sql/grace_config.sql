/*
 Navicat Premium Data Transfer

 Source Server         : windows-mysql8
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : grace_config

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 17/10/2023 00:24:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_info`;
CREATE TABLE `sys_config_info`  (
  `id` bigint(0) NOT NULL COMMENT '主键,配置信息id',
  `config_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置信息的名称',
  `group_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '该配置信息所属的分组名称',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置信息的内容',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'md5值,每一次修改配置内容这个值都要重新生成,用于判断配置文件是否被修改',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置信息的描述',
  `name_space_id` bigint(0) NOT NULL COMMENT '该配置所属的命名空间id',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置信息内容的类型(例如:text、yml或yaml、properties、json、xml)',
  `user_id` bigint(0) NOT NULL COMMENT '创建该配置信息的用户id',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建该配置信息的用户的ip地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace配置中心的配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '后台侧边栏。父菜单ID,一级菜单为0',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '菜单/权限名称',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'vue路由地址（type=1才会生效，type=0和2不生效）',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '动态路由要用到。views目录下的组件名,自动会补上前缀‘../views’，这个前缀是固定的写法不能写到数据库里不然会报错（type=1才会生效，type=0和2不生效）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
  `visible` tinyint(1) NULL DEFAULT 0 COMMENT '菜单显示状态（0显示 1隐藏）（type=0或者1才会生效，type=2不生效）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单权限标识，比如sys:user:list(type=0设置为null即可，不会生效)',
  `type` int(0) NOT NULL COMMENT '菜单类型。0：目录（点击后台侧边栏可以展开成下一级菜单的按钮）;1：菜单（点击后台侧边栏直接跳转vue路由组件的按钮）;2：按钮;菜单里面的按钮',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标（type=0或者1才会生效，type=2不生效）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `sort` int(0) NULL DEFAULT 1 COMMENT '前端菜单排序，默认值为1，1的优先级最高，排在最上面',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1001, 0, '服务管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (1002, 1001, '服务列表', '/service/list', '/service-list/index', 0, 0, 'sys:service:list', 1, 'el-icon-s-order', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (2001, 0, '配置管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (2002, 2001, '配置列表', '/config/list', '/user-list/index', 0, 0, 'sys:user:list', 1, 'el-icon-s-order', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (2003, 2001, '历史版本', '/history/version', '/history-version/index', 0, 0, 'sys:history:version', 1, 'el-icon-s-order', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (3001, 0, '用户管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (3002, 3001, '用户列表', '/user/list', '/user-list/index', 0, 0, 'sys:user:list', 1, 'el-icon-s-order', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (3003, 3002, '新增用户', NULL, NULL, 0, 0, 'sys:user:list:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 3, 'bz');
INSERT INTO `sys_menu` VALUES (3004, 3002, '修改用户', NULL, NULL, 0, 0, 'sys:user:list:update', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 4, 'bz');
INSERT INTO `sys_menu` VALUES (3005, 3002, '删除用户', NULL, NULL, 0, 0, 'sys:user:list:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 5, 'bz');
INSERT INTO `sys_menu` VALUES (3006, 3002, '分配角色', NULL, NULL, 0, 0, 'sys:user:list:assign-role', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 5, 'bz');
INSERT INTO `sys_menu` VALUES (4001, 0, '用户管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (4002, 4001, '角色列表', '/role/list', '/role-list/index', 0, 0, 'sys:role:list', 1, 'el-icon-suitcase', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 6, 'bz');
INSERT INTO `sys_menu` VALUES (4003, 4002, '新增角色', NULL, NULL, 0, 0, 'sys:role:list:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 7, 'bz');
INSERT INTO `sys_menu` VALUES (4004, 4002, '修改角色', NULL, NULL, 0, 0, 'sys:role:list:update', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 8, 'bz');
INSERT INTO `sys_menu` VALUES (4005, 4002, '删除角色', NULL, NULL, 0, 0, 'sys:role:list:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 9, 'bz');
INSERT INTO `sys_menu` VALUES (4006, 4002, '分配菜单', NULL, NULL, 0, 0, 'sys:role:list:assign-menu', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 9, 'bz');
INSERT INTO `sys_menu` VALUES (5001, 0, '菜单管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (5002, 5001, '菜单列表', '/menu/list', '/menu-list/index', 0, 0, 'sys:menu:list', 1, 'el-icon-s-custom', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 10, 'bz');
INSERT INTO `sys_menu` VALUES (5003, 5002, '新增菜单', NULL, NULL, 0, 0, 'sys:menu:list:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 11, 'bz');
INSERT INTO `sys_menu` VALUES (5004, 5002, '修改菜单', NULL, NULL, 0, 0, 'sys:menu:list:update', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 12, 'bz');
INSERT INTO `sys_menu` VALUES (5005, 5002, '删除菜单', NULL, NULL, 0, 0, 'sys:menu:list:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 13, 'bz');

-- ----------------------------
-- Table structure for sys_namespace
-- ----------------------------
DROP TABLE IF EXISTS `sys_namespace`;
CREATE TABLE `sys_namespace`  (
  `namespace_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键,命令空间id',
  `namespace_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '命名空间名称',
  `max_config_count` int(0) NULL DEFAULT 200 COMMENT '该命名空间最大的配置数',
  `namespace_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '命名空间描述',
  PRIMARY KEY (`namespace_id`) USING BTREE,
  UNIQUE INDEX `namespace_name`(`namespace_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace命名空间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_namespace
-- ----------------------------
INSERT INTO `sys_namespace` VALUES ('dev-namespace', 'dev', 200, '开发环境');
INSERT INTO `sys_namespace` VALUES ('test-namespace', 'test', 200, '测试环境');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色权限名称，比如管理员',
  `role_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色权限关键字，比如admin',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (2001, '超级管理员', 'admin', 0, 0, '2022-09-26 23:46:02', '2022-09-28 23:46:02', '超级管理员角色');
INSERT INTO `sys_role` VALUES (2002, '普通角色', 'user', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '普通角色');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (5001, 2001, 1001);
INSERT INTO `sys_role_menu` VALUES (5002, 2001, 1002);
INSERT INTO `sys_role_menu` VALUES (5003, 2001, 2001);
INSERT INTO `sys_role_menu` VALUES (5004, 2001, 2002);
INSERT INTO `sys_role_menu` VALUES (5005, 2001, 2003);
INSERT INTO `sys_role_menu` VALUES (5006, 2001, 3001);
INSERT INTO `sys_role_menu` VALUES (5007, 2001, 3002);
INSERT INTO `sys_role_menu` VALUES (5008, 2001, 3003);
INSERT INTO `sys_role_menu` VALUES (5009, 2001, 3004);
INSERT INTO `sys_role_menu` VALUES (5010, 2001, 3005);
INSERT INTO `sys_role_menu` VALUES (5011, 2001, 3006);
INSERT INTO `sys_role_menu` VALUES (5012, 2001, 4001);
INSERT INTO `sys_role_menu` VALUES (5013, 2001, 4002);
INSERT INTO `sys_role_menu` VALUES (5014, 2001, 4003);
INSERT INTO `sys_role_menu` VALUES (5015, 2001, 4004);
INSERT INTO `sys_role_menu` VALUES (5016, 2001, 4005);
INSERT INTO `sys_role_menu` VALUES (5017, 2001, 4006);
INSERT INTO `sys_role_menu` VALUES (5018, 2001, 5001);
INSERT INTO `sys_role_menu` VALUES (5019, 2001, 5002);
INSERT INTO `sys_role_menu` VALUES (5020, 2001, 5003);
INSERT INTO `sys_role_menu` VALUES (5021, 2001, 5004);
INSERT INTO `sys_role_menu` VALUES (5022, 2001, 5005);
INSERT INTO `sys_role_menu` VALUES (6001, 2002, 1001);
INSERT INTO `sys_role_menu` VALUES (6002, 2002, 1002);
INSERT INTO `sys_role_menu` VALUES (6003, 2002, 2001);
INSERT INTO `sys_role_menu` VALUES (6004, 2002, 2002);
INSERT INTO `sys_role_menu` VALUES (6005, 2002, 2003);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '用户状态（0正常 1停用）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1001, 'root', '周杰伦', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0, '1550324080@qq.com', '18420163207', 0, 'https://img2.baidu.com/it/u=670341883,3643142939&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2022-09-26', '2022-09-26 23:46:05', 0);
INSERT INTO `sys_user` VALUES (1002, 'test', '蔡徐坤', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0, '1550324080@qq.com', '18420163207', 0, 'https://img2.baidu.com/it/u=361550957,796293689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2022-09-26', '2022-09-26 23:46:05', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `role_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (4001, 1001, 2001);
INSERT INTO `sys_user_role` VALUES (4002, 1001, 2002);
INSERT INTO `sys_user_role` VALUES (4005, 1002, 2002);

SET FOREIGN_KEY_CHECKS = 1;
