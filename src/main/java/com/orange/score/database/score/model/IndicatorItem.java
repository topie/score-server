package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_indicator_item")
public class IndicatorItem {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_indicator_item_seq.nextval from dual")
    private Integer id;

    /**
     * 指标信息ID
     */
    @Column(name = "indicator_id")
    private Integer indicatorId;

    /**
     * 选项内容
     */
    private String content;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 分数计算
     */
    @Column(name = "score_function")
    private String scoreFunction;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建用户
     */
    @Column(name = "add_user")
    private String addUser;

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
     * 获取指标信息ID
     *
     * @return indicator_id - 指标信息ID
     */
    public Integer getIndicatorId() {
        return indicatorId;
    }

    /**
     * 设置指标信息ID
     *
     * @param indicatorId 指标信息ID
     */
    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    /**
     * 获取选项内容
     *
     * @return content - 选项内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置选项内容
     *
     * @param content 选项内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取分数
     *
     * @return score - 分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取分数计算
     *
     * @return score_function - 分数计算
     */
    public String getScoreFunction() {
        return scoreFunction;
    }

    /**
     * 设置分数计算
     *
     * @param scoreFunction 分数计算
     */
    public void setScoreFunction(String scoreFunction) {
        this.scoreFunction = scoreFunction;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建用户
     *
     * @return add_user - 创建用户
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * 设置创建用户
     *
     * @param addUser 创建用户
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }
}
