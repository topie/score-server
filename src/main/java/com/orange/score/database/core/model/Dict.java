package com.orange.score.database.core.model;

import javax.persistence.*;

@Table(name = "d_dict")
public class Dict {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 字典别名英文
     */
    private String alias;

    /**
     * 字典别名
     */
    @Column(name = "alias_name")
    private String aliasName;

    /**
     * 文本
     */
    private String text;

    /**
     * 值
     */
    private Integer value;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取字典别名英文
     *
     * @return alias - 字典别名英文
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置字典别名英文
     *
     * @param alias 字典别名英文
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取字典别名
     *
     * @return alias_name - 字典别名
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * 设置字典别名
     *
     * @param aliasName 字典别名
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * 获取文本
     *
     * @return text - 文本
     */
    public String getText() {
        return text;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 获取排序值
     *
     * @return sort - 排序值
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序值
     *
     * @param sort 排序值
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}