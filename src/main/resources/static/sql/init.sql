
DROP DATABASE IF EXISTS `notes`;

CREATE DATABASE `notes`;

USE notes;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `account`     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
    `password`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `username`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `telephone`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
    `user_groups` text CHARACTER SET utf8 COLLATE utf8_general_ci        NULL COMMENT '分组',
    PRIMARY KEY (`account`) USING BTREE,
    INDEX `index_account` (`account`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user`
VALUES ('chenliu', 'cl123456', '陈六', '13316397963', 'chenliu@163.com', NULL);
INSERT INTO `t_user`
VALUES ('huangshi', 'hs123456', '黄十', '15815632498', 'huangshi@163.com', NULL);
INSERT INTO `t_user`
VALUES ('lisi', 'ls123456', '李四', '13430241235', 'lisi@qq.com', '语文#数学#英语#eee');
INSERT INTO `t_user`
VALUES ('maba', 'mb123456', '马八', '13654879632', 'maba@163.com', NULL);
INSERT INTO `t_user`
VALUES ('wangwu', 'ww123456', '王五', '13645236589', 'wangwu@163.com', NULL);
INSERT INTO `t_user`
VALUES ('xuqi', 'xq123456', '许七', '13352679568', 'xuqi@163.com', NULL);
INSERT INTO `t_user`
VALUES ('zhangsan', 'zs123456', '张三', '15915712354', 'zhangsan@163.com', NULL);
INSERT INTO `t_user`
VALUES ('zhengjiu', 'zj123456', '郑九', '13912546983', 'zhengjiu@163.com', NULL);

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_notes`;
CREATE TABLE `t_notes`
(
    `notes_id`    int(11)                                                NOT NULL AUTO_INCREMENT COMMENT '错题编号',
    `notes_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '错题标题',
    `promulgator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布者账号',
    `priority`    varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '优先级',
    `subject`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '错题内容',
    `answer`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '标准答案',
    `respond`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '我的答案',
    `summary`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '错题内容',
    `notes_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错题分组',
    `deleted`     tinyint(1)                                             NULL DEFAULT 0 COMMENT '是否删除',
    `update_time` datetime                                               NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`notes_id`) USING BTREE,
    INDEX `promulgator` (`promulgator`) USING BTREE,
    INDEX `index_notes_id` (`notes_id`) USING BTREE,
    CONSTRAINT `t_notes_ibfk_1` FOREIGN KEY (`promulgator`) REFERENCES `t_user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 29
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `t_notes`
VALUES (22, 'english', 'lisi', '3', '<h1></h1><p data-we-empty-p=\"\" id=\"3gfuy\"><img src=\"http://localhost:8080/imgFile/8d6de24c-3fbf-451a-a9e2-caf86d9ba95f.jpg\" style=\"max-width:100%;\" contenteditable=\"false\" width=\"138\" height=\"138\"/></p><h1 id=\"x33w1\">YURI</h1><p>777</p>', '<p>bbb</p>', '<p>aaa</p>', '<p>ccc<br/></p>', '英语', 0,
        '2023-04-21 02:22:19');
INSERT INTO `t_notes`
VALUES (23, '数学错题x1', 'lisi', '2', '<p>10+20=?</p>', '<p><b>30</b><br/></p>', '<p>28</p>', '<p>?<img src=\"http://localhost:8080/imgFile/20215923-0a05-4edf-a9d0-ac90d7e457c3.jpg\" contenteditable=\"false\" style=\"max-width: 100%;\" width=\"71\" height=\"96.27\"/></p><br/>', '数学', 0, '2023-04-22 15:59:01');
INSERT INTO `t_notes`
VALUES (24, '语文错题x1', 'lisi', '2', '<p>dsdsdsdd<br/></p>', '<p>3e3edfefe</p>', '<p>ddddd</p>', '<p>343r3rr</p>', '语文', 0, '2023-05-08 10:28:43');
INSERT INTO `t_notes`
VALUES (25, '物理试题x', 'lisi', '2', '<p><b>dsdsdsd</b><br/></p>', '<p><b>eee</b><br/></p>', '<p>EEEEEE</p>', '<p>EEE<br/></p>', '物理', 0, '2023-05-17 17:24:14');
INSERT INTO `t_notes`
VALUES (26, '生物错题x1', 'lisi', '3', '<p><b>ssssss</b><br/></p>', '<p><i>xxxxx</i><br/></p>', '<p><b>sssssssssssssssssssssssssssssss</b><br/></p>', '<p>sddfgvfgerd</p>', '生物', 0, '2023-05-18 15:56:28');
INSERT INTO `t_notes`
VALUES (27, '地理错题', 'lisi', '2', '<p>dldldldlldldldld</p>', '<p>fff</p>', '<p>dddd</p>', '<p>ggg</p>', '地理', 0, '2023-05-18 16:32:08');
INSERT INTO `t_notes`
VALUES (28, '历史错题x1', 'lisi', '1', '<p>的士大夫豆腐干hghgh</p>', '<p>gfgfg65</p>', '<p>hghghghg</p>', '<p>ghgh</p>', '历史', 0, '2023-05-18 16:34:44');

DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo`
(
    `photo_id`     int(11)                                                NOT NULL AUTO_INCREMENT,
    `photo_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `photo_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `photo_type`   varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`photo_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE t_review
(
    review_id   int auto_increment comment '复习计划id' primary key,
    promulgator varchar(30)                           comment '发布者账号',
    title       varchar(64) comment '复习计划标题',
    notes       text comment '选中的错题',
    cycle       varchar(64)                          not null comment '复习周期',
    cron        varchar(64)                          not null comment 'cron表达式',
    deleted     tinyint(1) default 0                 null comment '是否删除',
    update_time datetime   default CURRENT_TIMESTAMP null comment '更新时间',
    foreign key (promulgator) references t_user (account)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;
