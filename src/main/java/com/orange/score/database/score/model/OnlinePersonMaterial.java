package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_online_person_material")
public class OnlinePersonMaterial {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_online_person_material_seq.nextval from dual")
    private Integer id;

    /**
     * 申请人ID
     */
    @Column(name = "person_id")
    private Integer personId;

    /**
     * 批次ID
     */
    @Column(name = "batch_id")
    private Integer batchId;

    /**
     * 材料ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "material_info_id")
    private Integer materialInfoId;

    @Transient
    private String materialInfoName;

    /**
     * 材料名称
     */
    @Column(name = "material_name")
    private String materialName;

    /**
     * 材料uri
     */
    @Column(name = "material_uri")
    private String materialUri;

    /**
     * 材料状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    public String getMaterialInfoName() {
        return materialInfoName;
    }

    public void setMaterialInfoName(String materialInfoName) {
        this.materialInfoName = materialInfoName;
    }

    public Integer getMaterialInfoId() {
        return materialInfoId;
    }

    public void setMaterialInfoId(Integer materialInfoId) {
        this.materialInfoId = materialInfoId;
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
     * 获取申请人ID
     *
     * @return person_id - 申请人ID
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * 设置申请人ID
     *
     * @param personId 申请人ID
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * 获取批次ID
     *
     * @return batch_id - 批次ID
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * 设置批次ID
     *
     * @param batchId 批次ID
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * 获取材料ID
     *
     * @return material_id - 材料ID
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * 设置材料ID
     *
     * @param materialId 材料ID
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取材料名称
     *
     * @return material_name - 材料名称
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 设置材料名称
     *
     * @param materialName 材料名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * 获取材料uri
     *
     * @return material_uri - 材料uri
     */
    public String getMaterialUri() {
        return materialUri;
    }

    /**
     * 设置材料uri
     *
     * @param materialUri 材料uri
     */
    public void setMaterialUri(String materialUri) {
        this.materialUri = materialUri;
    }

    /**
     * 获取材料状态
     *
     * @return status - 材料状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置材料状态
     *
     * @param status 材料状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return c_time - 创建时间
     */
    public Date getcTime() {
        return cTime;
    }

    /**
     * 设置创建时间
     *
     * @param cTime 创建时间
     */
    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }
}
