CREATE TABLE if not exists `trace_logger`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '编码',
    `user_id`     varchar(255)       DEFAULT 'unknown' COMMENT '用户',
    `ip`          varchar(255)       DEFAULT NULL COMMENT '地址',
    `os`          varchar(255)       DEFAULT NULL COMMENT '系统',
    `browser`     varchar(255)       DEFAULT NULL COMMENT '浏览器',
    `user_agent`  text               DEFAULT NULL COMMENT '代理',
    `message`     text               DEFAULT NULL COMMENT '信息',
    `cost_millis` int(11)   NOT NULL DEFAULT '0' COMMENT '消耗时间',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '日志表';

CREATE TABLE if not exists `master_menu`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '编码',
    `parent_id`   int(11)      NOT NULL DEFAULT '0' COMMENT '父编码',
    `title`       varchar(255)          DEFAULT NULL COMMENT '标题',
    `address`     varchar(255)          DEFAULT NULL COMMENT '地址',
    `attr_id`     tinyint(1)   NOT NULL DEFAULT '1' COMMENT '属性',
    `icon_cls`    varchar(255)          DEFAULT NULL COMMENT '图标',
    `status_id`   tinyint(1)   NOT NULL DEFAULT '1' COMMENT '状态',
    `order_id`    int(11)      NOT NULL DEFAULT '0' COMMENT '排序',
    `create_user` varchar(255) NOT NULL DEFAULT 'unknown' COMMENT '创建用户',
    `update_user` varchar(255)          DEFAULT NULL COMMENT '修改用户',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '菜单表';

CREATE TABLE if not exists `master_operate`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '编码',
    `parent_id`   int(11)      NOT NULL DEFAULT '0' COMMENT '父编码',
    `title`       varchar(255)          DEFAULT NULL COMMENT '标题',
    `address`     varchar(255)          DEFAULT NULL COMMENT '地址',
    `method`      varchar(255)          DEFAULT NULL COMMENT '方法',
    `attr_id`     tinyint(1)   NOT NULL DEFAULT '1' COMMENT '属性',
    `icon_cls`    varchar(255)          DEFAULT NULL COMMENT '图标',
    `status_id`   tinyint(1)   NOT NULL DEFAULT '1' COMMENT '状态',
    `order_id`    int(11)      NOT NULL DEFAULT '0' COMMENT '排序',
    `create_user` varchar(255) NOT NULL DEFAULT 'unknown' COMMENT '创建用户',
    `update_user` varchar(255)          DEFAULT NULL COMMENT '修改用户',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '操作表';

CREATE TABLE if not exists `master_user`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '编码',
    `name`        varchar(255)          DEFAULT NULL COMMENT '姓名',
    `username`    varchar(255)          DEFAULT NULL COMMENT '用户名称',
    `password`    varchar(255)          DEFAULT NULL COMMENT '用户密码',
    `mobile`      varchar(255)          DEFAULT NULL COMMENT '手机号码',
    `email`       varchar(255)          DEFAULT NULL COMMENT '邮箱地址',
    `attr_id`     tinyint(1)   NOT NULL DEFAULT '1' COMMENT '属性',
    `status_id`   tinyint(1)   NOT NULL DEFAULT '1' COMMENT '状态',
    `login_ip`    varchar(255)          DEFAULT NULL COMMENT '登陆IP',
    `login_time`  timestamp    NULL     DEFAULT NULL COMMENT '登陆时间',
    `create_user` varchar(255) NOT NULL DEFAULT 'unknown' COMMENT '创建用户',
    `update_user` varchar(255)          DEFAULT NULL COMMENT '修改用户',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户表';

CREATE TABLE if not exists `master_group`
(
    `id`            int(11)      NOT NULL AUTO_INCREMENT COMMENT '编码',
    `group_name`    varchar(255)          DEFAULT NULL COMMENT '分组名称',
    `group_explain` varchar(255)          DEFAULT NULL COMMENT '分组说明',
    `status_id`     tinyint(1)   NOT NULL DEFAULT '1' COMMENT '分组状态',
    `create_user`   varchar(255) NOT NULL DEFAULT 'unknown' COMMENT '创建用户',
    `update_user`   varchar(255)          DEFAULT NULL COMMENT '修改用户',
    `create_time`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp    NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '分组表';

CREATE TABLE if not exists `master_user_group`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `user_id`  int(11) DEFAULT NULL COMMENT '用户编码',
    `group_id` int(11) DEFAULT NULL COMMENT '分组编码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '用户分组关系表';

CREATE TABLE if not exists `master_role`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '编码',
    `role_name`    varchar(255)          DEFAULT NULL COMMENT '角色名称',
    `role_explain` varchar(255)          DEFAULT NULL COMMENT '角色说明',
    `status_id`    tinyint(1)   NOT NULL DEFAULT '1' COMMENT '角色状态',
    `create_user`  varchar(255) NOT NULL DEFAULT 'unknown' COMMENT '创建用户',
    `update_user`  varchar(255)          DEFAULT NULL COMMENT '修改用户',
    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp    NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色表';


CREATE TABLE if not exists `master_group_role`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `group_id` int(11) DEFAULT NULL COMMENT '分组编码',
    `role_id`  int(11) DEFAULT NULL COMMENT '角色编码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '分组角色关系表';


CREATE TABLE if not exists `master_role_menu`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `role_id` int(11) DEFAULT NULL COMMENT '角色编码',
    `menu_id` int(11) DEFAULT NULL COMMENT '菜单编码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色菜单关系表';

CREATE TABLE if not exists `master_role_operate`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
    `role_id`    int(11) DEFAULT NULL COMMENT '角色编码',
    `operate_id` int(11) DEFAULT NULL COMMENT '操作编码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '角色操作关系表';

CREATE TABLE if not exists `chat_user`
(
    `id`       int(11) NOT NULL COMMENT '用户编码',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `status`   varchar(255) DEFAULT NULL COMMENT '状态',
    `sign`     varchar(255) DEFAULT NULL COMMENT '签名',
    `avatar`   varchar(255) DEFAULT NULL COMMENT '头像',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '即时通讯用户表';



