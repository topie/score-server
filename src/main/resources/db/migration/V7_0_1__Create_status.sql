DROP TABLE IF EXISTS t_person_batch_status_info;
CREATE TABLE t_person_batch_status_info (
  id                     INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  person_id              INT(11)     NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  person_name            VARCHAR(64) NOT NULL  DEFAULT ''
  COMMENT '申请人',
  person_id_num          VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '申请人身份证',
  company_id             INT(11)               DEFAULT 0
  COMMENT '企业ID',
  commany_name           VARCHAR(128)          DEFAULT ''
  COMMENT '企业名称',
  batch_id               INT(11)     NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  reservation_status     INT(11)               DEFAULT 0
  COMMENT '申请预约状态',
  hall_status            INT(11)               DEFAULT 0
  COMMENT '预约大厅状态',
  union_approve_status_1 INT(11)               DEFAULT 0
  COMMENT '公安预审状态',
  union_approve_status_2 INT(11)               DEFAULT 0
  COMMENT '人社预审状态',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='申请人批次状态';

DROP TABLE IF EXISTS t_person_batch_status_record;
CREATE TABLE t_person_batch_status_record (
  id              INT(11)   NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id        INT(11)   NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id       INT(11)   NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  status_type     INT(11)             DEFAULT 0
  COMMENT '状态类型',
  status_type_str VARCHAR(64)         DEFAULT ''
  COMMENT '状态类型描述',
  status_int      INT(11)             DEFAULT 0
  COMMENT '状态值',
  status_str      VARCHAR(64)         DEFAULT ''
  COMMENT '状态文本',
  status_time     TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '状态生成时间',
  status_reason   VARCHAR(255)        DEFAULT ''
  COMMENT '状态原因',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='申请人批次状态记录';
