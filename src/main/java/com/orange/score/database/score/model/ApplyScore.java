package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "t_apply_score")
public class ApplyScore {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_apply_score_seq.nextval from dual")
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
     * 申请人身份证号
     */
    @Column(name = "person_id_number")
    private String personIdNumber;

    /**
     * 指标ID
     */
    @Column(name = "indicator_id")
    private Integer indicatorId;

    /**
     * 指标名称
     */
    @Column(name = "indicator_name")
    private String indicatorName;

    /**
     * 申请原因
     */
    @Column(name = "apply_reason")
    private String applyReason;

    /**
     * 申请人
     */
    @Column(name = "apply_user")
    private String applyUser;

    /**
     * 申请人
     */
    @Column(name = "apply_user_id")
    private Integer applyUserId;

    /**
     * 申请人部门ID
     */
    @Column(name = "apply_role_id")
    private Integer applyRoleId;

    @Column(name = "apply_user_type")
    private Integer applyUserType;

    /**
     * 申请人部门
     */
    @Column(name = "apply_role")
    private String applyRole;

    /**
     * 申请状态
     */
    @Column(name = "approve_status")
    private Integer approveStatus;

    @Column(name = "approve_content")
    private String approveContent;

    /**
     * 审核人
     */
    @Column(name = "approve_user")
    private String approveUser;

    public Integer getApplyUserType() {
        return applyUserType;
    }

    public void setApplyUserType(Integer applyUserType) {
        this.applyUserType = applyUserType;
    }

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
     * 获取申请人身份证号
     *
     * @return person_id_number - 申请人身份证号
     */
    public String getPersonIdNumber() {
        return personIdNumber;
    }

    /**
     * 设置申请人身份证号
     *
     * @param personIdNumber 申请人身份证号
     */
    public void setPersonIdNumber(String personIdNumber) {
        this.personIdNumber = personIdNumber;
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
     * 获取指标名称
     *
     * @return indicator_name - 指标名称
     */
    public String getIndicatorName() {
        return indicatorName;
    }

    /**
     * 设置指标名称
     *
     * @param indicatorName 指标名称
     */
    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    /**
     * 获取申请原因
     *
     * @return apply_reason - 申请原因
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 设置申请原因
     *
     * @param applyReason 申请原因
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    /**
     * 获取申请人
     *
     * @return apply_user - 申请人
     */
    public String getApplyUser() {
        return applyUser;
    }

    /**
     * 设置申请人
     *
     * @param applyUser 申请人
     */
    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Integer getApplyRoleId() {
        return applyRoleId;
    }

    public void setApplyRoleId(Integer applyRoleId) {
        this.applyRoleId = applyRoleId;
    }

    /**
     * 获取申请人部门
     *
     * @return apply_role - 申请人部门
     */
    public String getApplyRole() {
        return applyRole;
    }

    /**
     * 设置申请人部门
     *
     * @param applyRole 申请人部门
     */
    public void setApplyRole(String applyRole) {
        this.applyRole = applyRole;
    }

    /**
     * 获取申请状态
     *
     * @return approve_status - 申请状态
     */
    public Integer getApproveStatus() {
        return approveStatus;
    }

    /**
     * 设置申请状态
     *
     * @param approveStatus 申请状态
     */
    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    /**
     * @return approve_content
     */
    public String getApproveContent() {
        return approveContent;
    }

    /**
     * @param approveContent
     */
    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    /**
     * 获取审核人
     *
     * @return approve_user - 审核人
     */
    public String getApproveUser() {
        return approveUser;
    }

    /**
     * 设置审核人
     *
     * @param approveUser 审核人
     */
    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }
}
