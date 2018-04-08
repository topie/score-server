DROP TABLE IF EXISTS d_column_json;
CREATE TABLE d_column_json (
  id          INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT 'ID:hidden',
  table_name  VARCHAR(128) NULL     DEFAULT ''
  COMMENT '表名称',
  type        INT(11)      NOT NULL DEFAULT 0
  COMMENT '类型:select:[json#0,info#1]',
  column_json TEXT         NULL
  COMMENT 'json:code',
  column_info TEXT         NULL
  COMMENT 'info:code',
  search_conf TEXT NULL
  COMMENT '搜索配置:code',
  PRIMARY KEY (id),
  UNIQUE KEY (table_name)
)
  DEFAULT CHARSET = utf8
  COMMENT '表列信息';
