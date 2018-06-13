package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_accept_record")
public class MaterialAcceptRecord {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select seq_id.nextval from dual")
    private Integer id;

    /**
     * 批次ID
     */
    @Column(name = "batch_id")
    private Integer batchId;

    /**
     * 申请人ID
     */
    @Column(name = "person_id")
    private Integer personId;

    /**
     * 部门ID
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 指标ID
     */
    @Column(name = "indicator_id")
    private Integer indicatorId;

    /**
     * 材料ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    /**
     * 材料名称
     */
    @Column(name = "material_name")
    private String materialName;

    /**
     * 送达状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

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
     * 获取部门ID
     *
     * @return role_id - 部门ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置部门ID
     *
     * @param roleId 部门ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取指标ID
     *
     * @return indicator_id - 指标ID
     */
    public Integer getIndicatorId() {
        return indicatorId;
    }

    /**
     * 设置指标ID
     *
     * @param indicatorId 指标ID
     */
    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
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
     * 获取送达状态
     *
     * @return status - 送达状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置送达状态
     *
     * @param status 送达状态
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
