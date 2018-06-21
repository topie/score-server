package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_pb_status_record")
public class PersonBatchStatusRecord {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_pb_status_record_seq.nextval from dual")
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

    @Column(name = "person_id_number")
    private String personIdNumber;

    /**
     * 状态字典alias
     */
    @Column(name = "status_dict_alias")
    private String statusDictAlias;

    @Column(name = "status_type_desc")
    private String statusTypeDesc;

    /**
     * 状态值
     */
    @Column(name = "status_int")
    private Integer statusInt;

    /**
     * 状态文本
     */
    @Column(name = "status_str")
    private String statusStr;

    /**
     * 状态生成时间
     */
    @Column(name = "status_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusTime;

    /**
     * 状态原因
     */
    @Column(name = "status_reason")
    private String statusReason;

    public String getStatusTypeDesc() {
        return statusTypeDesc;
    }

    public void setStatusTypeDesc(String statusTypeDesc) {
        this.statusTypeDesc = statusTypeDesc;
    }

    public String getPersonIdNumber() {
        return personIdNumber;
    }

    public void setPersonIdNumber(String personIdNumber) {
        this.personIdNumber = personIdNumber;
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
     * 获取状态字典alias
     *
     * @return status_dict_alias - 状态字典alias
     */
    public String getStatusDictAlias() {
        return statusDictAlias;
    }

    /**
     * 设置状态字典alias
     *
     * @param statusDictAlias 状态字典alias
     */
    public void setStatusDictAlias(String statusDictAlias) {
        this.statusDictAlias = statusDictAlias;
    }

    /**
     * 获取状态值
     *
     * @return status_int - 状态值
     */
    public Integer getStatusInt() {
        return statusInt;
    }

    /**
     * 设置状态值
     *
     * @param statusInt 状态值
     */
    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    /**
     * 获取状态文本
     *
     * @return status_str - 状态文本
     */
    public String getStatusStr() {
        return statusStr;
    }

    /**
     * 设置状态文本
     *
     * @param statusStr 状态文本
     */
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    /**
     * 获取状态生成时间
     *
     * @return status_time - 状态生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getStatusTime() {
        return statusTime;
    }

    /**
     * 设置状态生成时间
     *
     * @param statusTime 状态生成时间
     */
    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    /**
     * 获取状态原因
     *
     * @return status_reason - 状态原因
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * 设置状态原因
     *
     * @param statusReason 状态原因
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
