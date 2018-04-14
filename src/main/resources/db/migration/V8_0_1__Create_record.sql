DROP TABLE IF EXISTS t_person_batch_accept_info;
CREATE TABLE t_person_batch_accept_info (
  id                INT(11)      NOT NULL  AUTO_INCREMENT
  COMMENT '主键ID',
  batch_id          INT(11)      NOT NULL  DEFAULT '0'
  COMMENT '批次ID',
  person_info_id    INT(11)      NOT NULL  DEFAULT '0'
  COMMENT '申请人身份信息id',
  accept_number     VARCHAR(64)  NOT NULL  DEFAULT ''
  COMMENT '受理编号',
  accept_address_id INT(11)      NOT NULL  DEFAULT '0'
  COMMENT '受理地点1、市级行政许可中心，2、滨海新区行政服务中心',
  accept_address    VARCHAR(128) NOT NULL  DEFAULT ''
  COMMENT '受理地点',
  reservaion_date   INT(11)      NOT NULL  DEFAULT '0'
  COMMENT '预约日期',
  reservaion_m      INT(11)      NOT NULL  DEFAULT '0'
  COMMENT '上午，下午',
  c_time            TIMESTAMP    NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='本期受理人员信息表';


DROP TABLE IF EXISTS t_person_batch_score_record;
CREATE TABLE t_person_batch_score_record (
  id             INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  accept_number  VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '受理编号',
  indicator_id   INT(11)     NOT NULL  DEFAULT 0
  COMMENT '指标ID',
  indicator_name VARCHAR(64)           DEFAULT ''
  COMMENT '指标名称',
  person_id      INT(11)     NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  person_name    VARCHAR(64) NOT NULL  DEFAULT ''
  COMMENT '申请人',
  person_id_num  VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '申请人身份证',
  company_id     INT(11)               DEFAULT 0
  COMMENT '企业ID',
  commany_name   VARCHAR(128)          DEFAULT ''
  COMMENT '企业名称',
  status         INT(11)               DEFAULT 0
  COMMENT '办理进度',
  score_value    DECIMAL(8, 4)         DEFAULT 0
  COMMENT '分数',
  item_id        INT(11)               DEFAULT 0
  COMMENT '打分选项ID',
  accept_date    DATE                  DEFAULT '0000-00-00'
  COMMENT '受理日期',
  submit_date    DATE                  DEFAULT '0000-00-00'
  COMMENT '送达日期',
  score_date     DATE                  DEFAULT '0000-00-00'
  COMMENT '打分日期',
  op_user_id     INT(11)               DEFAULT 0
  COMMENT '审核人id',
  op_user        VARCHAR(64)           DEFAULT ''
  COMMENT '审核人',
  op_role_id     INT(11)               DEFAULT 0
  COMMENT '审核部门id',
  op_role        VARCHAR(64)           DEFAULT ''
  COMMENT '审核部门',
  score_detail   VARCHAR(255)          DEFAULT ''
  COMMENT '打分说明',
  c_time         TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人打分信息表';

DROP TABLE IF EXISTS t_person_material_accept_record;
CREATE TABLE t_person_material_accept_record (
  id            INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  batch_id      INT(11)      NOT NULL DEFAULT 0
  COMMENT '批次ID',
  person_id     INT(11)      NOT NULL DEFAULT 0
  COMMENT '申请人ID',
  role_id       INT(11)      NOT NULL DEFAULT 0
  COMMENT '部门ID',
  material_id   INT(11)      NOT NULL DEFAULT 0
  COMMENT '材料ID',
  material_name VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '材料名称',
  status        INT(11)               DEFAULT 0
  COMMENT '送达状态',
  c_time        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY (batch_id, person_id, role_id, material_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人材料送达记录表';


DROP TABLE IF EXISTS t_indicator_json;
CREATE TABLE t_indicator_json (
  id          INT(11)   NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  batch_id    INT(11)   NOT NULL DEFAULT 0
  COMMENT '批次ID',
  json        TEXT               DEFAULT NULL
  COMMENT '指标json',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  add_user    VARCHAR(64)        DEFAULT ''
  COMMENT '创建用户',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '指标Json信息表';
