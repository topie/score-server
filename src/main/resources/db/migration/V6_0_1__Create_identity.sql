DROP TABLE IF EXISTS t_company_info;
CREATE TABLE t_company_info (
  id               INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  user_name        VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '用户名',
  password         VARCHAR(128) NOT NULL DEFAULT ''
  COMMENT '用户密码',
  company_name     VARCHAR(128) NOT NULL DEFAULT ''
  COMMENT '企业名称',
  company_type     INT(11)      NOT NULL DEFAULT 0
  COMMENT '单位类型，1、企事业单位；2、个体工商户',
  society_code     VARCHAR(128) NOT NULL DEFAULT ''
  COMMENT '统一社会信用代码',
  company_mobile   VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '单位联系电话',
  operator         VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '经办人姓名',
  operator_mobile  VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '经办人联系手机',
  operator_address VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '经办人联系地址',
  remark           TEXT         NOT NULL
  COMMENT '备注说明',
  create_time      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  add_user         VARCHAR(64)           DEFAULT ''
  COMMENT '创建用户',
  status           INT(11)               DEFAULT 0
  COMMENT '状态',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='企业信息表';

-- 积分申请人身份信息 --
DROP TABLE IF EXISTS t_identity_info;
CREATE TABLE t_identity_info (
  id                     INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  id_number              VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '身份证号',
  batch_id               INT(11)      NOT NULL DEFAULT 0
  COMMENT '批次ID',
  company_id             INT(11)      NOT NULL DEFAULT 0
  COMMENT '单位ID',
  id_card_positive       VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '身份证正面图片',
  id_card_opposite       VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '身份证反面图片',
  name                   VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '姓名',
  sex                    INT(11)      NOT NULL DEFAULT '0'
  COMMENT '性别：1、男；2、女',
  birthday               VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '出生日期',
  age                    INT(11)      NOT NULL DEFAULT '0'
  COMMENT '年龄',
  nation                 VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '民族',
  region                 INT(11)      NOT NULL DEFAULT '0'
  COMMENT '拟落户地区',
  reservation_status     INT(11)               DEFAULT 0
  COMMENT '申请预约状态',
  hall_status            INT(11)               DEFAULT 0
  COMMENT '预约大厅状态',
  union_approve_status_1 INT(11)               DEFAULT 0
  COMMENT '公安预审状态',
  union_approve_status_2 INT(11)               DEFAULT 0
  COMMENT '人社预审状态',
  police_approve_status  INT(11)               DEFAULT 0
  COMMENT '公安前置预审状态',
  renshe_accept_status   INT(11)               DEFAULT 0
  COMMENT '人社受理状态',
  cancel_status          INT(11)               DEFAULT 0
  COMMENT '资格取消状态',
  result_status          INT(11)               DEFAULT 0
  COMMENT '核算状态',
  accept_number          VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '受理编号',
  accept_address_id      INT(11)      NOT NULL DEFAULT '0'
  COMMENT '受理地点1、市级行政许可中心，2、滨海新区行政服务中心',
  accept_address         VARCHAR(128) NOT NULL DEFAULT ''
  COMMENT '受理地点',
  reservaion_date        INT(11)      NOT NULL DEFAULT '0'
  COMMENT '预约日期',
  reservaion_m           INT(11)      NOT NULL DEFAULT '0'
  COMMENT '上午，下午',
  c_time                 TIMESTAMP    NULL
  COMMENT '创建时间',
  u_time                 TIMESTAMP    NULL
  COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_id_number (batch_id, id_number)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='积分申请人身份信息';

-- 户籍迁移信息 --
DROP TABLE IF EXISTS t_house_move;
CREATE TABLE t_house_move (
  id                     INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  identity_info_id       INT(11)      NOT NULL DEFAULT '0'
  COMMENT '申请人身份信息id',
  move_province          INT(11)      NOT NULL DEFAULT '0'
  COMMENT '迁出地区（省）',
  move_city              INT(11)      NOT NULL DEFAULT '0'
  COMMENT '迁出地区（市）',
  move_region            INT(11)      NOT NULL DEFAULT '0'
  COMMENT '迁出地区（区）',
  move_address           VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '迁出地地址',
  move_registered_office VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '现户籍登记机关',
  house_nature           INT(11)      NOT NULL DEFAULT '0'
  COMMENT '现户口性质',
  settled_nature         INT(11)      NOT NULL DEFAULT '0'
  COMMENT '落户性质',
  registered_office      VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '迁入户籍登记机关',
  address                VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '迁入地详细地址',
  witness                VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '证明人',
  witness_phone          VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '证明人电话',
  witness_address        VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '证明人收件地址',
  region                 INT(11)      NOT NULL DEFAULT '0'
  COMMENT '拟落户地区',
  marriage_status        INT(11)      NOT NULL DEFAULT '0'
  COMMENT '婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚',
  have_son               INT(11)      NOT NULL DEFAULT '0'
  COMMENT '有无子女，1、有；2、无',
  son_number             INT(11)      NOT NULL DEFAULT '0'
  COMMENT '子女数量',
  c_time                 TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_identity_info_id (identity_info_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='户籍迁移信息';

-- 申请人家庭关系 --
DROP TABLE IF EXISTS t_house_relationship;
CREATE TABLE t_house_relationship (
  id               INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  identity_info_id INT(11)      NOT NULL DEFAULT '0'
  COMMENT '申请人身份信息id',
  relationship     VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '与本人关系',
  name             VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '姓名',
  id_number        VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '身份证号',
  is_remove        INT(11)      NOT NULL DEFAULT '0'
  COMMENT '是否随迁，1、是；2、否',
  culture_degree   VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '文化程度',
  c_time           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='申请人家庭关系';

-- 申请人其他信息 --
DROP TABLE IF EXISTS t_house_other;
CREATE TABLE t_house_other (
  id                  INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  identity_info_id    INT(11)      NOT NULL DEFAULT '0'
  COMMENT '申请人身份信息id',
  applicant_type      INT(11)      NOT NULL DEFAULT '0'
  COMMENT '申请人类型，1、企业员工；2、投资者；3、个体工商户',
  political_status    INT(11)      NOT NULL DEFAULT '0'
  COMMENT '政治面貌，1、中共党员；2、中共预备党员；3、共青团员',
  soldier_meritorious INT(11)      NOT NULL DEFAULT '0'
  COMMENT '军人立功，0、无；1、一等功；2、二等功；3、三等功',
  culture_degree      INT(11)      NOT NULL DEFAULT '0'
  COMMENT '文化程度',
  degree              INT(11)      NOT NULL DEFAULT '0'
  COMMENT '学位',
  profession          VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '专业',
  company_name        VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '单位名称',
  company_address     VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '单位地址',
  company_phone       VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '单位电话',
  self_phone          VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '本人电话',
  application_date    VARCHAR(16)  NOT NULL DEFAULT ''
  COMMENT '居住证申领日期',
  social_security_pay TINYINT(1)   NOT NULL DEFAULT '0'
  COMMENT '是否缴纳社保, 1、是；2、否',
  provident_fund      TINYINT(1)   NOT NULL DEFAULT '0'
  COMMENT '是否参加住房公积金, 1、是；2、否',
  taxes               TINYINT(1)   NOT NULL DEFAULT '0'
  COMMENT '纳税情况, 1、是；2、否',
  detention           TINYINT(1)   NOT NULL DEFAULT '0'
  COMMENT '拘留情况, 1、是；2、否',
  penalty             TINYINT(1)   NOT NULL DEFAULT '0'
  COMMENT '获刑情况, 1、是；2、否',
  awards_title        INT(11)      NOT NULL DEFAULT '0'
  COMMENT '奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇',
  c_time              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='申请人其他信息';

-- 职业资格证书 --
DROP TABLE IF EXISTS t_house_profession;
CREATE TABLE t_house_profession (
  id                INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  identity_info_id  INT(11)      NOT NULL DEFAULT '0'
  COMMENT '申请人身份信息id',
  profession_type   INT(11)      NOT NULL DEFAULT '0'
  COMMENT '职业资格项，1、无；2、具有职称；3、具有职业资格',
  job_title_level   INT(11)      NOT NULL DEFAULT '0'
  COMMENT '职称级别，1、初级职称；2、中级职称；3、高级职称',
  job_position      VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '职位',
  issuing_authority VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '发证机关',
  issuing_date      VARCHAR(32)  NOT NULL DEFAULT ''
  COMMENT '发证日期',
  certificate_code  VARCHAR(255) NOT NULL DEFAULT ''
  COMMENT '证书编号',
  job_level         INT(11)      NOT NULL DEFAULT '0'
  COMMENT '职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工',
  job_type          INT(11)      NOT NULL DEFAULT '0'
  COMMENT '工种',
  c_time            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='职业资格证书';


DROP TABLE IF EXISTS t_region;
CREATE TABLE t_region (
  id        INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  name      VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '地区名称',
  parent_id INT(11)      NOT NULL DEFAULT '0'
  COMMENT '上级地区ID',
  level     INT(11)      NOT NULL DEFAULT '0'
  COMMENT '等级, 1、省；2、市；3、区',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT ='地区信息表';


