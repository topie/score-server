package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_house_move")
public class HouseMove {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_house_move_seq.nextval from dual")
    private Integer id;

    /**
     * 申请人身份信息id
     */
    @Column(name = "identity_info_id")
    private Integer identityInfoId;

    /**
     * 迁出地区（省）
     */
    @Column(name = "move_province")
    private Integer moveProvince;

    /**
     * 迁出地区（市）
     */
    @Column(name = "move_city")
    private Integer moveCity;

    /**
     * 迁出地区（区）
     */
    @Column(name = "move_region")
    private Integer moveRegion;

    /**
     * 迁出地地址
     */
    @Column(name = "move_address")
    private String moveAddress;

    /**
     * 现户籍登记机关
     */
    @Column(name = "move_registered_office")
    private String moveRegisteredOffice;

    /**
     * 现户口性质
     */
    @Column(name = "house_nature")
    private Integer houseNature;

    /**
     * 落户性质
     */
    @Column(name = "settled_nature")
    private Integer settledNature;

    /**
     * 迁入户籍登记机关
     */
    @Column(name = "registered_office")
    private String registeredOffice;

    @Column(name = "registered_region")
    private String registeredRegion;

    /**
     * 迁入地详细地址
     */
    private String address;

    /**
     * 证明人
     */
    private String witness;

    /**
     * 证明人电话
     */
    @Column(name = "witness_phone")
    private String witnessPhone;

    /**
     * 证明人收件地址
     */
    @Column(name = "witness_address")
    private String witnessAddress;

    /**
     * 拟落户地区
     */
    private Integer region;

    /**
     * 婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚
     */
    @Column(name = "marriage_status")
    private Integer marriageStatus;

    /**
     * 有无子女，1、有；2、无
     */
    @Column(name = "have_son")
    private Integer haveSon;

    /**
     * 子女数量
     */
    @Column(name = "son_number")
    private Integer sonNumber;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    public String getRegisteredRegion() {
        return registeredRegion;
    }

    public void setRegisteredRegion(String registeredRegion) {
        this.registeredRegion = registeredRegion;
    }

    public String getSettledNatureStr() {
        switch (settledNature) {
            case 1:
                return "本单位集体户口";
            case 2:
                return "非本单位集体户口";
            case 3:
                return "家庭户口";
        }
        return "-";
    }

    public String getRegionStr() {
        switch (region) {
            case 21:
                return "和平区";
            case 22:
                return "河东区";
            case 23:
                return "河西区";
            case 24:
                return "南开区";
            case 25:
                return "河北区";
            case 26:
                return "红桥区";
            case 27:
                return "东丽区";
            case 28:
                return "西青区";
            case 29:
                return "津南区";
            case 30:
                return "北辰区";
            case 31:
                return "武清区";
            case 32:
                return "宝坻区";
            case 33:
                return "滨海新区";
            case 34:
                return "宁河区";
            case 35:
                return "静海区";
            case 36:
                return "蓟州区";

        }
        return "-";
    }

    public String getHouseNatureStr() {
        switch (houseNature) {
            case 1:
                return "未落常住户口";
            case 2:
                return "非农业家庭户口";
            case 3:
                return "农业家庭户口";
            case 4:
                return "非农业集体户口";
            case 5:
                return "农业集体户口";
            case 6:
                return "自理口粮户口";
            case 7:
                return "寄住户口";
            case 8:
                return "暂住户口";
            case 9:
                return "地方城镇居民户口";
            case 10:
                return "其他户口";
        }
        return "-";
    }

    public String getHaveSonStr() {
        return haveSon == 1 ? "有" : "无";
    }

    public String getMarriageStatusStr() {
        if (marriageStatus == null) return "-";
        switch (marriageStatus) {
            case 1:
                return "已婚";
            case 2:
                return "未婚";
            case 3:
                return "丧偶";
            case 4:
                return "离婚";
        }
        return "-";
    }

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
     * 获取迁出地区（省）
     *
     * @return move_province - 迁出地区（省）
     */
    public Integer getMoveProvince() {
        return moveProvince;
    }

    /**
     * 设置迁出地区（省）
     *
     * @param moveProvince 迁出地区（省）
     */
    public void setMoveProvince(Integer moveProvince) {
        this.moveProvince = moveProvince;
    }

    /**
     * 获取迁出地区（市）
     *
     * @return move_city - 迁出地区（市）
     */
    public Integer getMoveCity() {
        return moveCity;
    }

    /**
     * 设置迁出地区（市）
     *
     * @param moveCity 迁出地区（市）
     */
    public void setMoveCity(Integer moveCity) {
        this.moveCity = moveCity;
    }

    /**
     * 获取迁出地区（区）
     *
     * @return move_region - 迁出地区（区）
     */
    public Integer getMoveRegion() {
        return moveRegion;
    }

    /**
     * 设置迁出地区（区）
     *
     * @param moveRegion 迁出地区（区）
     */
    public void setMoveRegion(Integer moveRegion) {
        this.moveRegion = moveRegion;
    }

    /**
     * 获取迁出地地址
     *
     * @return move_address - 迁出地地址
     */
    public String getMoveAddress() {
        return moveAddress;
    }

    /**
     * 设置迁出地地址
     *
     * @param moveAddress 迁出地地址
     */
    public void setMoveAddress(String moveAddress) {
        this.moveAddress = moveAddress;
    }

    /**
     * 获取现户籍登记机关
     *
     * @return move_registered_office - 现户籍登记机关
     */
    public String getMoveRegisteredOffice() {
        return moveRegisteredOffice;
    }

    /**
     * 设置现户籍登记机关
     *
     * @param moveRegisteredOffice 现户籍登记机关
     */
    public void setMoveRegisteredOffice(String moveRegisteredOffice) {
        this.moveRegisteredOffice = moveRegisteredOffice;
    }

    /**
     * 获取现户口性质
     *
     * @return house_nature - 现户口性质
     */
    public Integer getHouseNature() {
        return houseNature;
    }

    /**
     * 设置现户口性质
     *
     * @param houseNature 现户口性质
     */
    public void setHouseNature(Integer houseNature) {
        this.houseNature = houseNature;
    }

    /**
     * 获取落户性质
     *
     * @return settled_nature - 落户性质
     */
    public Integer getSettledNature() {
        return settledNature;
    }

    /**
     * 设置落户性质
     *
     * @param settledNature 落户性质
     */
    public void setSettledNature(Integer settledNature) {
        this.settledNature = settledNature;
    }

    /**
     * 获取迁入户籍登记机关
     *
     * @return registered_office - 迁入户籍登记机关
     */
    public String getRegisteredOffice() {
        return registeredOffice;
    }

    /**
     * 设置迁入户籍登记机关
     *
     * @param registeredOffice 迁入户籍登记机关
     */
    public void setRegisteredOffice(String registeredOffice) {
        this.registeredOffice = registeredOffice;
    }

    /**
     * 获取迁入地详细地址
     *
     * @return address - 迁入地详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置迁入地详细地址
     *
     * @param address 迁入地详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取证明人
     *
     * @return witness - 证明人
     */
    public String getWitness() {
        return witness;
    }

    /**
     * 设置证明人
     *
     * @param witness 证明人
     */
    public void setWitness(String witness) {
        this.witness = witness;
    }

    /**
     * 获取证明人电话
     *
     * @return witness_phone - 证明人电话
     */
    public String getWitnessPhone() {
        return witnessPhone;
    }

    /**
     * 设置证明人电话
     *
     * @param witnessPhone 证明人电话
     */
    public void setWitnessPhone(String witnessPhone) {
        this.witnessPhone = witnessPhone;
    }

    /**
     * 获取证明人收件地址
     *
     * @return witness_address - 证明人收件地址
     */
    public String getWitnessAddress() {
        return witnessAddress;
    }

    /**
     * 设置证明人收件地址
     *
     * @param witnessAddress 证明人收件地址
     */
    public void setWitnessAddress(String witnessAddress) {
        this.witnessAddress = witnessAddress;
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
     * 获取婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚
     *
     * @return marriage_status - 婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚
     */
    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    /**
     * 设置婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚
     *
     * @param marriageStatus 婚姻状态，1、已婚；2、未婚；3、丧偶；4、离婚
     */
    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    /**
     * 获取有无子女，1、有；2、无
     *
     * @return have_son - 有无子女，1、有；2、无
     */
    public Integer getHaveSon() {
        return haveSon;
    }

    /**
     * 设置有无子女，1、有；2、无
     *
     * @param haveSon 有无子女，1、有；2、无
     */
    public void setHaveSon(Integer haveSon) {
        this.haveSon = haveSon;
    }

    /**
     * 获取子女数量
     *
     * @return son_number - 子女数量
     */
    public Integer getSonNumber() {
        return sonNumber;
    }

    /**
     * 设置子女数量
     *
     * @param sonNumber 子女数量
     */
    public void setSonNumber(Integer sonNumber) {
        this.sonNumber = sonNumber;
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
