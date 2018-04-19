DROP TABLE IF EXISTS t_common_question;
CREATE TABLE t_common_question (
  `id`       INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `question` VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '常见问题',
  `answer`   TEXT         NOT NULL
  COMMENT '问题解答',
  `c_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='常见问题表';

DROP TABLE IF EXISTS t_system_notice;
CREATE TABLE t_system_notice (
  `id`      INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `title`   VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '标题',
  `content` MEDIUMTEXT   NOT NULL
  COMMENT '内容',
  `type`    INT(11)      NOT NULL DEFAULT '0'
  COMMENT '类型，1、相关政策，2、办理指南；2、重要通知',
  `author`  VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '发文机关或作者',
  `c_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='相关政策、重要通知、办理指南表';
