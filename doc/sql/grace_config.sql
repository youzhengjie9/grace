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

 Date: 23/10/2023 00:25:29
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace配置中心的配置信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'grace配置中心的配置信息表' ROW_FORMAT = Dynamic;

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
  `namespace_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键,命令空间id',
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
