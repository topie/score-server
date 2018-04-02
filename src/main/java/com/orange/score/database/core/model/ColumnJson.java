package com.orange.score.database.core.model;

import javax.persistence.*;

@Table(name = "d_column_json")
public class ColumnJson {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_name")
    private String tableName;

    /**
     * 类型:select:[json#0,info#1]
     */
    private Integer type;

    /**
     * column的json数据:textarea
     */
    @Column(name = "column_json")
    private String columnJson;

    /**
     * column的表信息:textarea
     */
    @Column(name = "column_info")
    private String columnInfo;

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
     * @return table_name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取类型:select:[json#0,info#1]
     *
     * @return type - 类型:select:[json#0,info#1]
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型:select:[json#0,info#1]
     *
     * @param type 类型:select:[json#0,info#1]
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取column的json数据:textarea
     *
     * @return column_json - column的json数据:textarea
     */
    public String getColumnJson() {
        return columnJson;
    }

    /**
     * 设置column的json数据:textarea
     *
     * @param columnJson column的json数据:textarea
     */
    public void setColumnJson(String columnJson) {
        this.columnJson = columnJson;
    }

    /**
     * 获取column的表信息:textarea
     *
     * @return column_info - column的表信息:textarea
     */
    public String getColumnInfo() {
        return columnInfo;
    }

    /**
     * 设置column的表信息:textarea
     *
     * @param columnInfo column的表信息:textarea
     */
    public void setColumnInfo(String columnInfo) {
        this.columnInfo = columnInfo;
    }
}