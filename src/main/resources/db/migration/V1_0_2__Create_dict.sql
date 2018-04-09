DROP TABLE IF EXISTS d_dict;
CREATE TABLE d_dict (
  id         INT(11) NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  alias      VARCHAR(128)     DEFAULT ''
  COMMENT '字典别名英文',
  alias_name VARCHAR(128)     DEFAULT ''
  COMMENT '字典别名',
  text       VARCHAR(128)     DEFAULT ''
  COMMENT '文本',
  value      INT(11)          DEFAULT 0
  COMMENT '值',
  sort       INT(11)          DEFAULT 0
  COMMENT '排序值',
  PRIMARY KEY (id),
  KEY k_alias(alias)
)
  DEFAULT CHARSET = utf8
  COMMENT '字典表';
