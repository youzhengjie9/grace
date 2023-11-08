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

 Date: 07/11/2023 22:08:23
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
INSERT INTO `sys_config` VALUES (7592589870826501, '', 'DEFAULT_GROUP', '123', 7592589870629893, 'aaa:123', '01ac24e83d03b79704cf090f5c2fff23', '123', 'properties', 666888, '127.0.0.1', '2023-10-22 23:55:29', '2023-10-22 23:55:29');
INSERT INTO `sys_config` VALUES (7592592782918661, '', 'DEFAULT_GROUP', 'xxx', 7592592782787589, 'xxx123', '0884d3007f1937b76b2e6548a17f36a5', 'xxx', 'text', 666888, '127.0.0.1', '2023-10-22 23:56:14', '2023-10-22 23:56:14');
INSERT INTO `sys_config` VALUES (7592594016764933, '', 'DEFAULT_GROUP', 'yyy', 7592594016568325, 'yyy789', '477eb6e0d3dc7dbfac7760c82ab8dfeb', 'yyy123456', 'text', 666888, '127.0.0.1', '2023-10-22 23:56:33', '2023-10-22 23:56:33');
INSERT INTO `sys_config` VALUES (7592597833909253, '', 'DEFAULT_GROUP', 'abc', 7592597833843717, 'abc:789', '10db935f7889936c76865fc941a07f5a', 'abc', 'properties', 666888, '127.0.0.1', '2023-10-22 23:57:31', '2023-10-22 23:57:31');
INSERT INTO `sys_config` VALUES (7592600112006149, '', 'DEFAULT_GROUP', 'test123', 7592600111875077, 'test: 666', 'dac3b15758329abc0a845e3eb2a947ad', 'test123', 'yaml', 666888, '127.0.0.1', '2023-10-22 23:58:06', '2023-10-22 23:58:06');
INSERT INTO `sys_config` VALUES (7592615856047109, '', 'DEFAULT_GROUP', 'ConfigMapper.xml', 7592615855981573, '\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name LIKE CONCAT(\'%\',#{groupName},\'%\')\r\n        AND data_id LIKE CONCAT(\'%\',#{dataId},\'%\')\r\n    \r\n\r\n    \r\n        DELETE\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n    \r\n        SELECT\r\n            current_version_id\r\n        FROM\r\n            sys_config\r\n        WHERE namespace_id = #{namespaceId}\r\n        AND group_name = #{groupName}\r\n        AND data_id = #{dataId}\r\n    \r\n\r\n', '3527149d192f985156c1460d289f1c99', 'ConfigMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:06', '2023-10-23 00:02:06');
INSERT INTO `sys_config` VALUES (7592617060008965, '', 'DEFAULT_GROUP', 'ConfigVersionMapper.xml', 7592617059943429, '\r\n\r\n\r\n\r\n\r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            operation_type,\r\n            operation_time\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n        ORDER BY id DESC\r\n        LIMIT #{page},#{size}\r\n    \r\n    \r\n        SELECT\r\n            COUNT(*)\r\n        FROM\r\n            sys_config_version\r\n        WHERE namespace_id = #{namespaceId}\r\n          AND group_name = #{groupName}\r\n          AND data_id = #{dataId}\r\n    \r\n    \r\n        SELECT\r\n            id,\r\n            namespace_id,\r\n            group_name,\r\n            data_id,\r\n            content,\r\n            md5,\r\n            config_desc,\r\n            type,\r\n            operation_user_id,\r\n            operation_user_ip,\r\n            operation_type,\r\n            operation_time\r\n        FROM sys_config_version\r\n        WHERE id = #{configVersionId}\r\n    \r\n    \r\n        SELECT\r\n            data_id,\r\n            group_name\r\n        FROM\r\n            sys_config_version\r\n        WHERE\r\n            namespace_id = #{namespaceId}\r\n    \r\n', '7380afc5a472c12974ca96335224f50a', 'ConfigVersionMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:24', '2023-10-23 00:02:24');
INSERT INTO `sys_config` VALUES (7592618302702597, '', 'DEFAULT_GROUP', 'NamespaceMapper.xml', 7592618302571525, '', 'd41d8cd98f00b204e9800998ecf8427e', 'NamespaceMapper.xml', 'xml', 666888, '127.0.0.1', '2023-10-23 00:02:43', '2023-10-23 00:02:43');
INSERT INTO `sys_config` VALUES (7646826115171333, '', 'DEFAULT_GROUP', 'grace-order-dev.yaml', 7646860471305221, 'product:\n  id: 1001\n  productName: product-1001 \n  price: 101\n\nproject:\n  version: v1.0.1', '7076d4c03754c6fd2299b7083fd0a09e', 'grace-order-dev.yaml配置', 'yaml', 666888, '127.0.0.1', '2023-11-01 13:48:29', '2023-11-01 13:57:13');
INSERT INTO `sys_config` VALUES (7646833820304389, '', 'DEFAULT_GROUP', 'grace-order-dev.properties', 7646861857653765, 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 666888, '127.0.0.1', '2023-11-01 13:50:26', '2023-11-01 13:57:34');
INSERT INTO `sys_config` VALUES (7646841237931013, '', 'DEFAULT_GROUP', 'grace-order-dev.json', 7646861083804677, '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 666888, '127.0.0.1', '2023-11-01 13:52:19', '2023-11-01 13:57:22');
INSERT INTO `sys_config` VALUES (7649442153038853, '', 'DEFAULT_GROUP', 'asd.properties', 7649442152907781, 'product.id=77778\r\nproduct.productName=product-7878\r\nproduct.price=7878\r\n\r\nproject.version=v78', '645bcd8cb0755a549d03a05d2e89c26e', '', 'properties', 1001, '127.0.0.1', '2023-11-02 00:53:46', '2023-11-02 00:53:46');

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
INSERT INTO `sys_config_version` VALUES (7646805466678277, '', 'grace_group', 'grace-console.yml', 'server:\r\n  port: 8848\r\n \r\nspring:\r\n  application:\r\n    name: grace-console\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/grace_config?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false\r\n    username: root\r\n    password: 18420163207\r\n\r\n# mybatis-plus\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath*:/mapper/**/*.xml', '4e134cadbd9d243ad99b6befd578653a', 'grace-console的配置', 'yaml', 123456, '127.0.0.1', '删除', '2023-11-01 13:43:14');
INSERT INTO `sys_config_version` VALUES (7646826114974725, '', 'DEFAULT_GROUP', 'grace-order-dev.yaml', 'product:\n  id: 1001\n  productName: 商品1001 \n  price: 101\n\nproject:\n  version: v1.0.1', '7b6215d983e2d189f86dc04d8c0d8990', 'grace-order-dev.yaml配置', 'yaml', 123456, '127.0.0.1', '新增', '2023-11-01 13:48:29');
INSERT INTO `sys_config_version` VALUES (7646833820173317, '', 'DEFAULT_GROUP', 'grace-order-dev.properties', 'product.id=2002\nproduct.productName=商品2002 \nproduct.price=202\n\nproject.version=v2.0.2', '9b8d090e4509a72a7913a7fcb7e93e39', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '新增', '2023-11-01 13:50:26');
INSERT INTO `sys_config_version` VALUES (7646841237799941, '', 'DEFAULT_GROUP', 'grace-order-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"商品3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', 'eaf0168817d2d35ecd01302f61174155', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '新增', '2023-11-01 13:52:19');
INSERT INTO `sys_config_version` VALUES (7646860471305221, '', 'DEFAULT_GROUP', 'grace-order-dev.yaml', 'product:\n  id: 1001\n  productName: product-1001 \n  price: 101\n\nproject:\n  version: v1.0.1', '7076d4c03754c6fd2299b7083fd0a09e', 'grace-order-dev.yaml配置', 'yaml', 123456, '127.0.0.1', '修改', '2023-11-01 13:57:13');
INSERT INTO `sys_config_version` VALUES (7646861083804677, '', 'DEFAULT_GROUP', 'grace-order-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '修改', '2023-11-01 13:57:22');
INSERT INTO `sys_config_version` VALUES (7646861857653765, '', 'DEFAULT_GROUP', 'grace-order-dev.properties', 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '修改', '2023-11-01 13:57:34');
INSERT INTO `sys_config_version` VALUES (7649419946099717, '', 'DEFAULT_GROUP', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '新增', '2023-11-02 00:48:07');
INSERT INTO `sys_config_version` VALUES (7649424725836805, '', 'DEFAULT_GROUP', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '删除', '2023-11-02 00:49:20');
INSERT INTO `sys_config_version` VALUES (7649425436115973, '', 'DEFAULT_GROUP', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '新增', '2023-11-02 00:49:31');
INSERT INTO `sys_config_version` VALUES (7649426338284549, '', 'DEFAULT_GROUP66', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '新增', '2023-11-02 00:49:45');
INSERT INTO `sys_config_version` VALUES (7649426720162821, '', 'DEFAULT_GROUP66', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '删除', '2023-11-02 00:49:51');
INSERT INTO `sys_config_version` VALUES (7649429641036805, '', 'DEFAULT_GROUP66', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '新增', '2023-11-02 00:50:35');
INSERT INTO `sys_config_version` VALUES (7649431258203141, '', 'DEFAULT_GROUP66', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '删除', '2023-11-02 00:51:00');
INSERT INTO `sys_config_version` VALUES (7649442152776709, '', 'DEFAULT_GROUP', 'asd.properties', 'product.id=66666\r\nproduct.productName=product-6666\r\nproduct.price=666\r\n\r\nproject.version=v6.6.6', '57aefd12b238715d6f8606027eaa74df', '', 'properties', 123456, '127.0.0.1', '删除', '2023-11-02 00:53:46');
INSERT INTO `sys_config_version` VALUES (7649442152907781, '', 'DEFAULT_GROUP', 'asd.properties', 'product.id=77778\r\nproduct.productName=product-7878\r\nproduct.price=7878\r\n\r\nproject.version=v78', '645bcd8cb0755a549d03a05d2e89c26e', '', 'properties', 123456, '127.0.0.1', '新增', '2023-11-02 00:53:46');
INSERT INTO `sys_config_version` VALUES (7660844588729349, 'dev-namespace', 'DEFAULT_GROUP', 'grace-order-dev.properties', 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '新增', '2023-11-04 01:13:34');
INSERT INTO `sys_config_version` VALUES (7660844598363141, 'dev-namespace', 'DEFAULT_GROUP', 'grace-order-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '新增', '2023-11-04 01:13:34');
INSERT INTO `sys_config_version` VALUES (7660846134264837, 'dev-namespace', 'DEFAULT_GROUP', 'grace-order-dev.properties', 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '删除', '2023-11-04 01:13:57');
INSERT INTO `sys_config_version` VALUES (7660846256161797, 'dev-namespace', 'DEFAULT_GROUP', 'grace-order-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '删除', '2023-11-04 01:13:59');
INSERT INTO `sys_config_version` VALUES (7660847313912837, 'dev-namespace', 'DEFAU123LT_GROUP', 'grace-orde123r-dev.properties', 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '新增', '2023-11-04 01:14:15');
INSERT INTO `sys_config_version` VALUES (7660847314502661, 'dev-namespace', 'DEFAU223LT_GROUP', 'grace-ord123er-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '新增', '2023-11-04 01:14:15');
INSERT INTO `sys_config_version` VALUES (7660848380052485, 'dev-namespace', 'DEFAU123LT_GROUP', 'grace-orde123r-dev.properties', 'product.id=2002\nproduct.productName=product-2002 \nproduct.price=202\n\nproject.version=v2.0.2', '8a210daa1aa0261dc68dac35c49c62a8', 'grace-order-dev.properties配置', 'properties', 123456, '127.0.0.1', '删除', '2023-11-04 01:14:31');
INSERT INTO `sys_config_version` VALUES (7660848558048261, 'dev-namespace', 'DEFAU223LT_GROUP', 'grace-ord123er-dev.json', '{\n  \"product\":{\n    \"id\": 3003,\n    \"productName\": \"product-3003\",\n    \"price\": 303\n  },\n  \"project\":{\n    \"version\": \"v3.0.3\"\n  }\n}', '9f9006ea46d6165014ec83e0f1dcc77e', 'grace-order-dev.json配置', 'json', 123456, '127.0.0.1', '删除', '2023-11-04 01:14:34');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (3001, 0, '配置管理', NULL, NULL, 0, 0, NULL, 0, 'el-icon-eleme', '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 1, 'bz');
INSERT INTO `sys_menu` VALUES (3002, 3001, '配置列表', '/config/list', '/config/list/index', 0, 0, 'config:list', 1, 'el-icon-s-order', '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (3003, 3002, '创建配置', NULL, NULL, 0, 0, 'config:add', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 3, 'bz');
INSERT INTO `sys_menu` VALUES (3004, 3002, '修改配置', NULL, NULL, 0, 0, 'config:modify', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 4, 'bz');
INSERT INTO `sys_menu` VALUES (3005, 3002, '删除配置', NULL, NULL, 0, 0, 'config:delete', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 5, 'bz');
INSERT INTO `sys_menu` VALUES (3006, 3002, '配置详情', NULL, NULL, 0, 0, 'config:detail', 2, '', '2023-11-06 17:03:20', '2023-11-06 17:03:23', 0, 6, 'bz');
INSERT INTO `sys_menu` VALUES (3007, 3002, '导入配置', NULL, NULL, 0, 0, 'config:import', 2, '', '2023-11-06 17:03:20', '2023-11-06 17:03:23', 0, 7, 'bz');
INSERT INTO `sys_menu` VALUES (3008, 3002, '导出配置', NULL, NULL, 0, 0, 'config:export', 2, '', '2023-11-06 17:03:20', '2023-11-06 17:03:23', 0, 8, 'bz');
INSERT INTO `sys_menu` VALUES (3009, 3002, '克隆配置', NULL, NULL, 0, 0, 'config:clone', 2, '', '2023-11-06 17:03:20', '2023-11-06 17:03:23', 0, 9, 'bz');
INSERT INTO `sys_menu` VALUES (3010, 3001, '配置版本', '/config/version/list', '/config/version/list/index', 0, 0, 'config:version:list', 1, 'el-icon-s-order', '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 10, 'bz');
INSERT INTO `sys_menu` VALUES (3011, 3010, '版本详情', NULL, NULL, 0, 0, 'version:detail', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 11, 'bz');
INSERT INTO `sys_menu` VALUES (3012, 3010, '版本回滚', NULL, NULL, 0, 0, 'version:rollback', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 12, 'bz');
INSERT INTO `sys_menu` VALUES (3013, 0, '服务管理', '', '', 0, 0, '', 0, 'el-icon-s-order', '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (3014, 3013, '服务列表', '/service/list', '/service/list/index', 0, 0, 'service:list', 1, 'el-icon-s-order', '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 2, 'bz');
INSERT INTO `sys_menu` VALUES (3015, 3013, '服务详情', NULL, NULL, 0, 0, 'service:detail', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 14, 'bz');
INSERT INTO `sys_menu` VALUES (3016, 3013, '删除服务', NULL, NULL, 0, 0, 'service:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 14, 'bz');
INSERT INTO `sys_menu` VALUES (3017, 3013, '修改服务', NULL, NULL, 0, 0, 'service:modify', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 14, 'bz');
INSERT INTO `sys_menu` VALUES (3018, 3013, '修改服务实例', NULL, NULL, 0, 0, 'instance:modify', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 14, 'bz');
INSERT INTO `sys_menu` VALUES (3019, 0, '用户管理', '/user/list', '/user/list/index', 0, 0, 'user:list', 1, 'el-icon-s-order', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 13, 'bz');
INSERT INTO `sys_menu` VALUES (3020, 3019, '新增用户', NULL, NULL, 0, 0, 'user:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 14, 'bz');
INSERT INTO `sys_menu` VALUES (3021, 3019, '修改用户', NULL, NULL, 0, 0, 'user:modify', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 15, 'bz');
INSERT INTO `sys_menu` VALUES (3022, 3019, '删除用户', NULL, NULL, 0, 0, 'user:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 16, 'bz');
INSERT INTO `sys_menu` VALUES (3023, 3019, '分配角色', NULL, NULL, 0, 0, 'assign:role', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 17, 'bz');
INSERT INTO `sys_menu` VALUES (3024, 0, '角色管理', '/role/list', '/role/list/index', 0, 0, 'role:list', 1, 'el-icon-suitcase', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 18, 'bz');
INSERT INTO `sys_menu` VALUES (3025, 3024, '新增角色', NULL, NULL, 0, 0, 'role:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 19, 'bz');
INSERT INTO `sys_menu` VALUES (3026, 3024, '修改角色', NULL, NULL, 0, 0, 'role:modify', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 20, 'bz');
INSERT INTO `sys_menu` VALUES (3027, 3024, '删除角色', NULL, NULL, 0, 0, 'role:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 21, 'bz');
INSERT INTO `sys_menu` VALUES (3028, 3024, '分配菜单', NULL, NULL, 0, 0, 'assign:menu', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 22, 'bz');
INSERT INTO `sys_menu` VALUES (3029, 0, '菜单管理', '/menu/list', '/menu/list/index', 0, 0, 'menu:list', 1, 'el-icon-s-custom', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 23, 'bz');
INSERT INTO `sys_menu` VALUES (3030, 3029, '新增菜单', NULL, NULL, 0, 0, 'menu:add', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 24, 'bz');
INSERT INTO `sys_menu` VALUES (3031, 3029, '修改菜单', NULL, NULL, 0, 0, 'menu:modify', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 25, 'bz');
INSERT INTO `sys_menu` VALUES (3032, 3029, '删除菜单', NULL, NULL, 0, 0, 'menu:delete', 2, NULL, '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 26, 'bz');
INSERT INTO `sys_menu` VALUES (3033, 0, '命名空间', '/namespace/list', '/namespace/list/index', 0, 0, 'namespace:list', 1, 'el-icon-s-custom', '2022-09-26 23:46:02', '2022-09-28 23:46:02', 0, 27, 'bz');
INSERT INTO `sys_menu` VALUES (3034, 3033, '创建命名空间', NULL, NULL, 0, 0, 'namespace:add', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 28, 'bz');
INSERT INTO `sys_menu` VALUES (3035, 3033, '修改命名空间', NULL, NULL, 0, 0, 'namespace:modify', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 29, 'bz');
INSERT INTO `sys_menu` VALUES (3036, 3033, '删除命名空间', NULL, NULL, 0, 0, 'namespace:delete', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 30, 'bz');
INSERT INTO `sys_menu` VALUES (3037, 3033, '命名空间详情', NULL, NULL, 0, 0, 'namespace:detail', 2, NULL, '2023-11-06 23:46:02', '2023-11-06 23:46:02', 0, 31, 'bz');

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
INSERT INTO `sys_role` VALUES (2003, '黑名单角色', 'blackListUser', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '黑名单角色');
INSERT INTO `sys_role` VALUES (2004, '测试角色1', 'testRole1', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色1');
INSERT INTO `sys_role` VALUES (2005, '测试角色2', 'testRole2', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色2');
INSERT INTO `sys_role` VALUES (2006, '测试角色3', 'testRole3', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色3');
INSERT INTO `sys_role` VALUES (2007, '测试角色4', 'testRole4', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色4');
INSERT INTO `sys_role` VALUES (2008, '测试角色5', 'testRole5', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色5');
INSERT INTO `sys_role` VALUES (2009, '测试角色6', 'testRole6', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色6');
INSERT INTO `sys_role` VALUES (2010, '测试角色7', 'testRole7', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色7');
INSERT INTO `sys_role` VALUES (2011, '测试角色8', 'testRole8', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色8');
INSERT INTO `sys_role` VALUES (1581238859201536002, '测试角色9', 'testRole9', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色9');
INSERT INTO `sys_role` VALUES (1581238859201581195, '测试角色10', 'testRole10', 0, 0, '2022-09-25 10:23:02', '2022-09-28 10:33:02', '测试角色10');

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
INSERT INTO `sys_role_menu` VALUES (5001, 2001, 3001);
INSERT INTO `sys_role_menu` VALUES (5002, 2001, 3002);
INSERT INTO `sys_role_menu` VALUES (5003, 2001, 3003);
INSERT INTO `sys_role_menu` VALUES (5004, 2001, 3004);
INSERT INTO `sys_role_menu` VALUES (5005, 2001, 3005);
INSERT INTO `sys_role_menu` VALUES (5006, 2001, 3006);
INSERT INTO `sys_role_menu` VALUES (5007, 2001, 3007);
INSERT INTO `sys_role_menu` VALUES (5008, 2001, 3008);
INSERT INTO `sys_role_menu` VALUES (5009, 2001, 3009);
INSERT INTO `sys_role_menu` VALUES (5010, 2001, 3010);
INSERT INTO `sys_role_menu` VALUES (5011, 2001, 3011);
INSERT INTO `sys_role_menu` VALUES (5012, 2001, 3012);
INSERT INTO `sys_role_menu` VALUES (5013, 2001, 3013);
INSERT INTO `sys_role_menu` VALUES (5014, 2001, 3014);
INSERT INTO `sys_role_menu` VALUES (5015, 2001, 3015);
INSERT INTO `sys_role_menu` VALUES (5016, 2001, 3016);
INSERT INTO `sys_role_menu` VALUES (5017, 2001, 3017);
INSERT INTO `sys_role_menu` VALUES (5018, 2001, 3018);
INSERT INTO `sys_role_menu` VALUES (5019, 2001, 3019);
INSERT INTO `sys_role_menu` VALUES (5020, 2001, 3020);
INSERT INTO `sys_role_menu` VALUES (5021, 2001, 3021);
INSERT INTO `sys_role_menu` VALUES (5022, 2001, 3022);
INSERT INTO `sys_role_menu` VALUES (5023, 2001, 3023);
INSERT INTO `sys_role_menu` VALUES (5024, 2001, 3024);
INSERT INTO `sys_role_menu` VALUES (5025, 2001, 3025);
INSERT INTO `sys_role_menu` VALUES (5026, 2001, 3026);
INSERT INTO `sys_role_menu` VALUES (5027, 2001, 3027);
INSERT INTO `sys_role_menu` VALUES (5028, 2001, 3028);
INSERT INTO `sys_role_menu` VALUES (5029, 2001, 3029);
INSERT INTO `sys_role_menu` VALUES (5030, 2001, 3030);
INSERT INTO `sys_role_menu` VALUES (5031, 2001, 3031);
INSERT INTO `sys_role_menu` VALUES (5032, 2001, 3032);
INSERT INTO `sys_role_menu` VALUES (5033, 2001, 3033);
INSERT INTO `sys_role_menu` VALUES (5034, 2001, 3034);
INSERT INTO `sys_role_menu` VALUES (5035, 2001, 3035);
INSERT INTO `sys_role_menu` VALUES (5036, 2001, 3036);
INSERT INTO `sys_role_menu` VALUES (5037, 2001, 3037);
INSERT INTO `sys_role_menu` VALUES (5038, 2002, 3001);
INSERT INTO `sys_role_menu` VALUES (5039, 2002, 3002);
INSERT INTO `sys_role_menu` VALUES (5040, 2002, 3003);
INSERT INTO `sys_role_menu` VALUES (5041, 2002, 3006);
INSERT INTO `sys_role_menu` VALUES (5042, 2002, 3013);
INSERT INTO `sys_role_menu` VALUES (5043, 2002, 3014);
INSERT INTO `sys_role_menu` VALUES (5044, 2002, 3015);
INSERT INTO `sys_role_menu` VALUES (5045, 2002, 3033);
INSERT INTO `sys_role_menu` VALUES (5046, 2002, 3037);
INSERT INTO `sys_role_menu` VALUES (5047, 2003, 3001);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '用户状态（0正常 1停用）',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1001, 'grace', '$2a$10$mb6mOv3liksjdFiENvRxv.3mRY/U.9CgMEwH4/JKecKDCprWOOivK', 0, '2023-11-05 14:25:21', '2023-11-05 14:25:23');
INSERT INTO `sys_user` VALUES (1002, 'test', '$2a$10$HebtQPbLFf3YrO6B1n8Sb.AWHAz8SZtAc48IFGm8iSXjZsym3GPii', 0, '2023-11-05 14:25:25', '2023-11-05 14:25:27');

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
INSERT INTO `sys_user_role` VALUES (4003, 1002, 2002);

SET FOREIGN_KEY_CHECKS = 1;
