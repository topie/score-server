package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_batch_conf")
public class BatchConf {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_batch_conf_seq.nextval from dual")
    private Integer id;

    /**
     * 批次名称：2018年1期
     */
    @Column(name = "batch_name")
    private String batchName;

    /**
     * 批次号：201811
     */
    @Column(name = "batch_number")
    private String batchNumber;

    /**
     * 在线申请开始日期
     */
    @Column(name = "apply_begin")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyBegin;

    /**
     * 在线申请结束日期
     */
    @Column(name = "apply_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEnd;

    /**
     * 受理开始日期
     */
    @Column(name = "accept_begin")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptBegin;

    /**
     * 受理结束日期
     */
    @Column(name = "accept_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptEnd;

    /**
     * 发布开始日期
     */
    @Column(name = "publish_begin")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publishBegin;

    /**
     * 发布结束日期
     */
    @Column(name = "publish_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publishEnd;

    /**
     * 公示开始日期
     */
    @Column(name = "notice_begin")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date noticeBegin;

    /**
     * 公示结束日期
     */
    @Column(name = "notice_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date noticeEnd;

    /**
     * 公示结束日期
     */
    @Column(name = "self_score_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date selfScoreEnd;

    /**
     * 受理地点
     */
    @Column(name = "accept_address_id")
    private Integer acceptAddressId;

    /**
     * 指标方式 0:总人数选取 1:分数线选取
     */
    @Column(name = "indicator_type")
    private Integer indicatorType;

    /**
     * 指标值
     */
    @Column(name = "indicator_value")
    private Integer indicatorValue;

    /**
     * 设置受理人数
     */
    @Column(name = "accept_user_count")
    private Integer acceptUserCount;

    /**
     * 当前状态
     */
    private Integer status;

    /**
     * 当前进程
     */
    private Integer process;

    /**
     * 2018年11月12日，添加4个字段
     * 1、关闭单位注册、填写申请人信息、自助测评、申请预审功能的时间；
     * 2、打开单位注册、填写申请人信息、自助测评、申请预审功能的时间；
     * 3、关闭申请人登录功能的时间；
     * 4、打开申请人登录功能的时间；
     */
    //关闭功能
    @Column(name = "close_function_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date closeFunctionTime;

    //打开功能
    @Column(name = "open_function_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date openFunctionTime;

    //关闭登录功能
    @Column(name = "close_login_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date closeLoginTime;

    //打开登录功能的时间，精确到分钟
    @Column(name = "open_login_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date openLoginTime;

    @Column(name = "closeOrder")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date closeOrder;


    /**
     * 2019年1月4日 增加一个字段值
     * 表示名单公示的最低分数线
     */
    @Column(name = "score_value")
    private Integer scoreValue;



    //关闭企业、个人注册时间
    @Column(name = "closeRegisterTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date closeRegisterTime;

    //打开企业、个人注册时间
    @Column(name = "openRegisterTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date openRegisterTime;


    //积分开放查询的时候的开始时间
    @Column(name = "queryTimeStart")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date queryTimeStart;

    //积分开放查询的时候的结束时间
    @Column(name = "queryTimeEnd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date queryTimeEnd;

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
     * 获取批次名称：2018年1期
     *
     * @return batch_name - 批次名称：2018年1期
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * 设置批次名称：2018年1期
     *
     * @param batchName 批次名称：2018年1期
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    /**
     * 获取批次号：201811
     *
     * @return batch_number - 批次号：201811
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * 设置批次号：201811
     *
     * @param batchNumber 批次号：201811
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * 获取在线申请开始日期
     *
     * @return apply_begin - 在线申请开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getApplyBegin() {
        return applyBegin;
    }

    /**
     * 设置在线申请开始日期
     *
     * @param applyBegin 在线申请开始日期
     */
    public void setApplyBegin(Date applyBegin) {
        this.applyBegin = applyBegin;
    }

    /**
     * 获取在线申请结束日期
     *
     * @return apply_end - 在线申请结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getApplyEnd() {
        return applyEnd;
    }

    /**
     * 设置在线申请结束日期
     *
     * @param applyEnd 在线申请结束日期
     */
    public void setApplyEnd(Date applyEnd) {
        this.applyEnd = applyEnd;
    }

    /**
     * 获取受理开始日期
     *
     * @return accept_begin - 受理开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getAcceptBegin() {
        return acceptBegin;
    }

    /**
     * 设置受理开始日期
     *
     * @param acceptBegin 受理开始日期
     */
    public void setAcceptBegin(Date acceptBegin) {
        this.acceptBegin = acceptBegin;
    }

    /**
     * 获取受理结束日期
     *
     * @return accept_end - 受理结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getAcceptEnd() {
        return acceptEnd;
    }

    /**
     * 设置受理结束日期
     *
     * @param acceptEnd 受理结束日期
     */
    public void setAcceptEnd(Date acceptEnd) {
        this.acceptEnd = acceptEnd;
    }

    /**
     * 获取发布开始日期
     *
     * @return publish_begin - 发布开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getPublishBegin() {
        return publishBegin;
    }

    /**
     * 设置发布开始日期
     *
     * @param publishBegin 发布开始日期
     */
    public void setPublishBegin(Date publishBegin) {
        this.publishBegin = publishBegin;
    }

    /**
     * 获取发布结束日期
     *
     * @return publish_end - 发布结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getPublishEnd() {
        return publishEnd;
    }

    /**
     * 设置发布结束日期
     *
     * @param publishEnd 发布结束日期
     */
    public void setPublishEnd(Date publishEnd) {
        this.publishEnd = publishEnd;
    }

    /**
     * 获取公示开始日期
     *
     * @return notice_begin - 公示开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getNoticeBegin() {
        return noticeBegin;
    }

    /**
     * 设置公示开始日期
     *
     * @param noticeBegin 公示开始日期
     */
    public void setNoticeBegin(Date noticeBegin) {
        this.noticeBegin = noticeBegin;
    }

    /**
     * 获取公示结束日期
     *
     * @return notice_end - 公示结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getNoticeEnd() {
        return noticeEnd;
    }

    /**
     * 设置公示结束日期
     *
     * @param noticeEnd 公示结束日期
     */
    public void setNoticeEnd(Date noticeEnd) {
        this.noticeEnd = noticeEnd;
    }

    /**
     * 获取公示结束日期
     *
     * @return self_score_end - 公示结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getSelfScoreEnd() {
        return selfScoreEnd;
    }

    /**
     * 设置公示结束日期
     *
     * @param selfScoreEnd 公示结束日期
     */
    public void setSelfScoreEnd(Date selfScoreEnd) {
        this.selfScoreEnd = selfScoreEnd;
    }

    /**
     * 获取受理地点
     *
     * @return accept_address_id - 受理地点
     */
    public Integer getAcceptAddressId() {
        return acceptAddressId;
    }

    /**
     * 设置受理地点
     *
     * @param acceptAddressId 受理地点
     */
    public void setAcceptAddressId(Integer acceptAddressId) {
        this.acceptAddressId = acceptAddressId;
    }

    /**
     * 获取指标方式 0:总人数选取 1:分数线选取
     *
     * @return indicator_type - 指标方式 0:总人数选取 1:分数线选取
     */
    public Integer getIndicatorType() {
        return indicatorType;
    }

    /**
     * 设置指标方式 0:总人数选取 1:分数线选取
     *
     * @param indicatorType 指标方式 0:总人数选取 1:分数线选取
     */
    public void setIndicatorType(Integer indicatorType) {
        this.indicatorType = indicatorType;
    }

    /**
     * 获取指标值
     *
     * @return indicator_value - 指标值
     */
    public Integer getIndicatorValue() {
        return indicatorValue;
    }

    /**
     * 设置指标值
     *
     * @param indicatorValue 指标值
     */
    public void setIndicatorValue(Integer indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    /**
     * 获取设置受理人数
     *
     * @return accept_user_count - 设置受理人数
     */
    public Integer getAcceptUserCount() {
        return acceptUserCount;
    }

    /**
     * 设置设置受理人数
     *
     * @param acceptUserCount 设置受理人数
     */
    public void setAcceptUserCount(Integer acceptUserCount) {
        this.acceptUserCount = acceptUserCount;
    }

    /**
     * 获取当前状态
     *
     * @return status - 当前状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置当前状态
     *
     * @param status 当前状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取当前进程
     *
     * @return process - 当前进程
     */
    public Integer getProcess() {
        return process;
    }

    /**
     * 设置当前进程
     *
     * @param process 当前进程
     */
    public void setProcess(Integer process) {
        this.process = process;
    }


    public void setCloseFunctionTime(Date closeFunctionTime) {
        this.closeFunctionTime = closeFunctionTime;
    }

    public void setOpenFunctionTime(Date openFunctionTime) {
        this.openFunctionTime = openFunctionTime;
    }

    public void setCloseLoginTime(Date closeLoginTime) {
        this.closeLoginTime = closeLoginTime;
    }

    public void setOpenLoginTime(Date openLoginTime) {
        this.openLoginTime = openLoginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getCloseFunctionTime() {
        return closeFunctionTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getOpenFunctionTime() {
        return openFunctionTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getCloseLoginTime() {
        return closeLoginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getOpenLoginTime() {
        return openLoginTime;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getCloseOrder() {
        return closeOrder;
    }

    public void setCloseOrder(Date closeOrder) {
        this.closeOrder = closeOrder;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getCloseRegisterTime() {
        return closeRegisterTime;
    }

    public void setCloseRegisterTime(Date closeRegisterTime) {
        this.closeRegisterTime = closeRegisterTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getOpenRegisterTime() {
        return openRegisterTime;
    }

    public void setOpenRegisterTime(Date openRegisterTime) {
        this.openRegisterTime = openRegisterTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getQueryTimeStart() {
        return queryTimeStart;
    }

    public void setQueryTimeStart(Date queryTimeStart) {
        this.queryTimeStart = queryTimeStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    public Date getQueryTimeEnd() {
        return queryTimeEnd;
    }

    public void setQueryTimeEnd(Date queryTimeEnd) {
        this.queryTimeEnd = queryTimeEnd;
    }
}
