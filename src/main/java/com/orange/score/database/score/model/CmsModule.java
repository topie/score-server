package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_system_notice")
public class CmsModule {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select seq_id.nextval from dual")
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型，1、相关政策，2、办理指南；2、重要通知
     */
    private Integer type;

    /**
     * 发文机关或作者
     */
    private String author;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 内容
     */
    private String content;

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取类型，1、相关政策，2、办理指南；2、重要通知
     *
     * @return type - 类型，1、相关政策，2、办理指南；2、重要通知
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型，1、相关政策，2、办理指南；2、重要通知
     *
     * @param type 类型，1、相关政策，2、办理指南；2、重要通知
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取发文机关或作者
     *
     * @return author - 发文机关或作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置发文机关或作者
     *
     * @param author 发文机关或作者
     */
    public void setAuthor(String author) {
        this.author = author;
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

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}
