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
