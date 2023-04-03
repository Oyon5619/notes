drop
database if exists notes;
create
database notes;
use
notes;

create table t_user
(
    account   varchar(30) not null comment '�˺�',
    password  varchar(30) not null comment '����',
    username  varchar(30) not null comment '�û���',
    telephone varchar(30) not null comment '�ֻ�����',
    email     varchar(50) not null comment '����',
    user_groups    text comment '����',
    primary key (account)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
create index index_account on t_user (account ASC);

insert into t_user(account, password, username, telephone, email)
values ('zhangsan', 'zs123456', '����', '15915712354', 'zhangsan@163.com'),
       ('lisi', 'ls123456', '����', '13430241235', 'lisi@qq.com'),
       ('wangwu', 'ww123456', '����', '13645236589', 'wangwu@163.com'),
       ('chenliu', 'cl123456', '����', '13316397963', 'chenliu@163.com'),
       ('xuqi', 'xq123456', '����', '13352679568', 'xuqi@163.com'),
       ('maba', 'mb123456', '���', '13654879632', 'maba@163.com'),
       ('zhengjiu', 'zj123456', '֣��', '13912546983', 'zhengjiu@163.com'),
       ('huangshi', 'hs123456', '��ʮ', '15815632498', 'huangshi@163.com');

drop table if exists t_notes;
CREATE TABLE t_notes
(
    notes_id      int auto_increment comment '������',
    notes_title   varchar(50) not null comment '�������',
    promulgator   varchar(30) not null comment '�������˺�',
    category      varchar(10) not null comment '�����Ŀ',
    priority      int default 0 comment '���ȼ�',
    notes_content text comment '��������',
    notes_images  text comment '�����е�ͼƬ',
    notes_group   varchar(30) null comment '�������',
    deleted       boolean default false comment '�Ƿ�ɾ��',
    update_time   date comment '����ʱ��',
    PRIMARY KEY (notes_id),
    foreign key (promulgator) references t_user (account)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
create index index_notes_id on t_notes (notes_id ASC);

drop table if exists t_photo;
CREATE TABLE t_photo
(
    photo_id int auto_increment primary key ,
    photo   blob null
)
