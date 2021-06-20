

SET FOREIGN_KEY_CHECKS=0;


CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '登录账号',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `user_profile` varchar(255) DEFAULT NULL COMMENT '管理员头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#反馈
CREATE TABLE `feedback` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#群聊
DROP TABLE IF EXISTS `group_msg_content`;
CREATE TABLE `group_msg_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息内容编号',
  `from_id` int(11) DEFAULT NULL COMMENT '发送者的编号',
  `from_name` varchar(20) DEFAULT NULL COMMENT '发送者的昵称',
  `from_profile` varchar(255) DEFAULT NULL COMMENT '发送者的头像',
  `create_time` datetime DEFAULT NULL COMMENT '消息发送时间',
  `content` text COMMENT '消息内容',
  `message_type_id` int(11) DEFAULT NULL COMMENT '消息类型编号',
  PRIMARY KEY (`id`),
  KEY `group_ibfk_1` (`from_id`),
  KEY `group_ibfk_2` (`message_type_id`),
  CONSTRAINT `group_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`),
  CONSTRAINT `group_ibfk_2` FOREIGN KEY (`message_type_id`) REFERENCES `message_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;


#消息类型
DROP TABLE IF EXISTS `message_type`;
CREATE TABLE `message_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息类型编号',
  `name` varchar(20) DEFAULT NULL COMMENT '消息类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '登录账号',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `user_profile` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `user_state_id` int(11) DEFAULT '2' COMMENT '用户状态id',
  `is_enabled` tinyint(1) DEFAULT '1' COMMENT '是否可用',
  `is_locked` tinyint(1) DEFAULT '0' COMMENT '是否被锁定',
  PRIMARY KEY (`id`),
  KEY `user_ibfk_1` (`user_state_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_state_id`) REFERENCES `user_state` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

#用户状态
DROP TABLE IF EXISTS `user_state`;
CREATE TABLE `user_state` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '状态名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#用户关系
DROP TABLE IF EXISTS relation;
CREATE TABLE relation (
    user_id varchar(20) NOT NULL ,
    friend_id varchar(20) NOT NULL
);


DROP TABLE IF EXISTS dynamics;
CREATE TABLE dynamics (
    user_id varchar(20)  NOT NULL ,
    nickname varchar(20) NOT NULL ,
    content text ,
    create_time DATE,
    user_profile varchar(255)
);

DROP TABLE IF EXISTS adduser;
CREATE TABLE adduser(
    from_id varchar(20) NOT NULL ,
    to_id varchar(20) NOT NULL
)



