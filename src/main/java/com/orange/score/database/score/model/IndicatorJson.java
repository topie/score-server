package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_indicator_json")
public class IndicatorJson {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 批次ID
     */
    @Column(name = "batch_id")
    private Integer batchId;

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
     * 指标json
     */
    private String json;

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

    /**
     * 获取指标json
     *
     * @return json - 指标json
     */
    public String getJson() {
        return json;
    }

    /**
     * 设置指标json
     *
     * @param json 指标json
     */
    public void setJson(String json) {
        this.json = json;
    }
}