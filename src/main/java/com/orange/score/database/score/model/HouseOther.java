package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_house_other")
public class HouseOther {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_house_other_seq.nextval from dual")
    private Integer id;

    /**
     * 申请人身份信息id
     */
    @Column(name = "identity_info_id")
    private Integer identityInfoId;

    /**
     * 申请人类型，1、企业员工；2、投资者；3、个体工商户
     */
    @Column(name = "applicant_type")
    private Integer applicantType;

    /**
     * 政治面貌，1、中共党员；2、中共预备党员；3、共青团员
     */
    @Column(name = "political_status")
    private Integer politicalStatus;

    /**
     * 军人立功，0、无；1、一等功；2、二等功；3、三等功
     */
    @Column(name = "soldier_meritorious")
    private Integer soldierMeritorious;

    /**
     * 文化程度
     */
    @Column(name = "culture_degree")
    private Integer cultureDegree;

    /**
     * 学位
     */
    private Integer degree;

    /**
     * 专业
     */
    private String profession;

    /**
     * 单位名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 单位地址
     */
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 办公地址
     */
    @Column(name = "APPLY_OFFICE_ADDRESS")
    private String applyOfficeAddress;

    public String getApplyOfficeAddress() {
        return applyOfficeAddress;
    }

    public void setApplyOfficeAddress(String applyOfficeAddress) {
        this.applyOfficeAddress = applyOfficeAddress;
    }

    /**
     * 单位电话
     */
    @Column(name = "company_phone")
    private String companyPhone;

    /**
     * 本人电话
     */
    @Column(name = "self_phone")
    private String selfPhone;

    /**
     * 居住证申领日期
     */
    @Column(name = "application_date")
    private String applicationDate;

    /**
     * 申请人是否存在同时在异地缴纳社会保险情况, 1、是；2、否
     */
    @Column(name = "isAnotherInsurance")
    private Integer isAnotherInsurance;

    /**
     * 申请人是否在申请单位进行就业登记, 1、是；2、否
     */
    @Column(name = "isApplyRegiste")
    private Integer isApplyRegiste;

    /**
     * 申请人是否在2008年以前在开发区有社保缴费记录, 1、是；2、否
     */
    @Column(name = "isInsurance")
    private Integer isInsurance;

    /**
     * 配偶是否在本市缴纳5险且满2年以上, 1、是；2、否
     */
    @Column(name = "isSpouseInsurance")
    private Integer isSpouseInsurance;

    /**
     * 是否缴纳社保, 1、是；2、否
     */
    @Column(name = "social_security_pay")
    private Integer socialSecurityPay;

    /**
     * 是否参加住房公积金, 1、是；2、否
     */
    @Column(name = "provident_fund")
    private Integer providentFund;

    /**
     * 纳税情况, 1、是；2、否
     */
    private Integer taxes;

    /**
     * 拘留情况, 1、是；2、否
     */
    private Integer detention;

    /**
     * 获刑情况, 1、是；2、否
     */
    private Integer penalty;

    /**
     * 奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇
     */
    @Column(name = "awards_title")
    private Integer awardsTitle;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 当前工作岗位
     */
    @Column(name = "job_title")
    private String jobTitle;

    /**
     * 主要从事工作内容
     */
    @Column(name = "job_content")
    private String jobContent;

    @Column(name = "penaltyDate")
    private String penaltyDate;//"犯罪日期"

    @Column(name = "penaltyContent")
    private String penaltyContent;//"犯罪内容"

    @Column(name = "penaltyResults")
    private String penaltyResults;//"处罚结果"

    @Column(name = "socialSecurityType")
    private String socialSecurityType;//"缴纳类型"

    // 人社相关信息
    @Column(name = "dispatch")
    private Integer dispatch;//"是否为派遣制用工"

    @Column(name = "industry")
    private String industry;//"实际用工单位所属行业"

    @Column(name = "jobDate")
    private String jobDate;//"自??年??月在此岗位工作"

    public String getPenaltyDate() {
        return penaltyDate;
    }

    public void setPenaltyDate(String penaltyDate) {
        this.penaltyDate = penaltyDate;
    }

    public String getPenaltyContent() {
        return penaltyContent;
    }

    public void setPenaltyContent(String penaltyContent) {
        this.penaltyContent = penaltyContent;
    }

    public String getPenaltyResults() {
        return penaltyResults;
    }

    public void setPenaltyResults(String penaltyResults) {
        this.penaltyResults = penaltyResults;
    }

    public String getSocialSecurityType() {
        return socialSecurityType;
    }

    public void setSocialSecurityType(String socialSecurityType) {
        this.socialSecurityType = socialSecurityType;
    }

    public Integer getDispatch() {
        return dispatch;
    }

    public void setDispatch(Integer dispatch) {
        this.dispatch = dispatch;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getCultureDegreeStr() {
        if (cultureDegree == null) return "-";
        switch (cultureDegree) {
            case 11:
                return "无";
            case 4:
                return "本科及以上学历";
            case 5:
                return "大专学历";
            case 6:
                return "高级技工学校高级班";
        }
        return "无";
    }

    public String getAwardsTitleStr() {
        if (awardsTitle == null) return "-";
        switch (awardsTitle) {
            case 1:
                return "拥有有效的中国发明专利";
            case 2:
                return "获得党中央、国务院授予的奖项和荣誉称号";
            case 3:
                return "获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号";
            case 4:
                return "获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇";
        }
        return "-";
    }

    public String getPenaltyStr() {
        return penalty == 1 ? "是" : "否";
    }

    public String getProvidentFundStr() {
        return providentFund == 1 ? "是" : "否";
    }

    public String getTaxesStr() {
        return taxes == 1 ? "是" : "否";
    }

    public String getDetentionStr() {
        return detention == 1 ? "是" : "否";
    }

    public String getSocialSecurityPayStr() {
        return socialSecurityPay == 1 ? "是" : "否";
    }

    public String getIsAnotherInsuranceStr() {
        return isAnotherInsurance == 1 ? "是" : "否";
    }
    public String getIsApplyRegisteStr() {
        return isApplyRegiste == 1 ? "是" : "否";
    }
    public String getIsInsuranceStr() {
        return isInsurance == 1 ? "是" : "否";
    }
    public String getIsSpouseInsuranceStr() {
        return isSpouseInsurance == 1 ? "是" : "否";
    }

    public String getDegreeStr() {
        if (degree == null) return "-";
        switch (degree) {
            case 0:
                return "无";
            case 1:
                return "博士后";
            case 2:
                return "名誉博士";
            case 3:
                return "博士";
            case 4:
                return "硕士";
            case 5:
                return "学士";
            case 6:
                return "双学士";
            default:
                return "-";
        }
    }

    public String getPoliticalStatusStr() {
        if (politicalStatus == null) return "-";
        switch (politicalStatus) {
            case 1:
                return "中共党员";
            case 2:
                return "中共预备党员";
            case 3:
                return "共青团员";
            case 4:
                return "民革会员";
            case 5:
                return "民盟盟员";
            case 6:
                return "民建会员";
            case 7:
                return "民进会员";
            case 8:
                return "农工党党员";
            case 9:
                return "致公党党员";
            case 10:
                return "九三学社社员";
            case 11:
                return "台盟盟员";
            case 12:
                return "无党派民主人士";
            case 13:
                return "群众";
        }
        return "-";
    }

    public String getSoldierMeritoriousStr() {
        if (soldierMeritorious == null) return "-";
        switch (soldierMeritorious) {
            case 1:
                return "一等功";
            case 2:
                return "二等功";
            case 3:
                return "三等功";
            case 0:
                return "无";
        }
        return "-";
    }

    public String getApplicantTypeStr() {
        if (applicantType == null) return "-";
        switch (applicantType) {
            case 1:
                return "企业员工";
            case 2:
                return "投资者";
            case 3:
                return "个体商户";
            case 4:
                return "事业单位";
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
     * 获取申请人类型，1、企业员工；2、投资者；3、个体工商户
     *
     * @return applicant_type - 申请人类型，1、企业员工；2、投资者；3、个体工商户
     */
    public Integer getApplicantType() {
        return applicantType;
    }

    /**
     * 设置申请人类型，1、企业员工；2、投资者；3、个体工商户
     *
     * @param applicantType 申请人类型，1、企业员工；2、投资者；3、个体工商户
     */
    public void setApplicantType(Integer applicantType) {
        this.applicantType = applicantType;
    }

    /**
     * 获取政治面貌，1、中共党员；2、中共预备党员；3、共青团员
     *
     * @return political_status - 政治面貌，1、中共党员；2、中共预备党员；3、共青团员
     */
    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    /**
     * 设置政治面貌，1、中共党员；2、中共预备党员；3、共青团员
     *
     * @param politicalStatus 政治面貌，1、中共党员；2、中共预备党员；3、共青团员
     */
    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    /**
     * 获取军人立功，0、无；1、一等功；2、二等功；3、三等功
     *
     * @return soldier_meritorious - 军人立功，0、无；1、一等功；2、二等功；3、三等功
     */
    public Integer getSoldierMeritorious() {
        return soldierMeritorious;
    }

    /**
     * 设置军人立功，0、无；1、一等功；2、二等功；3、三等功
     *
     * @param soldierMeritorious 军人立功，0、无；1、一等功；2、二等功；3、三等功
     */
    public void setSoldierMeritorious(Integer soldierMeritorious) {
        this.soldierMeritorious = soldierMeritorious;
    }

    /**
     * 获取文化程度
     *
     * @return culture_degree - 文化程度
     */
    public Integer getCultureDegree() {
        return cultureDegree;
    }

    /**
     * 设置文化程度
     *
     * @param cultureDegree 文化程度
     */
    public void setCultureDegree(Integer cultureDegree) {
        this.cultureDegree = cultureDegree;
    }

    /**
     * 获取学位
     *
     * @return degree - 学位
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * 设置学位
     *
     * @param degree 学位
     */
    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    /**
     * 获取专业
     *
     * @return profession - 专业
     */
    public String getProfession() {
        return profession;
    }

    /**
     * 设置专业
     *
     * @param profession 专业
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * 获取单位名称
     *
     * @return company_name - 单位名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置单位名称
     *
     * @param companyName 单位名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取单位地址
     *
     * @return company_address - 单位地址
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 设置单位地址
     *
     * @param companyAddress 单位地址
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * 获取单位电话
     *
     * @return company_phone - 单位电话
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * 设置单位电话
     *
     * @param companyPhone 单位电话
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    /**
     * 获取本人电话
     *
     * @return self_phone - 本人电话
     */
    public String getSelfPhone() {
        return selfPhone;
    }

    /**
     * 设置本人电话
     *
     * @param selfPhone 本人电话
     */
    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }

    /**
     * 获取居住证申领日期
     *
     * @return application_date - 居住证申领日期
     */
    public String getApplicationDate() {
        return applicationDate;
    }

    /**
     * 设置居住证申领日期
     *
     * @param applicationDate 居住证申领日期
     */
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * 获取是否缴纳社保, 1、是；2、否
     *
     * @return social_security_pay - 是否缴纳社保, 1、是；2、否
     */
    public Integer getSocialSecurityPay() {
        return socialSecurityPay;
    }

    /**
     * 设置是否缴纳社保, 1、是；2、否
     *
     * @param socialSecurityPay 是否缴纳社保, 1、是；2、否
     */
    public void setSocialSecurityPay(Integer socialSecurityPay) {
        this.socialSecurityPay = socialSecurityPay;
    }

    /**
     * 获取是否参加住房公积金, 1、是；2、否
     *
     * @return provident_fund - 是否参加住房公积金, 1、是；2、否
     */
    public Integer getProvidentFund() {
        return providentFund;
    }

    /**
     * 设置是否参加住房公积金, 1、是；2、否
     *
     * @param providentFund 是否参加住房公积金, 1、是；2、否
     */
    public void setProvidentFund(Integer providentFund) {
        this.providentFund = providentFund;
    }

    /**
     * 获取纳税情况, 1、是；2、否
     *
     * @return taxes - 纳税情况, 1、是；2、否
     */
    public Integer getTaxes() {
        return taxes;
    }

    /**
     * 设置纳税情况, 1、是；2、否
     *
     * @param taxes 纳税情况, 1、是；2、否
     */
    public void setTaxes(Integer taxes) {
        this.taxes = taxes;
    }

    /**
     * 获取拘留情况, 1、是；2、否
     *
     * @return detention - 拘留情况, 1、是；2、否
     */
    public Integer getDetention() {
        return detention;
    }

    /**
     * 设置拘留情况, 1、是；2、否
     *
     * @param detention 拘留情况, 1、是；2、否
     */
    public void setDetention(Integer detention) {
        this.detention = detention;
    }

    /**
     * 获取获刑情况, 1、是；2、否
     *
     * @return penalty - 获刑情况, 1、是；2、否
     */
    public Integer getPenalty() {
        return penalty;
    }

    /**
     * 设置获刑情况, 1、是；2、否
     *
     * @param penalty 获刑情况, 1、是；2、否
     */
    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    /**
     * 获取奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇
     *
     * @return awards_title - 奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇
     */
    public Integer getAwardsTitle() {
        return awardsTitle;
    }

    /**
     * 设置奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇
     *
     * @param awardsTitle 奖项荣誉称号,1、拥有有效的中国发明专利；2、获得党中央、国务院授予的奖项和荣誉称号；3、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的奖项和荣誉称号；4、获得省（自治区、直辖市）党委、政府或中央和国家机关部委等授予的劳动模范或先进工作者荣誉称号，并享受省部级劳动模范或先进工作者待遇
     */
    public void setAwardsTitle(Integer awardsTitle) {
        this.awardsTitle = awardsTitle;
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

    public Integer getIsAnotherInsurance() {
        return isAnotherInsurance;
    }

    public void setIsAnotherInsurance(Integer isAnotherInsurance) {
        this.isAnotherInsurance = isAnotherInsurance;
    }

    public Integer getIsApplyRegiste() {
        return isApplyRegiste;
    }

    public void setIsApplyRegiste(Integer isApplyRegiste) {
        this.isApplyRegiste = isApplyRegiste;
    }

    public Integer getIsInsurance() {
        return isInsurance;
    }

    public void setIsInsurance(Integer isInsurance) {
        this.isInsurance = isInsurance;
    }

    public Integer getIsSpouseInsurance() {
        return isSpouseInsurance;
    }

    public void setIsSpouseInsurance(Integer isSpouseInsurance) {
        this.isSpouseInsurance = isSpouseInsurance;
    }
}
