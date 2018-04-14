DROP TABLE IF EXISTS d_example;
CREATE TABLE d_example (
  id         INT(11)   NOT NULL    AUTO_INCREMENT
  COMMENT 'ID:hidden',
  o_text     VARCHAR(64)           DEFAULT ''
  COMMENT '文本:text',
  o_hidden   VARCHAR(64)           DEFAULT ''
  COMMENT '文本:hidden',
  o_date     TIMESTAMP
  COMMENT '日期:date',
  o_datetime TIMESTAMP NULL        DEFAULT CURRENT_TIMESTAMP
  COMMENT '时间:datetime',
  o_image    VARCHAR(255)          DEFAULT ''
  COMMENT '图片:image',
  o_file     VARCHAR(255)          DEFAULT ''
  COMMENT '附件:file',
  o_tree     INT(11)   NOT NULL    DEFAULT 0
  COMMENT '树:tree',
  o_select   INT(11)               DEFAULT 0
  COMMENT '选择:select:[选择1#1,选择2#2,选择3#3]',
  o_radio    INT(11)               DEFAULT 0
  COMMENT '单选:radioGroup:[选择1#1,选择2#2,选择3#3]',
  o_checkbox VARCHAR(255)          DEFAULT ''
  COMMENT '多选:checkboxGroup:[选择1#1,选择2#2,选择3#3]',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '示例表';

INSERT INTO d_function VALUES ('7', '2', '示例列表', '1', '1', NULL, '/api/core/example/list', '4', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '7');
