package com.orange.score.database.core.model;

import com.orange.score.common.utils.Sortable;

import javax.persistence.*;

@Table(name = "d_profile_field")
public class ProfileField extends Sortable {

    private static final long serialVersionUID = -1237122005830689098L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select d_profile_field_seq.nextval from dual")
    private Integer id;

    /**
     * 字段名称
     */
    @Column(name = "field_name")
    private String fieldName;

    private String detail;

    /**
     * 字段值
     */
    @Column(name = "field_value")
    private String fieldValue;

    /**
     * 字段类型 : INT,VARCHAR,TIMESTAMP
     */
    @Column(name = "field_type")
    private String fieldType;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取字段名称
     *
     * @return field_name - 字段名称
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设置字段名称
     *
     * @param fieldName 字段名称
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 获取字段值
     *
     * @return field_value - 字段值
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * 设置字段值
     *
     * @param fieldValue 字段值
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * 获取字段类型 : INT,VARCHAR,TIMESTAMP
     *
     * @return field_type - 字段类型 : INT,VARCHAR,TIMESTAMP
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * 设置字段类型 : INT,VARCHAR,TIMESTAMP
     *
     * @param fieldType 字段类型 : INT,VARCHAR,TIMESTAMP
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
