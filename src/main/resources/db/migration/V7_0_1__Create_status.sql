DROP TABLE IF EXISTS t_person_batch_status_record;
CREATE TABLE t_person_batch_status_record (
  id                INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id          INT(11)     NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id         INT(11)     NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  person_id_number  VARCHAR(64) NOT NULL  DEFAULT ''
  COMMENT '申请人身份证号码',
  status_dict_alias VARCHAR(64)           DEFAULT ''
  COMMENT '状态字典alias',
  status_type_desc  VARCHAR(64)           DEFAULT ''
  COMMENT '状态类型',
  status_int        INT(11)               DEFAULT 0
  COMMENT '状态值',
  status_str        VARCHAR(64)           DEFAULT ''
  COMMENT '状态文本',
  status_time       TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '状态生成时间',
  status_reason     VARCHAR(255)          DEFAULT ''
  COMMENT '状态原因',
  PRIMARY KEY (id),
  KEY k_bp(batch_id, person_id_number),
  UNIQUE KEY (batch_id, person_id, status_dict_alias, status_int)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='申请人批次状态记录';

DROP TABLE IF EXISTS t_sms_send_config;
CREATE TABLE t_sms_send_config (
  id                INT(11) NOT NULL  AUTO_INCREMENT
  COMMENT 'ID:hidden',
  status_dict_alias VARCHAR(64)       DEFAULT ''
  COMMENT '状态字典alias:text',
  status_int        INT(11)           DEFAULT 0
  COMMENT '状态值:text',
  template_str      VARCHAR(255)      DEFAULT ''
  COMMENT '短信模板内容:textarea',
  PRIMARY KEY (id),
  UNIQUE KEY (status_dict_alias, status_int)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='短信发送设置';
