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

 Date: 31/10/2023 01:03:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(0) NOT NULL COMMENT '主键,配置信息id',
  `namespace_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置所属的命名空间id',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置所属的分组名称',
  `data_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置的名称',
  `current_version_id` bigint(0) NOT NULL COMMENT '该配置当前的版本id',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '配置的内容',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置内容的MD5值,用于判断配置文件是否被修改',
  `config_desc` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置的描述',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置的类型',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建该配置的用户的id',
  `create_user_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建该配置的用户的ip地址',
  `create_time` datetime(0) NOT NULL COMMENT '创建该配置的时间',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后一次修改该配置的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace配置中心的配置信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (7572651925832709, '', 'DEFAULT_GROUP', 'user.properties', 7592676084941829, 'user.id=520\nuser.name=youzhengjie\nuser.age=18', '1086ef9b1d0afa959870fcaaef8655cb', 'user配置', 'properties', 666888, '127.0.0.1', '2023-10-19 11:25:00', '2023-10-23 00:17:25');
INSERT INTO `sys_config` VALUES (7573148522120197, 'dev-namespace', 'DEFAULT_GROUP', 'system.json', 7575794877268997, '{\n  \"user\":{\n    \"id\": 789123,\n    \"name\": \"yzj\",\n    \"age\": 20\n  }\n}', '8cdf7d0626ac7d6e9d34ff4ccc6b313b', 'system的json配置', 'json', 666888, '127.0.0.1', '2023-10-19 13:31:18', '2023-10-20 00:44:18');
INSERT INTO `sys_config` VALUES (7592250446119941, '', 'grace_group', 'grace-console.yml', 7592677071127557, 'server:\r\n  port: 8848\r\n \r\nspring:\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n\r\n# mybatis-plus\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath*:/mapper/**/*.xml', '4e134cadbd9d243ad99b6befd578653a', 'grace-console的配置', 'yaml', 666888, '127.0.0.1', '2023-10-22 22:29:10', '2023-10-23 00:17:40');
INSERT INTO `sys_config` VALUES (7592589870826501, '', 'DEFAULT_GROUP', '123', 7592589870629893, 'aaa:123', '01ac24e83d03b79704cf090f5c2fff23', '123', 'properties', 666888, '127.0.0.1', '2023-10-22 23:55:29', '2023-10-22 23:55:29');
INSERT INTO `sys_config` VALUES (7592592782918661, '', 'DEFAULT_GROUP', 'xxx', 7592592782787589, 'xxx123', '0884d3007f1937b76b2e6548a17f36a5', 'xxx', 'text', 666888, '127.0.0.1', '2023-10-22 23:56:14', '2023-10-22 23:56:14');
INSERT INTO `sys_config` VALUES (7592594016764933, '', 'DEFAULT_GROUP', 'yyy', 7592594016568325, 'yyy789', '477eb6e0d3dc7dbfac7760c82ab8dfeb', 'yyy123456', 'text', 666888, '127.0.0.1', '2023-10-22 23:56:33', '2023-10-22 23:56:33');
INSERT INTO `sys_config` VALUES (7592597833909253, '', 'DEFAULT_GROUP', 'abc', 7592597833843717, 'abc:789', '10db935f7889936c76865fc941a07f5a', 'abc', 'properties', 666888, '127.0.0.1', '2023-10-22 23:57:31', '2023-10-22 23:57:31');
INSERT INTO `sys_config` VALUES (7592600112006149, '', 'DEFAULT_GROUP', 'test123', 7592600111875077, 'test: 666', 'dac3b15758329abc0a845e3eb2a947ad', 'test123', 'yaml', 666888, '127.0.0.1', '2023-10-22 23:58:06', '2023-10-22 23:58:06');
INSERT INTO `sys_config` VALUES (7592611645883397, '', 'DEFAULT_GROUP', 'qwer', 7592611645752325, 'qwer123', '553e83ca69693b33ef73958c04b7a315', 'qwer', 'text', 666888, '127.0.0.1', '2023-10-23 00:01:02', '2023-10-23 00:01:02');
INSERT INTO `sys_config` VALUES (7592613482464261, '', 'DEFAULT_GROUP', 'haha', 7592613482267653, '哈哈123', 'ff9192ab58cde6c040c1ed2d216b20fa', 'haha', 'text', 666888, '127.0.0.1', '2023-10-23 00:01:30', '2023-10-23 00:01:30');
INSERT INTO `sys_config` VALUES (7592615856047109, '', 'DEFAULT_GROUP', 'ConfigMapper.xml', 7592615855981573, '\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n    \r\n\r\n    \r\n        DELETE\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            current_version_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n', '3527149d192f985156c1460d289f1c99', 'ConfigMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:06', '2023-10-23 00:02:06');
INSERT INTO `sys_config` VALUES (7592617060008965, '', 'DEFAULT_GROUP', 'ConfigVersionMapper.xml', 7592617059943429, '\r\n\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            operation_type,\r\n            operation_time\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n    \r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            md5,\r\n            config_desc,\r\n            type,\r\n            operation_user_id,\r\n            operation_user_ip,\r\n            operation_type,\r\n            operation_time\r\n        FROM sys_config_version\r\n        WHERE id = #{configVersionId}\r\n    \r\n    \r\n        SELECT\r\n            data_id,\r\n            group_name\r\n        FROM\r\n            sys_config_version\r\n        WHERE\r\n            namespace_id = #{namespaceId}\r\n    \r\n', '7380afc5a472c12974ca96335224f50a', 'ConfigVersionMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:24', '2023-10-23 00:02:24');
INSERT INTO `sys_config` VALUES (7592618302702597, '', 'DEFAULT_GROUP', 'NamespaceMapper.xml', 7592618302571525, '', 'd41d8cd98f00b204e9800998ecf8427e', 'NamespaceMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:43', '2023-10-23 00:02:43');

-- ----------------------------
-- Table structure for sys_config_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_version`;
CREATE TABLE `sys_config_version`  (
  `id` bigint(0) NOT NULL COMMENT '主键,历史配置id（通过这个id去获取到\"某一个\"历史配置，但是获取某一个配置的“历史配置列表”还是通过namespaceId、groupName、dataId）',
  `namespace_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置所属的命名空间id',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置所属的分组名称',
  `data_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'dataId。也就是配置的名称',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '配置的内容',
  `md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置内容的MD5值',
  `config_desc` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置的描述',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置的类型',
  `operation_user_id` bigint(0) NULL DEFAULT NULL COMMENT '操作这个配置的用户的id',
  `operation_user_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作这个配置的用户的ip地址',
  `operation_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '这个配置被执行了什么操作（比如新增、修改、删除）',
  `operation_time` datetime(0) NOT NULL COMMENT '操作这个配置的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace配置中心的配置信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config_version
-- ----------------------------
INSERT INTO `sys_config_version` VALUES (7575791996961797, '', 'DEFAULT_GROUP', 'user.properties', 'user.id=1314520\nuser.name=youzhengjie666\nuser.age=22', '43da6bc11334e4a942d2691e8f3fb7cb', 'user配置', 'properties', 123456, '127.0.0.1', '更新', '2023-10-20 00:43:34');
INSERT INTO `sys_config_version` VALUES (7575793346872325, 'dev-namespace', 'DEFAULT_GROUP', 'system.json', '{\n  \"user\":{\n    \"id\": 666888,\n    \"name\": \"youzhengjie\",\n    \"age\": 36\n  }\n}', 'b21d0f60cbec6162dffd4091f4fbdf02', 'system的json配置', 'json', 123456, '127.0.0.1', '更新', '2023-10-20 00:43:55');
INSERT INTO `sys_config_version` VALUES (7575794877268997, 'dev-namespace', 'DEFAULT_GROUP', 'system.json', '{\n  \"user\":{\n    \"id\": 789123,\n    \"name\": \"yzj\",\n    \"age\": 20\n  }\n}', '8cdf7d0626ac7d6e9d34ff4ccc6b313b', 'system的json配置', 'json', 123456, '127.0.0.1', '更新', '2023-10-20 00:44:18');
INSERT INTO `sys_config_version` VALUES (7575796800029701, '', 'DEFAULT_GROUP', '123', '123', '202cb962ac59075b964b07152d234b70', '123', 'yaml', 5201314, '127.0.0.1', '删除', '2023-10-20 00:44:47');
INSERT INTO `sys_config_version` VALUES (7575799120921605, '', 'DEFAULT_GROUP', 'user.properties', 'user.id=520\nuser.name=youzhengjie\nuser.age=18', '1086ef9b1d0afa959870fcaaef8655cb', 'user配置', 'properties', 123456, '127.0.0.1', '更新', '2023-10-20 00:45:23');
INSERT INTO `sys_config_version` VALUES (7592245888680965, '', 'DEFAULT_GROUP', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n  # spring-data-redis配置\r\n  redis:\r\n    database: 1\r\n    host: 192.168.184.100\r\n    port: 6379', 'd17b2efcdb2fcf18c86559b9fd2b8db5', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '新增', '2023-10-22 22:28:01');
INSERT INTO `sys_config_version` VALUES (7592248663540741, '', 'DEFAULT_GROUP', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n  # spring-data-redis配置\r\n  redis:\r\n    database: 1\r\n    host: 192.168.184.100\r\n    port: 6379', 'd17b2efcdb2fcf18c86559b9fd2b8db5', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '删除', '2023-10-22 22:28:43');
INSERT INTO `sys_config_version` VALUES (7592250446054405, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB', '79652eae05edf46147fb187df3ffc07e', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '新增', '2023-10-22 22:29:10');
INSERT INTO `sys_config_version` VALUES (7592251202536453, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher', '9511862f41caad17fc35b39de0aafd4b', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 22:29:22');
INSERT INTO `sys_config_version` VALUES (7592251876770821, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console', 'c2fa6e5f6cc77eb024f840d793a46abc', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 22:29:32');
INSERT INTO `sys_config_version` VALUES (7592252910142469, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207', '1467cc8130fa87f658510842b2a4c70b', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 22:29:48');
INSERT INTO `sys_config_version` VALUES (7592253574349829, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 3MB\r\n      max-request-size: 6MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n  # spring-data-redis配置\r\n  redis:\r\n    database: 1\r\n    host: 192.168.184.100\r\n    port: 6379', 'd17b2efcdb2fcf18c86559b9fd2b8db5', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 22:29:58');
INSERT INTO `sys_config_version` VALUES (7592269955859461, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 8MB\r\n      max-request-size: 10MB\r\n  mvc:\r\n    pathmatch:\r\n      #swagger所需要的配置\r\n      matching-strategy: ant_path_matcher\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n  # spring-data-redis配置\r\n  redis:\r\n    database: 8\r\n    host: 192.168.184.100\r\n    port: 6379', 'e279225f857dd91a3cfffa157905b04b', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 22:34:08');
INSERT INTO `sys_config_version` VALUES (7592541330211845, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 30MB\r\n      max-request-size: 60MB', '6288e00bd0f5c96838786b1fdb1cef8a', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 23:43:09');
INSERT INTO `sys_config_version` VALUES (7592541934126085, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 30MB', '8153cdef1d33d470054d08d59a575570', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 23:43:18');
INSERT INTO `sys_config_version` VALUES (7592542515561477, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 20MB', '3d938bfa703e3a505165e8509b0ac4db', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 23:43:27');
INSERT INTO `sys_config_version` VALUES (7592542770955269, '', 'grace_group', 'grace-console.yml', 'spring:\r\n  servlet:\r\n    #文件上传配置\r\n    multipart:\r\n      max-file-size: 10MB', 'ebc649faa478ea7bfd6d830c18af50c3', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 23:43:31');
INSERT INTO `sys_config_version` VALUES (7592545573471237, '', 'grace_group', 'grace-console.yml', 'server:\r\n  port: 8848\r\n\r\nspring:\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n\r\n# mybatis-plus\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath*:/mapper/**/*.xml', 'b3061d00d2894aa70f5d021cb3434575', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-22 23:44:13');
INSERT INTO `sys_config_version` VALUES (7592589870629893, '', 'DEFAULT_GROUP', '123', 'aaa:123', '01ac24e83d03b79704cf090f5c2fff23', '123', 'properties', 123456, '127.0.0.1', '新增', '2023-10-22 23:55:29');
INSERT INTO `sys_config_version` VALUES (7592592782787589, '', 'DEFAULT_GROUP', 'xxx', 'xxx123', '0884d3007f1937b76b2e6548a17f36a5', 'xxx', 'text', 123456, '127.0.0.1', '新增', '2023-10-22 23:56:14');
INSERT INTO `sys_config_version` VALUES (7592594016568325, '', 'DEFAULT_GROUP', 'yyy', 'yyy789', '477eb6e0d3dc7dbfac7760c82ab8dfeb', 'yyy123456', 'text', 123456, '127.0.0.1', '新增', '2023-10-22 23:56:33');
INSERT INTO `sys_config_version` VALUES (7592597833843717, '', 'DEFAULT_GROUP', 'abc', 'abc:789', '10db935f7889936c76865fc941a07f5a', 'abc', 'properties', 123456, '127.0.0.1', '新增', '2023-10-22 23:57:31');
INSERT INTO `sys_config_version` VALUES (7592600111875077, '', 'DEFAULT_GROUP', 'test123', 'test: 666', 'dac3b15758329abc0a845e3eb2a947ad', 'test123', 'yaml', 123456, '127.0.0.1', '新增', '2023-10-22 23:58:06');
INSERT INTO `sys_config_version` VALUES (7592611645752325, '', 'DEFAULT_GROUP', 'qwer', 'qwer123', '553e83ca69693b33ef73958c04b7a315', 'qwer', 'text', 123456, '127.0.0.1', '新增', '2023-10-23 00:01:02');
INSERT INTO `sys_config_version` VALUES (7592613482267653, '', 'DEFAULT_GROUP', 'haha', '哈哈123', 'ff9192ab58cde6c040c1ed2d216b20fa', 'haha', 'text', 123456, '127.0.0.1', '新增', '2023-10-23 00:01:30');
INSERT INTO `sys_config_version` VALUES (7592615855981573, '', 'DEFAULT_GROUP', 'ConfigMapper.xml', '\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n    \r\n\r\n    \r\n        DELETE\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            current_version_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n', '3527149d192f985156c1460d289f1c99', 'ConfigMapper.xml', 'xml', 123456, '127.0.0.1', '新增', '2023-10-23 00:02:06');
INSERT INTO `sys_config_version` VALUES (7592617059943429, '', 'DEFAULT_GROUP', 'ConfigVersionMapper.xml', '\r\n\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            operation_type,\r\n            operation_time\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n    \r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            md5,\r\n            config_desc,\r\n            type,\r\n            operation_user_id,\r\n            operation_user_ip,\r\n            operation_type,\r\n            operation_time\r\n        FROM sys_config_version\r\n        WHERE id = #{configVersionId}\r\n    \r\n    \r\n        SELECT\r\n            data_id,\r\n            group_name\r\n        FROM\r\n            sys_config_version\r\n        WHERE\r\n            namespace_id = #{namespaceId}\r\n    \r\n', '7380afc5a472c12974ca96335224f50a', 'ConfigVersionMapper.xml', 'xml', 123456, '127.0.0.1', '新增', '2023-10-23 00:02:24');
INSERT INTO `sys_config_version` VALUES (7592618302571525, '', 'DEFAULT_GROUP', 'NamespaceMapper.xml', '', 'd41d8cd98f00b204e9800998ecf8427e', 'NamespaceMapper.xml', 'xml', 123456, '127.0.0.1', '新增', '2023-10-23 00:02:43');
INSERT INTO `sys_config_version` VALUES (7592675481879557, '', 'grace_group', 'grace-console.yml', 'server:\r\n  port: 8848\r\n \r\nspring:\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n\r\n# mybatis-plus\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath*:/mapper/**/*.xml', '4e134cadbd9d243ad99b6befd578653a', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-23 00:17:16');
INSERT INTO `sys_config_version` VALUES (7592676084941829, '', 'DEFAULT_GROUP', 'user.properties', 'user.id=520\nuser.name=youzhengjie\nuser.age=18', '1086ef9b1d0afa959870fcaaef8655cb', 'user配置', 'properties', 123456, '127.0.0.1', '修改', '2023-10-23 00:17:25');
INSERT INTO `sys_config_version` VALUES (7592677071127557, '', 'grace_group', 'grace-console.yml', 'server:\r\n  port: 8848\r\n \r\nspring:\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n\r\n# mybatis-plus\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath*:/mapper/**/*.xml', '4e134cadbd9d243ad99b6befd578653a', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-10-23 00:17:40');

-- ----------------------------
-- Table structure for sys_namespace
-- ----------------------------
DROP TABLE IF EXISTS `sys_namespace`;
CREATE TABLE `sys_namespace`  (
  `namespace_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键,命令空间id',
  `namespace_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '命名空间名称',
  `max_config_count` int(0) NULL DEFAULT 200 COMMENT '该命名空间最大的配置数',
  `namespace_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '命名空间描述',
  PRIMARY KEY (`namespace_id`) USING BTREE,
  UNIQUE INDEX `namespace_name`(`namespace_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace命名空间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_namespace
-- ----------------------------
INSERT INTO `sys_namespace` VALUES ('dev-namespace', 'dev', 200, '开发环境');
INSERT INTO `sys_namespace` VALUES ('test-namespace', 'test', 200, '测试环境');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '用户状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1001, 'root', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0);
INSERT INTO `sys_user` VALUES (1002, 'test', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0);

SET FOREIGN_KEY_CHECKS = 1;
