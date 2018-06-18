package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_house_profession")
public class HouseProfession {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_house_profession_seq.nextval from dual")
    private Integer id;

    /**
     * 申请人身份信息id
     */
    @Column(name = "identity_info_id")
    private Integer identityInfoId;

    /**
     * 职业资格项，1、无；2、具有职称；3、具有职业资格
     */
    @Column(name = "profession_type")
    private Integer professionType;

    /**
     * 职称级别，1、初级职称；2、中级职称；3、高级职称
     */
    @Column(name = "job_title_level")
    private Integer jobTitleLevel;

    /**
     * 职位
     */
    @Column(name = "job_position")
    private String jobPosition;

    /**
     * 发证机关
     */
    @Column(name = "issuing_authority")
    private String issuingAuthority;

    /**
     * 发证日期
     */
    @Column(name = "issuing_date")
    private String issuingDate;

    /**
     * 证书编号
     */
    @Column(name = "certificate_code")
    private String certificateCode;

    /**
     * 职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工
     */
    @Column(name = "job_level")
    private Integer jobLevel;

    /**
     * 工种
     */
    @Column(name = "job_type")
    private Integer jobType;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

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
     * 获取申请人身份信息id
     *
     * @return identity_info_id - 申请人身份信息id
     */
    public Integer getIdentityInfoId() {
        return identityInfoId;
    }

    /**
     * 设置申请人身份信息id
     *
     * @param identityInfoId 申请人身份信息id
     */
    public void setIdentityInfoId(Integer identityInfoId) {
        this.identityInfoId = identityInfoId;
    }

    /**
     * 获取职业资格项，1、无；2、具有职称；3、具有职业资格
     *
     * @return profession_type - 职业资格项，1、无；2、具有职称；3、具有职业资格
     */
    public Integer getProfessionType() {
        return professionType;
    }

    /**
     * 设置职业资格项，1、无；2、具有职称；3、具有职业资格
     *
     * @param professionType 职业资格项，1、无；2、具有职称；3、具有职业资格
     */
    public void setProfessionType(Integer professionType) {
        this.professionType = professionType;
    }

    /**
     * 获取职称级别，1、初级职称；2、中级职称；3、高级职称
     *
     * @return job_title_level - 职称级别，1、初级职称；2、中级职称；3、高级职称
     */
    public Integer getJobTitleLevel() {
        return jobTitleLevel;
    }

    /**
     * 设置职称级别，1、初级职称；2、中级职称；3、高级职称
     *
     * @param jobTitleLevel 职称级别，1、初级职称；2、中级职称；3、高级职称
     */
    public void setJobTitleLevel(Integer jobTitleLevel) {
        this.jobTitleLevel = jobTitleLevel;
    }

    /**
     * 获取职位
     *
     * @return job_position - 职位
     */
    public String getJobPosition() {
        return jobPosition;
    }

    /**
     * 设置职位
     *
     * @param jobPosition 职位
     */
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    /**
     * 获取发证机关
     *
     * @return issuing_authority - 发证机关
     */
    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    /**
     * 设置发证机关
     *
     * @param issuingAuthority 发证机关
     */
    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    /**
     * 获取发证日期
     *
     * @return issuing_date - 发证日期
     */
    public String getIssuingDate() {
        return issuingDate;
    }

    /**
     * 设置发证日期
     *
     * @param issuingDate 发证日期
     */
    public void setIssuingDate(String issuingDate) {
        this.issuingDate = issuingDate;
    }

    /**
     * 获取证书编号
     *
     * @return certificate_code - 证书编号
     */
    public String getCertificateCode() {
        return certificateCode;
    }

    /**
     * 设置证书编号
     *
     * @param certificateCode 证书编号
     */
    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    /**
     * 获取职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工
     *
     * @return job_level - 职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工
     */
    public Integer getJobLevel() {
        return jobLevel;
    }

    /**
     * 设置职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工
     *
     * @param jobLevel 职业资格级别,1、高级技师；2、技师；3、高级工；4、中级工；5、初级工
     */
    public void setJobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
    }

    /**
     * 获取工种
     *
     * @return job_type - 工种
     */
    public Integer getJobType() {
        return jobType;
    }

    /**
     * 设置工种
     *
     * @param jobType 工种
     */
    public void setJobType(Integer jobType) {
        this.jobType = jobType;
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
