package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rui15 on 2019/2/28.
 */
@Table(name = "t_fake_record_history")
public class FakeRecordHistory {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_fake_record_history_seq.nextval from dual")
    private Integer id;

    /*
    名字或者企业名称
     */
    @Column(name = "name")
    private String name;

    /*
    身份证号或者社会统一信用代码
     */
    @Column(name = "ID_CODE")
    private String idCode;

    /*
    操作：创建、修改、删除
     */
    @Column(name = "handle")
    private String handle;

    /*
    创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;


    /*
    批次
     */
    @Column(name = "BATCH_ID")
    private Integer batchId;

    /*
    内容
     */
    @Column(name = "content")
    private String content;


    /*
    操作人
     */
    @Column(name = "operator")
    private String operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
