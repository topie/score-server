package com.orange.score.database.score.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rui15 on 2019/2/27.
 */
@Table(name = "t_fake_record_company")
public class FakeRecordCompany {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_fake_record_company_seq.nextval from dual")
    private Integer id;

    /*
    公司名字
     */
    @Column(name = "COMPANY_NAME")
    private String companyName;

    /*
    公司统一社会信用代码
     */
    @Column(name = "COMPANY_USID")
    private String companyUsid;

    /*
    提交虚假材料期次
     */
    @Column(name = "BATCH_ID")
    private Integer batchId;

    /*
    录入时间
     */
    @Column(name = "RECORD_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    /*
    惩罚截止时间
     */
    @Column(name = "FINISH_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    /*
    录入用户
     */
    @Column(name = "ADD_USER")
    private String addUser;

    /**
     * 虚假内容
     */
    @Column(name = "fake_content")
    private String fakeContent;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUsid() {
        return companyUsid;
    }

    public void setCompanyUsid(String companyUsid) {
        this.companyUsid = companyUsid;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getFakeContent() {
        return fakeContent;
    }

    public void setFakeContent(String fakeContent) {
        this.fakeContent = fakeContent;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}
