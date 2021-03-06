DROP TABLE IF EXISTS t_online_person_material;
CREATE TABLE t_online_person_material (
  id            INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  person_id     INT(11)      NOT NULL DEFAULT 0
  COMMENT '申请人ID',
  batch_id      INT(11)      NOT NULL DEFAULT 0
  COMMENT '批次ID',
  material_id   INT(11)      NOT NULL DEFAULT 0
  COMMENT '材料ID',
  material_name VARCHAR(128) NOT NULL DEFAULT ''
  COMMENT '材料名称',
  material_uri  VARCHAR(1024)         DEFAULT ''
  COMMENT '材料uri',
  status        INT(11)      NOT NULL DEFAULT 0
  COMMENT '材料状态',
  c_time        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人线上材料信息表';


DROP TABLE IF EXISTS t_indicator_self_test_result;
CREATE TABLE t_indicator_self_test_result (
  id           INT(11) NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id     INT(11) NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id    INT(11) NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  indicator_id INT(11) NOT NULL  DEFAULT 0
  COMMENT '指标ID',
  item_id      INT(11)           DEFAULT 0
  COMMENT '用户选择指标选项ID',
  item_value   VARCHAR(64)       DEFAULT '用户选择选项文本',
  score_value  DECIMAL(8, 4)     DEFAULT 0
  COMMENT '得分',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '用户自测信息结果表';


DROP TABLE IF EXISTS t_indicator_self_test_record;
CREATE TABLE t_indicator_self_test_record (
  id          INT(11) NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id    INT(11) NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id   INT(11) NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  try_count   INT(11) NOT NULL  DEFAULT 0
  COMMENT '尝试次数',
  score_value DECIMAL(8, 4)     DEFAULT 0
  COMMENT '得分',

  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '用户自测得分';


DROP TABLE IF EXISTS t_pb_score_record;
CREATE TABLE t_pb_score_record (
  id                  INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  accept_number       VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '受理编号',
  batch_id            INT(11)     NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  indicator_id        INT(11)     NOT NULL  DEFAULT 0
  COMMENT '指标ID',
  indicator_name      VARCHAR(64)           DEFAULT ''
  COMMENT '指标名称',
  person_id           INT(11)     NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  person_name         VARCHAR(64) NOT NULL  DEFAULT ''
  COMMENT '申请人',
  person_id_num       VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '申请人身份证',
  person_mobile_phone VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '申请人手机号',
  company_id          INT(11)               DEFAULT 0
  COMMENT '企业ID',
  company_name        VARCHAR(128)          DEFAULT ''
  COMMENT '企业名称',
  status              INT(11)               DEFAULT 0
  COMMENT '办理进度',
  score_value         DECIMAL(8, 4)         DEFAULT 0
  COMMENT '分数',
  item_id             INT(11)               DEFAULT 0
  COMMENT '打分选项ID',
  accept_date         DATE                  DEFAULT NULL
  COMMENT '受理日期',
  submit_date         DATE                  DEFAULT NULL
  COMMENT '送达日期',
  score_date          DATE                  DEFAULT NULL
  COMMENT '打分日期',
  op_user_id          INT(11)               DEFAULT 0
  COMMENT '审核人id',
  op_user             VARCHAR(64)           DEFAULT ''
  COMMENT '审核人',
  op_role_id          INT(11)               DEFAULT 0
  COMMENT '审核部门id',
  op_role             VARCHAR(64)           DEFAULT ''
  COMMENT '审核部门',
  score_detail        VARCHAR(255)          DEFAULT ''
  COMMENT '打分说明',
  c_time              TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY k_batch(batch_id),
  KEY k_number(person_id_num),
  KEY k_company(company_id),
  KEY k_indicator(indicator_id),
  UNIQUE KEY (batch_id, person_id, indicator_id, op_role_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人打分信息记录表';

DROP TABLE IF EXISTS t_person_material_accept_record;
CREATE TABLE t_person_material_accept_record (
  id            INT(11)      NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id      INT(11)      NOT NULL  DEFAULT 0
  COMMENT '批次ID',
  person_id     INT(11)      NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  role_id       INT(11)      NOT NULL  DEFAULT 0
  COMMENT '部门ID',
  indicator_id  INT(11)      NOT NULL  DEFAULT 0
  COMMENT '指标ID',
  material_id   INT(11)      NOT NULL  DEFAULT 0
  COMMENT '材料ID',
  material_name VARCHAR(255) NOT NULL  DEFAULT ''
  COMMENT '材料名称',
  status        INT(11)                DEFAULT 0
  COMMENT '送达状态',
  c_time        TIMESTAMP    NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY (batch_id, person_id, role_id, indicator_id, material_id)
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


DROP TABLE IF EXISTS t_pb_score_result;
CREATE TABLE t_pb_score_result (
  id             INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  batch_id       INT(11)     NOT NULL  DEFAULT 0
  COMMENT '批次ID',
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
  score_value    DECIMAL(8, 4)         DEFAULT 0
  COMMENT '分数',
  score_detail   VARCHAR(255)          DEFAULT ''
  COMMENT '打分说明',
  c_time         TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY k_batch(batch_id),
  KEY k_indicator(indicator_id),
  UNIQUE KEY (batch_id, person_id, indicator_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人打分结果';


