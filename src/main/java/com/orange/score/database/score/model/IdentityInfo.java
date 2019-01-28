package com.orange.score.database.score.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orange.score.common.utils.CollectionUtil;
import com.orange.score.common.utils.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "t_identity_info")
public class IdentityInfo {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_identity_info_seq.nextval from dual")
    private Integer id;

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "batch_id")
    private Integer batchId;

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

    @Column(name = "accept_number")
    private String acceptNumber;

    @Column(name = "accept_address")
    private String acceptAddress;

    @Column(name = "reservation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @Column(name = "reservation_m")
    private Integer reservationM;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "accept_address_id")
    private Integer acceptAddressId;

    @Transient
    private String orderByColumn;

    @Column(name = "luohu_number")
    private String luohuNumber;

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

    //2019-1-9添加卫健委信息
    @Column(name = "formerName")
    private String formerName;//"曾用名"

    @Column(name = "pregnantPromise")
    private Integer pregnantPromise;//本人或配偶目前 1.承诺 2.不承诺 已怀孕_周

    public String getStringPregnantPromise() {
        if (this.pregnantPromise == null) {
            return "_";
        } else {
            switch (this.pregnantPromise) {
                case 1:
                    return "承诺";
                case 2:
                    return "不承诺";
                default:
                    return "_";
            }
        }
    }

    @Column(name = "pregnantWeek")
    private String pregnantWeek;//本人或配偶目前(不)承诺 已怀孕_周

    @Column(name = "thirdPregnantPromise")
    private Integer thirdPregnantPromise;//本人或配偶1.承诺 2.不承诺目前未处于政策外第三个及以上子女怀孕期间

    public String getStringThirdPregnantPromise() {
        if (this.thirdPregnantPromise == null) {
            return "_";
        } else {
            switch (this.thirdPregnantPromise) {
                case 1:
                    return "承诺";
                case 2:
                    return "不承诺";
                default:
                    return "_";
            }
        }
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(String pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }

    public Integer getPregnantPromise() {
        return pregnantPromise;
    }

    public void setPregnantPromise(Integer pregnantPromise) {
        this.pregnantPromise = pregnantPromise;
    }

    public Integer getThirdPregnantPromise() {
        return thirdPregnantPromise;
    }

    public void setThirdPregnantPromise(Integer thirdPregnantPromise) {
        this.thirdPregnantPromise = thirdPregnantPromise;
    }

    //2019-1-9添加卫健委信息END

    @Column(name = "reservation_status")
    private Integer reservationStatus;

    @Column(name = "hall_status")
    private Integer hallStatus;

    @Column(name = "union_approve_status_1")
    private Integer unionApproveStatus1;

    @Column(name = "union_approve_status_2")
    private Integer unionApproveStatus2;

    @Column(name = "police_approve_status")
    private Integer policeApproveStatus;

    @Column(name = "renshe_accept_status")
    private Integer rensheAcceptStatus;

    @Column(name = "cancel_status")
    private Integer cancelStatus;

    @Column(name = "result_status")
    private Integer resultStatus;

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
     * 人社受理审核通过时间，用来控制自动接收材料的时间
     */
    @Column(name = "renshePass_time")
    private Date renshePassTime;

    @Column(name = "renshe_accept_supply_et")
    private Date rensheAcceptSupplyEt;

    @Column(name = "police_approve_et")
    private Date policeApproveEt;

    @Column(name = "union_approve_1_et")
    private Date unionApprove1Et;

    @Column(name = "union_approve_2_et")
    private Date unionApprove2Et;

    @Transient
    private String etStatus;

    @Transient
    private String epStatus;

    @Transient
    private String orderBy;

    @Column(name = "reject_reason")
    private String rejectReason;

    private String opuser1;

    private String opuser2;

    private String opuser3;

    private String opuser4;

    private String opuser5;

    private String opuser6;

    @Column(name = "lock_user_1")
    private String lockUser1;

    @Column(name = "lock_user_2")
    private String lockUser2;

    @Column(name = "materialStatus")
    private Integer materialStatus;//"材料送达补正状态"

    public Integer getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(Integer materialStatus) {
        this.materialStatus = materialStatus;
    }

    //驳回材料部门的集合
    @Transient
    private Set<Integer> opuser6RoleSet;

    public Set<Integer> getOpuser6RoleSet() {
        return opuser6RoleSet;
    }

    public void setOpuser6RoleSet(Set<Integer> opuser6RoleSet) {
        this.opuser6RoleSet = opuser6RoleSet;
        if (opuser6RoleSet != null) {
            opuser6 = StringUtils.join(opuser6RoleSet, ",");
        }
    }

    @Transient
    private Integer companyWarning = 0;

    public String getLockUser1() {
        return lockUser1;
    }

    public void setLockUser1(String lockUser1) {
        this.lockUser1 = lockUser1;
    }

    public String getLockUser2() {
        return lockUser2;
    }

    public void setLockUser2(String lockUser2) {
        this.lockUser2 = lockUser2;
    }

    public Integer getCompanyWarning() {
        return companyWarning;
    }

    public void setCompanyWarning(Integer companyWarning) {
        this.companyWarning = companyWarning;
    }

    public String getLuohuNumber() {
        return luohuNumber;
    }

    public void setLuohuNumber(String luohuNumber) {
        this.luohuNumber = luohuNumber;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getEtStatus() {
        int min = 0;
        if (policeApproveEt == null) return "-";
        Date now = new Date();
        if ((2 == policeApproveStatus || 1 == policeApproveStatus) && policeApproveEt != null) {
            min = DateUtil.getIntervalMins(policeApproveEt, now);
        }
        if (min > 0) {
            int day = min / 24;
            if (day > 0) return "剩余" + day + "天";
            return "剩余" + min + "小时";
        } else if (min < 0) {
            return "已过期";
        } else {
            return "-";
        }
    }

    public void setEtStatus(String etStatus) {
        this.etStatus = etStatus;
    }

    public String getEpStatus() {
        int min = 0;
        if (unionApprove1Et == null)
            return "-";
        Date now = new Date();
        if (unionApproveStatus1 == 2 && unionApproveStatus2 == 2) {
            return "-";
        }
        if (unionApproveStatus1 != 3 && unionApproveStatus2 != 3 && unionApprove1Et != null) {
            min = DateUtil.getIntervalMins(unionApprove2Et, now);
        }
        if (min > 0) {
            int day = min / 24;
            if (day > 0) return "剩余" + day + "天";
            return "剩余" + min + "小时";
        } else if (min < 0) {
            return "已过期";
        } else {
            return "-";
        }
    }

    public void setEpStatus(String epStatus) {
        this.epStatus = epStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getOpuser1() {
        return opuser1;
    }

    public void setOpuser1(String opuser1) {
        this.opuser1 = opuser1;
    }

    public String getOpuser2() {
        return opuser2;
    }

    public void setOpuser2(String opuser2) {
        this.opuser2 = opuser2;
    }

    public String getOpuser3() {
        return opuser3;
    }

    public void setOpuser3(String opuser3) {
        this.opuser3 = opuser3;
    }

    public String getOpuser4() {
        return opuser4;
    }

    public void setOpuser4(String opuser4) {
        this.opuser4 = opuser4;
    }

    public String getOpuser5() {
        return opuser5;
    }

    public void setOpuser5(String opuser5) {
        this.opuser5 = opuser5;
    }

    public String getOpuser6() {
        return opuser6;
    }

    public void setOpuser6(String opuser6) {
        this.opuser6 = opuser6;
        if (StringUtils.isNotEmpty(opuser6)) {
            this.opuser6RoleSet = new HashSet<>(Arrays.asList(CollectionUtil.StringToIntegerArr(opuser6)));
        }
    }

    public Date getRensheAcceptSupplyEt() {
        return rensheAcceptSupplyEt;
    }

    public void setRensheAcceptSupplyEt(Date rensheAcceptSupplyEt) {
        this.rensheAcceptSupplyEt = rensheAcceptSupplyEt;
    }

    public Date getPoliceApproveEt() {
        return policeApproveEt;
    }

    public void setPoliceApproveEt(Date policeApproveEt) {
        this.policeApproveEt = policeApproveEt;
    }

    public Date getUnionApprove1Et() {
        return unionApprove1Et;
    }

    public void setUnionApprove1Et(Date unionApprove1Et) {
        this.unionApprove1Et = unionApprove1Et;
    }

    public Date getUnionApprove2Et() {
        return unionApprove2Et;
    }

    public void setUnionApprove2Et(Date unionApprove2Et) {
        this.unionApprove2Et = unionApprove2Et;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getAcceptAddressId() {
        return acceptAddressId;
    }

    public void setAcceptAddressId(Integer acceptAddressId) {
        this.acceptAddressId = acceptAddressId;
    }

    public String getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(String acceptNumber) {
        this.acceptNumber = acceptNumber;
    }

    public String getAcceptAddress() {
        return acceptAddress;
    }

    public void setAcceptAddress(String acceptAddress) {
        this.acceptAddress = acceptAddress;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getReservationM() {
        return reservationM;
    }

    public void setReservationM(Integer reservationM) {
        this.reservationM = reservationM;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public Integer getPoliceApproveStatus() {
        return policeApproveStatus;
    }

    public void setPoliceApproveStatus(Integer policeApproveStatus) {
        this.policeApproveStatus = policeApproveStatus;
    }

    public Integer getRensheAcceptStatus() {
        return rensheAcceptStatus;
    }

    public void setRensheAcceptStatus(Integer rensheAcceptStatus) {
        this.rensheAcceptStatus = rensheAcceptStatus;
    }

    public Integer getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Integer reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Integer getHallStatus() {
        return hallStatus;
    }

    public void setHallStatus(Integer hallStatus) {
        this.hallStatus = hallStatus;
    }

    public Integer getUnionApproveStatus1() {
        return unionApproveStatus1;
    }

    public void setUnionApproveStatus1(Integer unionApproveStatus1) {
        this.unionApproveStatus1 = unionApproveStatus1;
    }

    public Integer getUnionApproveStatus2() {
        return unionApproveStatus2;
    }

    public void setUnionApproveStatus2(Integer unionApproveStatus2) {
        this.unionApproveStatus2 = unionApproveStatus2;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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

    public Date getRenshePassTime() {
        return renshePassTime;
    }

    public void setRenshePassTime(Date renshePassTime) {
        this.renshePassTime = renshePassTime;
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
