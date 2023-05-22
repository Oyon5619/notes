
DROP DATABASE IF EXISTS `notes`;

CREATE DATABASE `notes`;

USE notes;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `account`     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '�˺�',
    `password`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '����',
    `username`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '�û���',
    `telephone`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '�ֻ�����',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '����',
    `user_groups` text CHARACTER SET utf8 COLLATE utf8_general_ci        NULL COMMENT '����',
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
VALUES ('chenliu', 'cl123456', '����', '13316397963', 'chenliu@163.com', NULL);
INSERT INTO `t_user`
VALUES ('huangshi', 'hs123456', '��ʮ', '15815632498', 'huangshi@163.com', NULL);
INSERT INTO `t_user`
VALUES ('lisi', 'ls123456', '����', '13430241235', 'lisi@qq.com', '����#��ѧ#Ӣ��#eee');
INSERT INTO `t_user`
VALUES ('maba', 'mb123456', '���', '13654879632', 'maba@163.com', NULL);
INSERT INTO `t_user`
VALUES ('wangwu', 'ww123456', '����', '13645236589', 'wangwu@163.com', NULL);
INSERT INTO `t_user`
VALUES ('xuqi', 'xq123456', '����', '13352679568', 'xuqi@163.com', NULL);
INSERT INTO `t_user`
VALUES ('zhangsan', 'zs123456', '����', '15915712354', 'zhangsan@163.com', NULL);
INSERT INTO `t_user`
VALUES ('zhengjiu', 'zj123456', '֣��', '13912546983', 'zhengjiu@163.com', NULL);

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_notes`;
CREATE TABLE `t_notes`
(
    `notes_id`    int(11)                                                NOT NULL AUTO_INCREMENT COMMENT '������',
    `notes_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '�������',
    `promulgator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '�������˺�',
    `priority`    varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '���ȼ�',
    `subject`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '��������',
    `answer`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '��׼��',
    `respond`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '�ҵĴ�',
    `summary`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL COMMENT '��������',
    `notes_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '�������',
    `deleted`     tinyint(1)                                             NULL DEFAULT 0 COMMENT '�Ƿ�ɾ��',
    `update_time` datetime                                               NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
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
VALUES (22, 'english', 'lisi', '3', '<h1></h1><p data-we-empty-p=\"\" id=\"3gfuy\"><img src=\"http://localhost:8080/imgFile/8d6de24c-3fbf-451a-a9e2-caf86d9ba95f.jpg\" style=\"max-width:100%;\" contenteditable=\"false\" width=\"138\" height=\"138\"/></p><h1 id=\"x33w1\">YURI</h1><p>777</p>', '<p>bbb</p>', '<p>aaa</p>', '<p>ccc<br/></p>', 'Ӣ��', 0,
        '2023-04-21 02:22:19');
INSERT INTO `t_notes`
VALUES (23, '��ѧ����x1', 'lisi', '2', '<p>10+20=?</p>', '<p><b>30</b><br/></p>', '<p>28</p>', '<p>?<img src=\"http://localhost:8080/imgFile/20215923-0a05-4edf-a9d0-ac90d7e457c3.jpg\" contenteditable=\"false\" style=\"max-width: 100%;\" width=\"71\" height=\"96.27\"/></p><br/>', '��ѧ', 0, '2023-04-22 15:59:01');
INSERT INTO `t_notes`
VALUES (24, '���Ĵ���x1', 'lisi', '2', '<p>dsdsdsdd<br/></p>', '<p>3e3edfefe</p>', '<p>ddddd</p>', '<p>343r3rr</p>', '����', 0, '2023-05-08 10:28:43');
INSERT INTO `t_notes`
VALUES (25, '��������x', 'lisi', '2', '<p><b>dsdsdsd</b><br/></p>', '<p><b>eee</b><br/></p>', '<p>EEEEEE</p>', '<p>EEE<br/></p>', '����', 0, '2023-05-17 17:24:14');
INSERT INTO `t_notes`
VALUES (26, '�������x1', 'lisi', '3', '<p><b>ssssss</b><br/></p>', '<p><i>xxxxx</i><br/></p>', '<p><b>sssssssssssssssssssssssssssssss</b><br/></p>', '<p>sddfgvfgerd</p>', '����', 0, '2023-05-18 15:56:28');
INSERT INTO `t_notes`
VALUES (27, '�������', 'lisi', '2', '<p>dldldldlldldldld</p>', '<p>fff</p>', '<p>dddd</p>', '<p>ggg</p>', '����', 0, '2023-05-18 16:32:08');
INSERT INTO `t_notes`
VALUES (28, '��ʷ����x1', 'lisi', '1', '<p>��ʿ��򶹸���hghgh</p>', '<p>gfgfg65</p>', '<p>hghghghg</p>', '<p>ghgh</p>', '��ʷ', 0, '2023-05-18 16:34:44');

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
    review_id   int auto_increment comment '��ϰ�ƻ�id' primary key,
    promulgator varchar(30)                           comment '�������˺�',
    title       varchar(64) comment '��ϰ�ƻ�����',
    notes       text comment 'ѡ�еĴ���',
    cycle       varchar(64)                          not null comment '��ϰ����',
    cron        varchar(64)                          not null comment 'cron���ʽ',
    deleted     tinyint(1) default 0                 null comment '�Ƿ�ɾ��',
    update_time datetime   default CURRENT_TIMESTAMP null comment '����ʱ��',
    foreign key (promulgator) references t_user (account)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;
