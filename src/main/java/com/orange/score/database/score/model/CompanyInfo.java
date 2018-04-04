package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_company_info")
public class CompanyInfo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 企业名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 单位类型，1、企事业单位；2、个体工商户
     */
    @Column(name = "company_type")
    private Integer companyType;

    /**
     * 统一社会信用代码
     */
    @Column(name = "society_code")
    private String societyCode;

    /**
     * 单位联系电话
     */
    @Column(name = "company_mobile")
    private String companyMobile;

    /**
     * 经办人姓名
     */
    private String operator;

    /**
     * 经办人联系手机
     */
    @Column(name = "operator_mobile")
    private String operatorMobile;

    /**
     * 经办人联系地址
     */
    @Column(name = "operator_address")
    private String operatorAddress;

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
     * 状态
     */
    private Integer status;

    /**
     * 备注说明
     */
    private String remark;

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
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取单位类型，1、企事业单位；2、个体工商户
     *
     * @return company_type - 单位类型，1、企事业单位；2、个体工商户
     */
    public Integer getCompanyType() {
        return companyType;
    }

    /**
     * 设置单位类型，1、企事业单位；2、个体工商户
     *
     * @param companyType 单位类型，1、企事业单位；2、个体工商户
     */
    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    /**
     * 获取统一社会信用代码
     *
     * @return society_code - 统一社会信用代码
     */
    public String getSocietyCode() {
        return societyCode;
    }

    /**
     * 设置统一社会信用代码
     *
     * @param societyCode 统一社会信用代码
     */
    public void setSocietyCode(String societyCode) {
        this.societyCode = societyCode;
    }

    /**
     * 获取单位联系电话
     *
     * @return company_mobile - 单位联系电话
     */
    public String getCompanyMobile() {
        return companyMobile;
    }

    /**
     * 设置单位联系电话
     *
     * @param companyMobile 单位联系电话
     */
    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    /**
     * 获取经办人姓名
     *
     * @return operator - 经办人姓名
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置经办人姓名
     *
     * @param operator 经办人姓名
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取经办人联系手机
     *
     * @return operator_mobile - 经办人联系手机
     */
    public String getOperatorMobile() {
        return operatorMobile;
    }

    /**
     * 设置经办人联系手机
     *
     * @param operatorMobile 经办人联系手机
     */
    public void setOperatorMobile(String operatorMobile) {
        this.operatorMobile = operatorMobile;
    }

    /**
     * 获取经办人联系地址
     *
     * @return operator_address - 经办人联系地址
     */
    public String getOperatorAddress() {
        return operatorAddress;
    }

    /**
     * 设置经办人联系地址
     *
     * @param operatorAddress 经办人联系地址
     */
    public void setOperatorAddress(String operatorAddress) {
        this.operatorAddress = operatorAddress;
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
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注说明
     *
     * @return remark - 备注说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注说明
     *
     * @param remark 备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}