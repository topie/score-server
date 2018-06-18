package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_fake_record")
public class FakeRecord {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_fake_record_seq.nextval from dual")
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;

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
     * 企业代码
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 批次号
     */
    @Column(name = "batch_code")
    private String batchCode;

    /**
     * 虚假内容
     */
    @Column(name = "fake_content")
    private String fakeContent;

    /**
     * 违规日期
     */
    @Column(name = "record_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 获取姓名
     *
     * @return user_name - 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取身份证号
     *
     * @return id_number - 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置身份证号
     *
     * @param idNumber 身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
     * 获取企业代码
     *
     * @return company_code - 企业代码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 设置企业代码
     *
     * @param companyCode 企业代码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 获取批次号
     *
     * @return batch_code - 批次号
     */
    public String getBatchCode() {
        return batchCode;
    }

    /**
     * 设置批次号
     *
     * @param batchCode 批次号
     */
    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    /**
     * 获取虚假内容
     *
     * @return fake_content - 虚假内容
     */
    public String getFakeContent() {
        return fakeContent;
    }

    /**
     * 设置虚假内容
     *
     * @param fakeContent 虚假内容
     */
    public void setFakeContent(String fakeContent) {
        this.fakeContent = fakeContent;
    }

    /**
     * 获取违规日期
     *
     * @return record_date - 违规日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getRecordDate() {
        return recordDate;
    }

    /**
     * 设置违规日期
     *
     * @param recordDate 违规日期
     */
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
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
