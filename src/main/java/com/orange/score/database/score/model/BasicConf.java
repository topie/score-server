package com.orange.score.database.score.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "t_basic_conf")
public class BasicConf {
    /**
     * id:hidden
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_basic_conf_seq.nextval from dual")
    private Integer id;

    /**
     * 自测合格分数
     */
    @Column(name = "self_test_score_line")
    private BigDecimal selfTestScoreLine;

    /**
     * 自测评次数限制
     */
    @Column(name = "self_test_limit")
    private Integer selfTestLimit;

    /**
     * 预约限制次数
     */
    @Column(name = "appointment_limit")
    private Integer appointmentLimit;

    /**
     * 经办人信息修改次数限制
     */
    @Column(name = "company_edit_limit")
    private Integer companyEditLimit;

    /**
     * 发布天数
     */
    @Column(name = "publish_day")
    private Integer publishDay;

    /**
     * 公示天数
     */
    @Column(name = "notice_day")
    private Integer noticeDay;

    /**
     * 获取id:hidden
     *
     * @return id - id:hidden
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id:hidden
     *
     * @param id id:hidden
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取自测合格分数
     *
     * @return self_test_score_line - 自测合格分数
     */
    public BigDecimal getSelfTestScoreLine() {
        return selfTestScoreLine;
    }

    /**
     * 设置自测合格分数
     *
     * @param selfTestScoreLine 自测合格分数
     */
    public void setSelfTestScoreLine(BigDecimal selfTestScoreLine) {
        this.selfTestScoreLine = selfTestScoreLine;
    }

    /**
     * 获取自测评次数限制
     *
     * @return self_test_limit - 自测评次数限制
     */
    public Integer getSelfTestLimit() {
        return selfTestLimit;
    }

    /**
     * 设置自测评次数限制
     *
     * @param selfTestLimit 自测评次数限制
     */
    public void setSelfTestLimit(Integer selfTestLimit) {
        this.selfTestLimit = selfTestLimit;
    }

    /**
     * 获取预约限制次数
     *
     * @return appointment_limit - 预约限制次数
     */
    public Integer getAppointmentLimit() {
        return appointmentLimit;
    }

    /**
     * 设置预约限制次数
     *
     * @param appointmentLimit 预约限制次数
     */
    public void setAppointmentLimit(Integer appointmentLimit) {
        this.appointmentLimit = appointmentLimit;
    }

    /**
     * 获取经办人信息修改次数限制
     *
     * @return company_edit_limit - 经办人信息修改次数限制
     */
    public Integer getCompanyEditLimit() {
        return companyEditLimit;
    }

    /**
     * 设置经办人信息修改次数限制
     *
     * @param companyEditLimit 经办人信息修改次数限制
     */
    public void setCompanyEditLimit(Integer companyEditLimit) {
        this.companyEditLimit = companyEditLimit;
    }

    /**
     * 获取发布天数
     *
     * @return publish_day - 发布天数
     */
    public Integer getPublishDay() {
        return publishDay;
    }

    /**
     * 设置发布天数
     *
     * @param publishDay 发布天数
     */
    public void setPublishDay(Integer publishDay) {
        this.publishDay = publishDay;
    }

    /**
     * 获取公示天数
     *
     * @return notice_day - 公示天数
     */
    public Integer getNoticeDay() {
        return noticeDay;
    }

    /**
     * 设置公示天数
     *
     * @param noticeDay 公示天数
     */
    public void setNoticeDay(Integer noticeDay) {
        this.noticeDay = noticeDay;
    }
}
