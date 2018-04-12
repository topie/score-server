package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_identity_info")
public class IdentityInfo {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;

    /**
     * 单位ID
     */
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 身份证正面图片
     */
    @Column(name = "id_card_positive")
    private String idCardPositive;

    /**
     * 身份证反面图片
     */
    @Column(name = "id_card_opposite")
    private String idCardOpposite;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：1、男；2、女
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 民族
     */
    private String nation;

    /**
     * 拟落户地区
     */
    private Integer region;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 更新时间
     */
    @Column(name = "u_time")
    private Date uTime;

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
     * 获取单位ID
     *
     * @return company_id - 单位ID
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置单位ID
     *
     * @param companyId 单位ID
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取身份证正面图片
     *
     * @return id_card_positive - 身份证正面图片
     */
    public String getIdCardPositive() {
        return idCardPositive;
    }

    /**
     * 设置身份证正面图片
     *
     * @param idCardPositive 身份证正面图片
     */
    public void setIdCardPositive(String idCardPositive) {
        this.idCardPositive = idCardPositive;
    }

    /**
     * 获取身份证反面图片
     *
     * @return id_card_opposite - 身份证反面图片
     */
    public String getIdCardOpposite() {
        return idCardOpposite;
    }

    /**
     * 设置身份证反面图片
     *
     * @param idCardOpposite 身份证反面图片
     */
    public void setIdCardOpposite(String idCardOpposite) {
        this.idCardOpposite = idCardOpposite;
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
     * 获取性别：1、男；2、女
     *
     * @return sex - 性别：1、男；2、女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别：1、男；2、女
     *
     * @param sex 性别：1、男；2、女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取民族
     *
     * @return nation - 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取拟落户地区
     *
     * @return region - 拟落户地区
     */
    public Integer getRegion() {
        return region;
    }

    /**
     * 设置拟落户地区
     *
     * @param region 拟落户地区
     */
    public void setRegion(Integer region) {
        this.region = region;
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
     * 获取更新时间
     *
     * @return u_time - 更新时间
     */
    public Date getuTime() {
        return uTime;
    }

    /**
     * 设置更新时间
     *
     * @param uTime 更新时间
     */
    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }
}