DROP TABLE IF EXISTS t_apply_cancel;
CREATE TABLE t_apply_cancel (
  id               INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  batch_id         INT(11) NOT NULL DEFAULT 0
  COMMENT '批次ID',
  person_id        INT(11) NOT NULL DEFAULT 0
  COMMENT '办理人ID',
  person_id_number VARCHAR(64)      DEFAULT ''
  COMMENT '办理人身份证号',
  apply_reason     VARCHAR(1024)    DEFAULT ''
  COMMENT '申请原因',
  apply_user_id    INT(11)          DEFAULT 0
  COMMENT '申请人ID',
  apply_user       VARCHAR(128)     DEFAULT ''
  COMMENT '申请人',
  apply_role_id    INT(11)          DEFAULT 0
  COMMENT '申请人部门ID',
  apply_role       VARCHAR(128)     DEFAULT ''
  COMMENT '申请人部门',
  approve_status   INT(11)          DEFAULT 0
  COMMENT '申请状态',
  approve_content  VARCHAR(1024)    DEFAULT '审核内容',
  approve_user     VARCHAR(128)     DEFAULT ''
  COMMENT '审核人',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='取消资格申请';

DROP TABLE IF EXISTS t_apply_score;
CREATE TABLE t_apply_score (
  id               INT(11) NOT NULL  AUTO_INCREMENT
  COMMENT '主键ID',
  batch_id         INT(11) NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id        INT(11) NOT NULL  DEFAULT 0
  COMMENT '办理人ID',
  person_id_number VARCHAR(64)       DEFAULT ''
  COMMENT '办理人身份证号',
  indicator_id     INT(11) NOT NULL  DEFAULT 0
  COMMENT '指标ID',
  indicator_name   VARCHAR(64)       DEFAULT ''
  COMMENT '指标名称',
  apply_reason     VARCHAR(1024)     DEFAULT ''
  COMMENT '申请原因',
  apply_user_id    INT(11)           DEFAULT 0
  COMMENT '申请人ID',
  apply_user       VARCHAR(128)      DEFAULT ''
  COMMENT '申请人',
  apply_role_id    INT(11)           DEFAULT 0
  COMMENT '申请人部门ID',
  apply_role       VARCHAR(128)      DEFAULT ''
  COMMENT '申请人部门',
  approve_status   INT(11)           DEFAULT 0
  COMMENT '申请状态',
  approve_content  VARCHAR(1024)     DEFAULT ''
  COMMENT '审核内容',
  approve_user     VARCHAR(128)      DEFAULT ''
  COMMENT '审核人',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='重新打分申请';
