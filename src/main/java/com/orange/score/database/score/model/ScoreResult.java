package com.orange.score.database.score.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_person_batch_score_result")
public class ScoreResult {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_person_batch_score_result_seq.nextval from dual")
    private Integer id;

    /**
     * 批次ID
     */
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

    /**
     * 分数
     */
    @Column(name = "score_value")
    private BigDecimal scoreValue;

    /**
     * 打分说明
     */
    @Column(name = "score_detail")
    private String scoreDetail;

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
}
