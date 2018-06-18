package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_accept_date_conf")
public class AcceptDateConf {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_accept_date_conf_seq.nextval from dual")
    private Integer id;

    /**
     * 批次ID
     */
    @Column(name = "batch_id")
    private Integer batchId;

    /**
     * 受理日期
     */
    @Column(name = "accept_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptDate;

    /**
     * 周几
     */
    @Column(name = "week_day")
    private String weekDay;

    @Column(name = "address_id")
    private Integer addressId;

    /**
     * 上午发放人数
     */
    @Column(name = "am_user_count")
    private Integer amUserCount;

    /**
     * 下午发放人数
     */
    @Column(name = "pm_user_count")
    private Integer pmUserCount;

    /**
     * 上午剩余人数
     */
    @Column(name = "am_remaining_count")
    private Integer amRemainingCount;

    /**
     * 下午剩余人数
     */
    @Column(name = "pm_remaining_count")
    private Integer pmRemainingCount;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
     * 获取周几
     *
     * @return week_day - 周几
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * 设置周几
     *
     * @param weekDay 周几
     */
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    /**
     * 获取上午发放人数
     *
     * @return am_user_count - 上午发放人数
     */
    public Integer getAmUserCount() {
        return amUserCount;
    }

    /**
     * 设置上午发放人数
     *
     * @param amUserCount 上午发放人数
     */
    public void setAmUserCount(Integer amUserCount) {
        this.amUserCount = amUserCount;
    }

    /**
     * 获取下午发放人数
     *
     * @return pm_user_count - 下午发放人数
     */
    public Integer getPmUserCount() {
        return pmUserCount;
    }

    /**
     * 设置下午发放人数
     *
     * @param pmUserCount 下午发放人数
     */
    public void setPmUserCount(Integer pmUserCount) {
        this.pmUserCount = pmUserCount;
    }

    /**
     * 获取上午剩余人数
     *
     * @return am_remaining_count - 上午剩余人数
     */
    public Integer getAmRemainingCount() {
        return amRemainingCount;
    }

    /**
     * 设置上午剩余人数
     *
     * @param amRemainingCount 上午剩余人数
     */
    public void setAmRemainingCount(Integer amRemainingCount) {
        this.amRemainingCount = amRemainingCount;
    }

    /**
     * 获取下午剩余人数
     *
     * @return pm_remaining_count - 下午剩余人数
     */
    public Integer getPmRemainingCount() {
        return pmRemainingCount;
    }

    /**
     * 设置下午剩余人数
     *
     * @param pmRemainingCount 下午剩余人数
     */
    public void setPmRemainingCount(Integer pmRemainingCount) {
        this.pmRemainingCount = pmRemainingCount;
    }
}
