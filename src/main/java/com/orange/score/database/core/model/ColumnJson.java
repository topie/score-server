package com.orange.score.database.core.model;

import javax.persistence.*;

@Table(name = "d_column_json")
public class ColumnJson {
    /**
     * ID:hidden
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select seq_id.nextval from dual")
    private Integer id;

    /**
     * 表名称
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 类型:select:[json#0,info#1]
     */
    private Integer type;

    /**
     * column的json数据:code
     */
    @Column(name = "column_json")
    private String columnJson;

    /**
     * column的表信息:code
     */
    @Column(name = "column_info")
    private String columnInfo;

    /**
     * 搜索配置:code
     */
    @Column(name = "search_conf")
    private String searchConf;

    /**
     * 获取ID:hidden
     *
     * @return id - ID:hidden
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID:hidden
     *
     * @param id ID:hidden
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取表名称
     *
     * @return table_name - 表名称
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名称
     *
     * @param tableName 表名称
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
     * 获取column的json数据:code
     *
     * @return column_json - column的json数据:code
     */
    public String getColumnJson() {
        return columnJson;
    }

    /**
     * 设置column的json数据:code
     *
     * @param columnJson column的json数据:code
     */
    public void setColumnJson(String columnJson) {
        this.columnJson = columnJson;
    }

    /**
     * 获取column的表信息:code
     *
     * @return column_info - column的表信息:code
     */
    public String getColumnInfo() {
        return columnInfo;
    }

    /**
     * 设置column的表信息:code
     *
     * @param columnInfo column的表信息:code
     */
    public void setColumnInfo(String columnInfo) {
        this.columnInfo = columnInfo;
    }

    /**
     * 获取搜索配置:code
     *
     * @return search_conf - 搜索配置:code
     */
    public String getSearchConf() {
        return searchConf;
    }

    /**
     * 设置搜索配置:code
     *
     * @param searchConf 搜索配置:code
     */
    public void setSearchConf(String searchConf) {
        this.searchConf = searchConf;
    }
}
