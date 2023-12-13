CREATE SCHEMA  IF NOT EXISTS `auth_security`;
USE `auth_security`;

DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户编号',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
    `contact_user_id` bigint NULL DEFAULT NULL COMMENT '联系人的用户编号',
    `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
    `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
    `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
    `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
    `package_id` bigint NOT NULL COMMENT '租户套餐编号',
    `expire_time` datetime NOT NULL COMMENT '过期时间',
    `account_count` int NOT NULL COMMENT '账号数量',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表';

BEGIN;
INSERT INTO `system_tenant` (`id`, `name`, `contact_user_id`, `contact_name`, `contact_mobile`, `status`, `domain`, `package_id`, `expire_time`, `account_count`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES
    (1, '系统租户', NULL, '', '13711112222', 0, 'https://www.aaa.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2022-02-23 12:15:11', b'0'),
    (121, '客户A', 110, '小王2', '15601691300', 0, 'http://www.iocoder.cn', 111, '2024-03-11 00:00:00', 20, '1', '2022-02-22 00:56:14', '1', '2022-05-17 10:03:59', b'0');
COMMIT;

DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
    `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父部门id',
    `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `leader_user_id` bigint NULL DEFAULT NULL COMMENT '负责人',
    `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
    `status` tinyint NOT NULL COMMENT '部门状态（0正常 1停用）',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表';

BEGIN;
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES
    (100, '研发部门', 0, 0, 1, '13711112222', 'kwseeker@gmail.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-06-19 00:29:10', b'0', 1);
COMMIT;

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
    `post_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位编号数组',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
    `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
    `sex` tinyint NULL DEFAULT 0 COMMENT '用户性别',
    `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
    `status` tinyint NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
    `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_username`(`username` ASC, `update_time` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表';

BEGIN;
INSERT INTO `system_users` (`id`, `username`, `password`, `nickname`, `remark`, `dept_id`, `post_ids`, `email`, `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES
    (1, 'admin', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'admin', '管理员', 100, '[1]', 'admin@gmail.com', '13723336666', 1, '', 0, '127.0.0.1', '2023-07-24 08:41:23', 'admin', '2021-01-05 17:03:47', NULL, '2023-07-24 08:41:23', b'0', 1),
    (100, 'kwseeker', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'kwseeker', '管理员', 100, '[1]', 'kwseeker@gmail.com', '13711112222', 1, '', 0, '127.0.0.1', '2023-07-24 08:41:23', 'admin', '2021-01-05 17:03:47', NULL, '2023-07-24 08:41:23', b'0', 1);
COMMIT;

DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
    `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
    `sort` int NOT NULL COMMENT '显示顺序',
    `data_scope` tinyint NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
    `status` tinyint NOT NULL COMMENT '角色状态（0正常 1停用）',
    `type` tinyint NOT NULL COMMENT '角色类型',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表';

BEGIN;
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`)
VALUES
    (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0', 1),
    (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0', 1),
    (101, '测试角色', 'test', 0, 1, '[]', 0, 2, '测试角色', '', '2021-01-06 13:49:35', '1', '2022-09-25 12:09:38', b'0', 1),
    (201, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
COMMIT;

DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表';

DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2873 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表';

DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
    `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
    `type` tinyint NOT NULL COMMENT '菜单类型',
    `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
    `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
    `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名',
    `status` tinyint NOT NULL DEFAULT 0 COMMENT '菜单状态',
    `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
    `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
    `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2303 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表';
