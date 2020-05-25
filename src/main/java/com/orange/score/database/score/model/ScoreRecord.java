package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_pb_score_record")
public class ScoreRecord {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_pb_score_record_seq.nextval from dual")
    private Integer id;

    /**
     * 受理编号
     */
    @Column(name = "accept_number")
    private String acceptNumber;

    @Column(name = "batch_id")
    private Integer batchId;

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
     * 申请人ID
     */
    @Column(name = "person_id")
    private Integer personId;

    /**
     * 申请人
     */
    @Column(name = "person_name")
    private String personName;

    /**
     * 申请人身份证
     */
    @Column(name = "person_id_num")
    private String personIdNum;

    @Column(name = "person_mobile_phone")
    private String personMobilePhone;

    /**
     * 企业ID
     */
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 企业名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 办理进度
     */
    private Integer status;

    /**
     * 分数
     */
    @Column(name = "score_value")
    private BigDecimal scoreValue;

    /**
     * 打分选项ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 受理日期
     */
    @Column(name = "accept_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptDate;

    /**
     * 送达日期
     */
    @Column(name = "submit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date submitDate;

    /**
     * 打分日期
     */
    @Column(name = "score_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date scoreDate;

    /**
     * 审核人id
     */
    @Column(name = "op_user_id")
    private Integer opUserId;

    /**
     * 审核人
     */
    @Column(name = "op_user")
    private String opUser;

    /**
     * 审核部门id
     */
    @Column(name = "op_role_id")
    private Integer opRoleId;

    /**
     * 审核部门
     */
    @Column(name = "op_role")
    private String opRole;

    /**
     * 打分说明
     */
    @Column(name = "score_detail")
    private String scoreDetail;

    @Column(name = "accept_address_id")
    private Integer acceptAddressId;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 原始分数，用来保存提供虚假材料的申请人没被扣分前的得分
     */
    @Column(name = "ORIGINAL_SCORE_VALUE")
    private BigDecimal originalScoreValue;

    /**
     * 是否已经扣分，1：已经扣过
     */
    @Column(name = "ISDEDUCTED")
    private String isDeducted;

    @Column(name = "toreviewreason")
    private  String toreviewreason;// 申请人申请复核的理由

    @Column(name = "toreviewtime")
    private  Date toreviewtime;//申请人申请复核的时间

    @Column(name = "idreviewend")
    private Integer idreviewend;// 申请复核是否完毕，1：结束

    public String getToreviewreason() {
        return toreviewreason;
    }

    public void setToreviewreason(String toreviewreason) {
        this.toreviewreason = toreviewreason;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getToreviewtime() {
        return toreviewtime;
    }

    public void setToreviewtime(Date toreviewtime) {
        this.toreviewtime = toreviewtime;
    }

    public Integer getIdreviewend() {
        return idreviewend;
    }

    public void setIdreviewend(Integer idreviewend) {
        this.idreviewend = idreviewend;
    }

    @Transient
    private Integer edit;

    public Integer getAcceptAddressId() {
        return acceptAddressId;
    }

    public void setAcceptAddressId(Integer acceptAddressId) {
        this.acceptAddressId = acceptAddressId;
    }

    public Integer getEdit() {
        return edit;
    }

    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    public String getPersonMobilePhone() {
        return personMobilePhone;
    }

    public void setPersonMobilePhone(String personMobilePhone) {
        this.personMobilePhone = personMobilePhone;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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
     * 获取受理编号
     *
     * @return accept_number - 受理编号
     */
    public String getAcceptNumber() {
        return acceptNumber;
    }

    /**
     * 设置受理编号
     *
     * @param acceptNumber 受理编号
     */
    public void setAcceptNumber(String acceptNumber) {
        this.acceptNumber = acceptNumber;
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
     * 获取申请人
     *
     * @return person_name - 申请人
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * 设置申请人
     *
     * @param personName 申请人
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * 获取申请人身份证
     *
     * @return person_id_num - 申请人身份证
     */
    public String getPersonIdNum() {
        return personIdNum;
    }

    /**
     * 设置申请人身份证
     *
     * @param personIdNum 申请人身份证
     */
    public void setPersonIdNum(String personIdNum) {
        this.personIdNum = personIdNum;
    }

    /**
     * 获取企业ID
     *
     * @return company_id - 企业ID
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置企业ID
     *
     * @param companyId 企业ID
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取企业名称
     *
     * @return company_name - 企业名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置企业名称
     *
     * @param companyName 企业名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取办理进度
     *
     * @return status - 办理进度
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置办理进度
     *
     * @param status 办理进度
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取分数
     *
     * @return score_value - 分数
     */
    public BigDecimal getScoreValue() {
        return scoreValue;
    }

    /**
     * 设置分数
     *
     * @param scoreValue 分数
     */
    public void setScoreValue(BigDecimal scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * 获取打分选项ID
     *
     * @return item_id - 打分选项ID
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * 设置打分选项ID
     *
     * @param itemId 打分选项ID
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取受理日期
     *
     * @return accept_date - 受理日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getAcceptDate() {
        return acceptDate;
    }

    /**
     * 设置受理日期
     *
     * @param acceptDate 受理日期
     */
    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    /**
     * 获取送达日期
     *
     * @return submit_date - 送达日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * 设置送达日期
     *
     * @param submitDate 送达日期
     */
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * 获取打分日期
     *
     * @return score_date - 打分日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getScoreDate() {
        return scoreDate;
    }

    /**
     * 设置打分日期
     *
     * @param scoreDate 打分日期
     */
    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    /**
     * 获取审核人id
     *
     * @return op_user_id - 审核人id
     */
    public Integer getOpUserId() {
        return opUserId;
    }

    /**
     * 设置审核人id
     *
     * @param opUserId 审核人id
     */
    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    /**
     * 获取审核人
     *
     * @return op_user - 审核人
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * 设置审核人
     *
     * @param opUser 审核人
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    /**
     * 获取审核部门id
     *
     * @return op_role_id - 审核部门id
     */
    public Integer getOpRoleId() {
        return opRoleId;
    }

    /**
     * 设置审核部门id
     *
     * @param opRoleId 审核部门id
     */
    public void setOpRoleId(Integer opRoleId) {
        this.opRoleId = opRoleId;
    }

    /**
     * 获取审核部门
     *
     * @return op_role - 审核部门
     */
    public String getOpRole() {
        return opRole;
    }

    /**
     * 设置审核部门
     *
     * @param opRole 审核部门
     */
    public void setOpRole(String opRole) {
        this.opRole = opRole;
    }

    /**
     * 获取打分说明
     *
     * @return score_detail - 打分说明
     */
    public String getScoreDetail() {
        return scoreDetail;
    }

    /**
     * 设置打分说明
     *
     * @param scoreDetail 打分说明
     */
    public void setScoreDetail(String scoreDetail) {
        this.scoreDetail = scoreDetail;
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

    public BigDecimal getOriginalScoreValue() {
        return originalScoreValue;
    }

    public void setOriginalScoreValue(BigDecimal originalScoreValue) {
        this.originalScoreValue = originalScoreValue;
    }

    public String getIsDeducted() {
        return isDeducted;
    }

    public void setIsDeducted(String isDeducted) {
        this.isDeducted = isDeducted;
    }
}
