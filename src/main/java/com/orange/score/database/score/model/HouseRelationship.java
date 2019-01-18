package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_house_relationship")
public class HouseRelationship {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_house_relationship_seq.nextval from dual")
    private Integer id;

    /**
     * 申请人身份信息id
     */
    @Column(name = "identity_info_id")
    private Integer identityInfoId;

    /**
     * 与本人关系
     */
    private String relationship;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;

    /**
     * 是否随迁，1、是；2、否
     */
    @Column(name = "is_remove")
    private Integer isRemove;

    /**
     * 文化程度
     */
    @Column(name = "culture_degree")
    private String cultureDegree;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    //2019-1-9添加卫健委信息

    @Column(name = "formerName")
    private String formerName;//"曾用名"

    @Column(name = "sex")
    private Integer sex;//"性别：1、男；2、女"

    @Column(name = "spouse_HJAddress")
    private String spouse_HJAddress;//"配偶户籍地详细地址"

    @Column(name = "spouse_LivingAddress")
    private String spouse_LivingAddress;//"配偶现居住地详细地址"

    @Column(name = "policyAttribute")
    private String policyAttribute;//"政策属性"

    @Column(name = "medical_number")
    private String medical_number;//"出生医学证明_编号"

    @Column(name = "medical_authority")
    private String medical_authority;//"出生医学证明_签证机构"

    @Column(name = "isAdopt")
    private Integer isAdopt;//"收养子女1、是；2、否"

    @Column(name = "birthplace")
    private String birthplace;//"出生地"

    @Column(name = "approval_time")
    private java.sql.Date approval_time;//"审批时间"

    @Column(name = "approval_number")
    private String approval_number;//"审批证明编号"

    @Column(name = "approval_companyName")
    private String approval_companyName;//"审批单位名称"

    @Column(name = "approval_rules")
    private String approval_rules;//"审批条例适用"

    @Column(name = "approval_which")
    private String approval_which;//"与第几任妻子/丈夫所生"

    @Column(name = "approval_custody")
    private String approval_custody;//"抚养权归属"

    //2019-1-17增加字段

    @Column(name = "marriageStatus")
    private Integer marriageStatus;  //配偶婚姻状况 0.请选择 7.初婚 8.复婚 9.再婚

    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getStringMarriageStatus() {
        if (this.marriageStatus == null) {
            return "";
        } else {
            switch (this.marriageStatus) {
                case 0:
                    return "";
                case 7:
                    return "初婚";
                case 8:
                    return "复婚";
                case 9:
                    return "再婚";
                default:
                    return "";
            }
        }
    }

    //2019-1-17增加字段End

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSpouse_HJAddress() {
        return spouse_HJAddress;
    }

    public void setSpouse_HJAddress(String spouse_HJAddress) {
        this.spouse_HJAddress = spouse_HJAddress;
    }

    public String getSpouse_LivingAddress() {
        return spouse_LivingAddress;
    }

    public void setSpouse_LivingAddress(String spouse_LivingAddress) {
        this.spouse_LivingAddress = spouse_LivingAddress;
    }

    public String getPolicyAttribute() {
        return policyAttribute;
    }

    public void setPolicyAttribute(String policyAttribute) {
        this.policyAttribute = policyAttribute;
    }

    public String getMedical_number() {
        return medical_number;
    }

    public void setMedical_number(String medical_number) {
        this.medical_number = medical_number;
    }

    public String getMedical_authority() {
        return medical_authority;
    }

    public void setMedical_authority(String medical_authority) {
        this.medical_authority = medical_authority;
    }

    public Integer getIsAdopt() {
        return isAdopt;
    }

    public void setIsAdopt(Integer isAdopt) {
        this.isAdopt = isAdopt;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public java.sql.Date getApproval_time() {
        return approval_time;
    }

    public void setApproval_time(java.sql.Date approval_time) {
        this.approval_time = approval_time;
    }

    public String getApproval_number() {
        return approval_number;
    }

    public void setApproval_number(String approval_number) {
        this.approval_number = approval_number;
    }

    public String getApproval_companyName() {
        return approval_companyName;
    }

    public void setApproval_companyName(String approval_companyName) {
        this.approval_companyName = approval_companyName;
    }

    public String getApproval_rules() {
        return approval_rules;
    }

    public void setApproval_rules(String approval_rules) {
        this.approval_rules = approval_rules;
    }

    public String getApproval_which() {
        return approval_which;
    }

    public void setApproval_which(String approval_which) {
        this.approval_which = approval_which;
    }

    public String getApproval_custody() {
        return approval_custody;
    }

    public void setApproval_custody(String approval_custody) {
        this.approval_custody = approval_custody;
    }

    //2019-1-9添加卫健委信息END

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
     * 获取与本人关系
     *
     * @return relationship - 与本人关系
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * 设置与本人关系
     *
     * @param relationship 与本人关系
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取是否随迁，1、是；2、否
     *
     * @return is_remove - 是否随迁，1、是；2、否
     */
    public Integer getIsRemove() {
        return isRemove;
    }

    /**
     * 设置是否随迁，1、是；2、否
     *
     * @param isRemove 是否随迁，1、是；2、否
     */
    public void setIsRemove(Integer isRemove) {
        this.isRemove = isRemove;
    }

    /**
     * 获取文化程度
     *
     * @return culture_degree - 文化程度
     */
    public String getCultureDegree() {
        return cultureDegree;
    }

    /**
     * 设置文化程度
     *
     * @param cultureDegree 文化程度
     */
    public void setCultureDegree(String cultureDegree) {
        this.cultureDegree = cultureDegree;
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

    public String getStringIsAdopt() {
        if (this.isAdopt == null) {
            return "";
        } else {
            switch (this.isAdopt) {
                case 1:
                    return "是";
                case 2:
                    return "否";
                default:
                    return "";
            }
        }
    }

    public String getStringSex() {
        if (this.sex == null) {
            return "";
        } else {
            switch (this.sex) {
                case 1:
                    return "男";
                case 2:
                    return "女";
                default:
                    return "";
            }
        }
    }
}
