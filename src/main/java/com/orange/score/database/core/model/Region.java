package com.orange.score.database.core.model;

import javax.persistence.*;

@Table(name = "t_region")
public class Region {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_region_seq.nextval from dual")
    private Integer id;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 上级地区ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 等级, 1、省；2、市；3、区
     */
    @Column(name = "region_level")
    private Integer level;


    /**
     * 2018年12月29日
     * 添加行政区划代码，区划代码来自公安局提供
     * 在数据库中只保存了“省市县”3级中的第3级别
     */
    @Column(name = "police_code")
    private Integer police_code;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取地区名称
     *
     * @return name - 地区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置地区名称
     *
     * @param name 地区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上级地区ID
     *
     * @return parent_id - 上级地区ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级地区ID
     *
     * @param parentId 上级地区ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取等级, 1、省；2、市；3、区
     *
     * @return level - 等级, 1、省；2、市；3、区
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置等级, 1、省；2、市；3、区
     *
     * @param level 等级, 1、省；2、市；3、区
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPolice_code() {
        return police_code;
    }

    public void setPolice_code(Integer police_code) {
        this.police_code = police_code;
    }
}
