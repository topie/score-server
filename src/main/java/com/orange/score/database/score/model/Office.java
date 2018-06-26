package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "T_OFFICE")
public class Office {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_office.nextval from dual")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "REGION_LEVEL")
    private Integer regionLevel;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return PARENT_ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return REGION_ID
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     * @param regionId
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * @return REGION_LEVEL
     */
    public Integer getRegionLevel() {
        return regionLevel;
    }

    /**
     * @param regionLevel
     */
    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }
}
