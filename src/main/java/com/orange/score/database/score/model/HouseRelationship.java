package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_house_relationship")
public class HouseRelationship {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}