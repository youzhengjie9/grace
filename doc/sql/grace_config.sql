DROP DATABASE IF EXISTS `grace_config`;
CREATE DATABASE `grace_config`;

USE `grace_config`;

/* 用户表 */
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL COMMENT '主键',
                            `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
                            `nick_name` varchar(32) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
                            `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
                            `status` tinyint(1) DEFAULT '0' COMMENT '用户状态（0正常 1停用）',
                            `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
                            `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
                            `sex` tinyint(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
                            `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像地址',
                            `create_time` date DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
                            `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';


INSERT INTO `sys_user` VALUES (1001, 'root', '周杰伦', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0, '1550324080@qq.com', '18420163207', 0, 'https://img2.baidu.com/it/u=670341883,3643142939&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2022-09-26 23:46:02', '2022-09-26 23:46:05', 0);
INSERT INTO `sys_user` VALUES (1002, 'test', '蔡徐坤', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0, '1550324080@qq.com', '18420163207', 0, 'https://img2.baidu.com/it/u=361550957,796293689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2022-09-26 23:46:02', '2022-09-26 23:46:05', 0);

/* 角色表 */
DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL COMMENT '主键',
                            `name` varchar(64) DEFAULT NULL COMMENT '角色权限名称，比如管理员',
                            `role_key` varchar(64) DEFAULT NULL COMMENT '角色权限关键字，比如admin',
                            `status` tinyint(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
                            `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
                            `remark` varchar(256) DEFAULT NULL COMMENT '备注',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';


INSERT INTO sys_role VALUES (2001,'超级管理员','admin',0,0,'2022-09-26 23:46:02','2022-09-28 23:46:02','超级管理员角色');
INSERT INTO sys_role VALUES (2002,'普通角色','user',0,0,'2022-09-25 10:23:02','2022-09-28 10:33:02','普通角色');


/* 菜单表。也就是后台侧边栏菜单（本质上其实也是接口菜单权限）与接口菜单权限的表 */
DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
                            `id` bigint(20) NOT NULL COMMENT '主键',
                            `parent_id` bigint(20) DEFAULT '0' COMMENT '后台侧边栏。父菜单ID,一级菜单为0',
                            `menu_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单/权限名称',
                            `path` varchar(200) DEFAULT NULL COMMENT 'vue路由地址（type=1才会生效，type=0和2不生效）',
                            `component` varchar(128) DEFAULT NULL COMMENT '动态路由要用到。views目录下的组件名,自动会补上前缀‘../views’，这个前缀是固定的写法不能写到数据库里不然会报错（type=1才会生效，type=0和2不生效）',
                            `status` tinyint(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
                            `visible` tinyint(1) DEFAULT '0' COMMENT '菜单显示状态（0显示 1隐藏）（type=0或者1才会生效，type=2不生效）',
                            `perms` varchar(100) DEFAULT NULL COMMENT '菜单权限标识，比如sys:user:list(type=0设置为null即可，不会生效)',
                            `type` int NOT NULL COMMENT '菜单类型。0：目录（点击后台侧边栏可以展开成下一级菜单的按钮）;1：菜单（点击后台侧边栏直接跳转vue路由组件的按钮）;2：按钮;菜单里面的按钮',
                            `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标（type=0或者1才会生效，type=2不生效）',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
                            `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            `sort` int DEFAULT '1' COMMENT '前端菜单排序，默认值为1，1的优先级最高，排在最上面',
                            `remark` varchar(256) DEFAULT NULL COMMENT '备注',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

-- 1: 服务管理

/* 服务管理-目录 */
INSERT INTO sys_menu VALUES (1001,0,'服务管理',null,null,0,0,null,0,'el-icon-eleme','2022-09-26 23:46:02','2022-09-28 23:46:02',0,1,'bz');

/* 服务列表-菜单 */
INSERT INTO sys_menu VALUES (1002,1001,'服务列表','/service/list','/service-list/index',0,0,'sys:service:list',1,'el-icon-s-order','2022-09-26 23:46:02','2022-09-28 23:46:02',0,2,'bz');


-- 2: 配置管理

/* 配置管理-目录 */
INSERT INTO sys_menu VALUES (2001,0,'配置管理',null,null,0,0,null,0,'el-icon-eleme','2022-09-26 23:46:02','2022-09-28 23:46:02',0,1,'bz');

/* 配置列表-菜单 */
INSERT INTO sys_menu VALUES (2002,2001,'配置列表','/config/list','/user-list/index',0,0,'sys:user:list',1,'el-icon-s-order','2022-09-26 23:46:02','2022-09-28 23:46:02',0,2,'bz');

/* 历史版本-菜单 */
INSERT INTO sys_menu VALUES (2003,2001,'历史版本','/history/version','/history-version/index',0,0,'sys:history:version',1,'el-icon-s-order','2022-09-26 23:46:02','2022-09-28 23:46:02',0,2,'bz');


-- 3: 用户管理

/* 用户管理-目录 */
INSERT INTO sys_menu VALUES (3001,0,'用户管理',null,null,0,0,null,0,'el-icon-eleme','2022-09-26 23:46:02','2022-09-28 23:46:02',0,1,'bz');

/* 用户列表-菜单 */
INSERT INTO sys_menu VALUES (3002,3001,'用户列表','/user/list','/user-list/index',0,0,'sys:user:list',1,'el-icon-s-order','2022-09-26 23:46:02','2022-09-28 23:46:02',0,2,'bz');

/* 用户列表里面的按钮权限 */
INSERT INTO sys_menu VALUES (3003,3002,'新增用户',null,null,0,0,'sys:user:list:add',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,3,'bz');
INSERT INTO sys_menu VALUES (3004,3002,'修改用户',null,null,0,0,'sys:user:list:update',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,4,'bz');
INSERT INTO sys_menu VALUES (3005,3002,'删除用户',null,null,0,0,'sys:user:list:delete',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,5,'bz');
INSERT INTO sys_menu VALUES (3006,3002,'分配角色',null,null,0,0,'sys:user:list:assign-role',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,5,'bz');

-- 4: 角色管理

/* 角色管理-目录 */
INSERT INTO sys_menu VALUES (4001,0,'用户管理',null,null,0,0,null,0,'el-icon-eleme','2022-09-26 23:46:02','2022-09-28 23:46:02',0,1,'bz');

/* 角色列表-菜单 */
INSERT INTO sys_menu VALUES (4002,4001,'角色列表','/role/list','/role-list/index',0,0,'sys:role:list',1,'el-icon-suitcase','2022-09-26 23:46:02','2022-09-28 23:46:02',0,6,'bz');
/* 角色列表里面的按钮权限 */
INSERT INTO sys_menu VALUES (4003,4002,'新增角色',null,null,0,0,'sys:role:list:add',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,7,'bz');
INSERT INTO sys_menu VALUES (4004,4002,'修改角色',null,null,0,0,'sys:role:list:update',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,8,'bz');
INSERT INTO sys_menu VALUES (4005,4002,'删除角色',null,null,0,0,'sys:role:list:delete',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,9,'bz');
INSERT INTO sys_menu VALUES (4006,4002,'分配菜单',null,null,0,0,'sys:role:list:assign-menu',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,9,'bz');

-- 5: 菜单管理

/* 菜单（权限）管理-目录 */

INSERT INTO sys_menu VALUES (5001,0,'菜单管理',null,null,0,0,null,0,'el-icon-eleme','2022-09-26 23:46:02','2022-09-28 23:46:02',0,1,'bz');

/* 菜单（权限）列表-菜单 */
INSERT INTO sys_menu VALUES (5002,5001,'菜单列表','/menu/list','/menu-list/index',0,0,'sys:menu:list',1,'el-icon-s-custom','2022-09-26 23:46:02','2022-09-28 23:46:02',0,10,'bz');

/* 菜单列表里面的按钮权限 */
INSERT INTO sys_menu VALUES (5003,5002,'新增菜单',null,null,0,0,'sys:menu:list:add',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,11,'bz');
INSERT INTO sys_menu VALUES (5004,5002,'修改菜单',null,null,0,0,'sys:menu:list:update',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,12,'bz');
INSERT INTO sys_menu VALUES (5005,5002,'删除菜单',null,null,0,0,'sys:menu:list:delete',2,null,'2022-09-26 23:46:02','2022-09-28 23:46:02',0,13,'bz');




/* 用户-角色关联表 */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
                                 `id` bigint(20) NOT NULL COMMENT '主键',
                                 `user_id` bigint(20) NOT NULL COMMENT '用户id',
                                 `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
                                 PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-角色表';

INSERT INTO sys_user_role VALUES (4001,1001,2001);
INSERT INTO sys_user_role VALUES (4002,1001,2002);

INSERT INTO sys_user_role VALUES (4005,1002,2002);


/* 角色-菜单关联表 */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
                                 `id` bigint(20) NOT NULL COMMENT '主键',
                                 `role_id` bigint(20) NOT NULL COMMENT '角色ID',
                                 `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
                                 PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色-菜单表';

-- 管理员角色的菜单(即使是目录也要把这个目录记录添加到sys_role_menu表中，否则展示不出来）

INSERT INTO sys_role_menu VALUES (5001,2001,1001);
INSERT INTO sys_role_menu VALUES (5002,2001,1002);

INSERT INTO sys_role_menu VALUES (5003,2001,2001);
INSERT INTO sys_role_menu VALUES (5004,2001,2002);
INSERT INTO sys_role_menu VALUES (5005,2001,2003);

INSERT INTO sys_role_menu VALUES (5006,2001,3001);
INSERT INTO sys_role_menu VALUES (5007,2001,3002);
INSERT INTO sys_role_menu VALUES (5008,2001,3003);
INSERT INTO sys_role_menu VALUES (5009,2001,3004);
INSERT INTO sys_role_menu VALUES (5010,2001,3005);
INSERT INTO sys_role_menu VALUES (5011,2001,3006);

INSERT INTO sys_role_menu VALUES (5012,2001,4001);
INSERT INTO sys_role_menu VALUES (5013,2001,4002);
INSERT INTO sys_role_menu VALUES (5014,2001,4003);
INSERT INTO sys_role_menu VALUES (5015,2001,4004);
INSERT INTO sys_role_menu VALUES (5016,2001,4005);
INSERT INTO sys_role_menu VALUES (5017,2001,4006);

INSERT INTO sys_role_menu VALUES (5018,2001,5001);
INSERT INTO sys_role_menu VALUES (5019,2001,5002);
INSERT INTO sys_role_menu VALUES (5020,2001,5003);
INSERT INTO sys_role_menu VALUES (5021,2001,5004);
INSERT INTO sys_role_menu VALUES (5022,2001,5005);



-- 普通用户角色的菜单(即使是目录也要把这个目录记录添加到sys_role_menu表中，否则展示不出来）

INSERT INTO sys_role_menu VALUES (6001,2002,1001);
INSERT INTO sys_role_menu VALUES (6002,2002,1002);
INSERT INTO sys_role_menu VALUES (6003,2002,2001);
INSERT INTO sys_role_menu VALUES (6004,2002,2002);
INSERT INTO sys_role_menu VALUES (6005,2002,2003);

/* grace注册中心的服务表 */

CREATE TABLE `sys_service` (
                                 `id` bigint(20) NOT NULL COMMENT '主键,服务名id',
                                 `service_name` varchar(200) NOT NULL COMMENT '服务名称',
                                 `group_name` varchar(200) NOT NULL COMMENT '分组名称',
                                 `meta_data` varchar(200) NOT NULL COMMENT '该服务的元数据',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='grace注册中心的服务表';


/* grace注册中心的服务实例表（设置了grace.registry.ephemeral: false的实例才会持久化到这个表中） */

CREATE TABLE `sys_service_instance` (
                               `id` bigint(20) NOT NULL COMMENT '主键,服务实例id',
                               `service_id` bigint(20) NOT NULL COMMENT '该实例所属的服务id',
                               `ip_addr` varchar(200) NOT NULL COMMENT '该实例的ip地址',
                               `port` varchar(200) NOT NULL COMMENT '该实例的端口号',
                               `weight` bigint(20) NOT NULL COMMENT '该实例的权重',
                               `meta_data` varchar(200) NOT NULL COMMENT '该实例的元数据',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='grace注册中心的服务实例表';

/* grace命名空间表 */

CREATE TABLE `sys_name_space` (
                                        `id` bigint(20) NOT NULL COMMENT '主键,命令空间id',
                                        `name` varchar(200) NOT NULL COMMENT '命名空间名称',
                                        `desc` varchar(255) NOT NULL COMMENT '描述',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
                                        `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='grace命名空间表';


/* grace配置中心的配置信息表 */

CREATE TABLE `sys_config_info` (
                                  `id` bigint(20) NOT NULL COMMENT '主键,配置信息id',
                                  `config_name` varchar(200) NOT NULL COMMENT '配置信息的名称',
                                  `group_name` varchar(200) NOT NULL COMMENT '该配置信息所属的分组名称',
                                  `content` longtext NOT NULL COMMENT '配置信息的内容',
                                  `md5` varchar(32) NOT NULL COMMENT 'md5值,每一次修改配置内容这个值都要重新生成,用于判断配置文件是否被修改',
                                  `desc` varchar(255) NOT NULL COMMENT '配置信息的描述',
                                  `name_space_id` bigint(20) NOT NULL COMMENT '该配置所属的命名空间id',
                                  `type` varchar(64) NOT NULL COMMENT '配置信息内容的类型(例如:text、yml或yaml、properties、json、xml)',
                                  `user_id` bigint(20) NOT NULL COMMENT '创建该配置信息的用户id',
                                  `ip` varchar(50) NOT NULL COMMENT '创建该配置信息的用户的ip地址',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
                                  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='grace配置中心的配置信息表';


