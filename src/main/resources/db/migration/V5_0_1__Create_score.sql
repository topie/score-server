DROP TABLE IF EXISTS t_company_info;
CREATE TABLE t_company_info (
  id     INT(11)   NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  c_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '企业信息表';

DROP TABLE IF EXISTS t_person_info;
CREATE TABLE t_person_info (
  id     INT(11)   NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  c_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人信息表';

DROP TABLE IF EXISTS t_person_accept_info;
CREATE TABLE t_person_accept_info (
  id            INT(11)     NOT NULL  AUTO_INCREMENT
  COMMENT 'id',
  person_id     INT(11)     NOT NULL  DEFAULT 0
  COMMENT '申请人ID',
  accept_number VARCHAR(32) NOT NULL  DEFAULT ''
  COMMENT '受理编号',
  status        INT(11)     NOT NULL  DEFAULT 0
  COMMENT '受理状态',
  accept_date   DATE                  DEFAULT '0000-00-00'
  COMMENT '受理日期',
  c_time        TIMESTAMP   NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY (accept_number)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请受理信息表';


DROP TABLE IF EXISTS t_material_info;
CREATE TABLE t_material_info (
  id     INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  name   VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '材料名称',
  c_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '材料信息表';


DROP TABLE IF EXISTS t_indicator;
CREATE TABLE t_indicator (
  id        INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  category  VARCHAR(16)  NOT NULL DEFAULT ''
  COMMENT '类别',
  index_num INT(11)      NOT NULL DEFAULT 0
  COMMENT '序号',
  name      VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '指标',
  note      VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '备注',
  item_type INT(11)      NOT NULL DEFAULT 0
  COMMENT '选项类型  0：单选题，1：填空题',
  c_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '指标信息表';

DROP TABLE IF EXISTS t_relate_indicator_material;
CREATE TABLE t_relate_indicator_material (
  indicator_id INT(11) NOT NULL DEFAULT 0
  COMMENT '指标ID',
  material_id  INT(11) NOT NULL DEFAULT 0
  COMMENT '材料ID',
  PRIMARY KEY (indicator_id, material_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '指标材料关联表';

DROP TABLE IF EXISTS t_indicator_item;
CREATE TABLE t_indicator_item (
  id             INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  indicator_id   INT(11)      NOT NULL DEFAULT 0
  COMMENT '指标信息ID',
  content        VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '选项内容',
  score          INT(11)               DEFAULT 0
  COMMENT '分数',
  score_function VARCHAR(255)          DEFAULT ''
  COMMENT '分数计算',
  c_time         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '指标信息选项表';

DROP TABLE IF EXISTS t_relate_indicator_role;
CREATE TABLE t_relate_indicator_role (
  indicator_id INT(11) NOT NULL DEFAULT 0
  COMMENT '指标ID',
  role_id      INT(11) NOT NULL DEFAULT 0
  COMMENT '角色ID',
  PRIMARY KEY (indicator_id, role_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '指标角色关联表';


DROP TABLE IF EXISTS t_person_score_info;
CREATE TABLE t_person_score_info (
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
  score          INT(11)               DEFAULT 0
  COMMENT '打分',
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

DROP TABLE IF EXISTS t_person_material_accept;
CREATE TABLE t_person_material_accept (
  id     INT(11)   NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  c_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '申请人材料送达信息表';

DROP TABLE IF EXISTS t_online_person_material;
CREATE TABLE t_online_person_material (
  id            INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  person_id     INT(11)      NOT NULL DEFAULT 0
  COMMENT '申请人ID',
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







