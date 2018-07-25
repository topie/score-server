package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_house_profession")
public class HouseProfession {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_house_profession_seq.nextval from dual")
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

    @Column(name = "job_name")
    private Integer jobName;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    @Transient
    public String getJobNameStr() {
        if (jobName == null) return "";
        switch (jobName) {
            case 1: {
                return "消防设施操作员";
            }
            case 2: {
                return "焊工";
                
            }
            case 3: {
                return "家畜繁殖员";
                
            }
            case 4: {
                return "游泳救生员";
                
            }
            case 5: {
                return "社会体育指导员";
                
            }
            case 6: {
                return "轨道列车司机";
                
            }
            case 7: {
                return "设备点检员";
                
            }
            case 8: {
                return "电工";
                
            }
            case 9: {
                return "锅炉设备检修工";
                
            }
            case 10: {
                return "变电设备检修工";
                
            }
            case 11: {
                return "工程机械维修工";
                
            }
            case 12: {
                return "起重装卸机械操作工";
                
            }
            case 13: {
                return "电梯安装维修工";
                
            }
            case 14: {
                return "制冷空调系统安装维修工";
                
            }
            case 15: {
                return "筑路工";
                
            }
            case 16: {
                return "桥隧工";
                
            }
            case 17: {
                return "防水工";
                
            }
            case 18: {
                return "电力电缆安装运维工";
                
            }
            case 19: {
                return "砌筑工";
                
            }
            case 20: {
                return "混凝土工";
                
            }
            case 21: {
                return "钢筋工";
                
            }
            case 22: {
                return "架子工";
                
            }
            case 23: {
                return "水生产处理工";
                
            }
            case 24: {
                return "工业废水处理工";
                
            }
            case 25: {
                return "工业气体生产工";
                
            }
            case 26: {
                return "工业废气治理工";
                
            }
            case 27: {
                return "压缩机操作工";
                
            }
            case 28: {
                return "锅炉运行值班员";
                
            }
            case 29: {
                return "发电集控值班员";
                
            }
            case 30: {
                return "变配电运行值班员";
                
            }
            case 31: {
                return "继电保护员";
                
            }
            case 32: {
                return "燃气轮机值班员";
                
            }
            case 33: {
                return "锅炉操作工";
                
            }
            case 34: {
                return "钟表及计时仪器制造工";
                
            }
            case 35: {
                return "广电和通信设备电子装接工";
                
            }
            case 36: {
                return "广电和通信设备调试工";
                
            }
            case 37: {
                return "计算机及外部设备装配调试员";
                
            }
            case 38: {
                return "液晶显示器件制造工";
                
            }
            case 39: {
                return "半导体芯片制造工";
                
            }
            case 40: {
                return "半导体分立器件和集成电路装调工";
                
            }
            case 41: {
                return "电子产品制版工";
                
            }
            case 42: {
                return "印制电路制作工";
                
            }
            case 43: {
                return "电线电缆制造工";
                
            }
            case 44: {
                return "变压器互感器制造工";
                
            }
            case 45: {
                return "高低压电器及成套设备装配工";
                
            }
            case 46: {
                return "汽车装调工";
                
            }
            case 47: {
                return "矫形器装配工";
                
            }
            case 48: {
                return "假肢装配工";
                
            }
            case 49: {
                return "机床装调维修工";
                
            }
            case 50: {
                return "模具工";
                
            }
            case 51: {
                return "铸造工";
                
            }
            case 52: {
                return "锻造工";
                
            }
            case 53: {
                return "金属热处理工";
                
            }
            case 54: {
                return "车工";
                
            }
            case 55: {
                return "铣工";
                
            }
            case 56: {
                return "钳工";
                
            }
            case 57: {
                return "磨工";
                
            }
            case 58: {
                return "冲压工";
                
            }
            case 59: {
                return "电切削工";
                
            }
            case 60: {
                return "硬质合金成型工";
                
            }
            case 61: {
                return "硬质合金烧结工";
                
            }
            case 62: {
                return "硬质合金精加工工";
                
            }
            case 63: {
                return "轧制原料工";
                
            }
            case 64: {
                return "金属轧制工";
                
            }
            case 65: {
                return "金属材热处理工";
                
            }
            case 66: {
                return "金属材精整工";
                
            }
            case 67: {
                return "金属挤压工";
                
            }
            case 68: {
                return "铸轧工";
                
            }
            case 69: {
                return "氧化铝制取工";
                
            }
            case 70: {
                return "铝电解工";
                
            }
            case 71: {
                return "重冶火法冶炼工";
                
            }
            case 72: {
                return "电解精炼工";
                
            }
            case 73: {
                return "重冶湿法冶炼工";
                
            }
            case 74: {
                return "炼钢原料工";
                
            }
            case 75: {
                return "炼钢工";
                
            }
            case 76: {
                return "高炉原料工";
                
            }
            case 77: {
                return "高炉炼铁工";
                
            }
            case 78: {
                return "高炉运转工";
                
            }
            case 79: {
                return "井下支护工";
                
            }
            case 80: {
                return "矿山救护工";
                
            }
            case 81: {
                return "陶瓷原料准备工";
                
            }
            case 82: {
                return "陶瓷烧成工";
                
            }
            case 83: {
                return "陶瓷装饰工";
                
            }
            case 84: {
                return "玻璃纤维及制品工";
                
            }
            case 85: {
                return "玻璃钢制品工";
                
            }
            case 86: {
                return "水泥生产工";
                
            }
            case 87: {
                return "石膏制品生产工";
                
            }
            case 88: {
                return "水泥混凝土制品工";
                
            }
            case 89: {
                return "药物制剂工";
                
            }
            case 90: {
                return "中药炮制工";
                
            }
            case 91: {
                return "涂料生产工";
                
            }
            case 92: {
                return "染料生产工";
                
            }
            case 93: {
                return "农药生产工";
                
            }
            case 94: {
                return "合成氨生产工";
                
            }
            case 95: {
                return "尿素生产工";
                
            }
            case 96: {
                return "硫酸生产工";
                
            }
            case 97: {
                return "硝酸生产工";
                
            }
            case 98: {
                return "纯碱生产工";
                
            }
            case 99: {
                return "烧碱生产工";
                
            }
            case 100: {
                return "无机化学反应生产工";
                
            }
            case 101: {
                return "有机合成工";
                
            }
            case 102: {
                return "化工总控工";
                
            }
            case 103: {
                return "防腐蚀工";
                
            }
            case 104: {
                return "制冷工";
                
            }
            case 105: {
                return "炼焦煤制备工";
                
            }
            case 106: {
                return "炼焦工";
                
            }
            case 107: {
                return "景泰蓝制作工";
                
            }
            case 108: {
                return "手工木工";
                
            }
            case 109: {
                return "服装制版师";
                
            }
            case 110: {
                return "印染前处理工";
                
            }
            case 111: {
                return "印花工";
                
            }
            case 112: {
                return "印染后整理工";
                
            }
            case 113: {
                return "印染染化料配制工";
                
            }
            case 114: {
                return "纺织染色工";
                
            }
            case 115: {
                return "整经工";
                
            }
            case 116: {
                return "织布工";
                
            }
            case 117: {
                return "纺纱工";
                
            }
            case 118: {
                return "缫丝工";
                
            }
            case 119: {
                return "纺织纤维梳理工";
                
            }
            case 120: {
                return "并条工";
                
            }
            case 121: {
                return "酿酒师";
                
            }
            case 122: {
                return "品酒师";
                
            }
            case 123: {
                return "酒精酿造工";
                
            }
            case 124: {
                return "白酒酿造工";
                
            }
            case 125: {
                return "啤酒酿造工";
                
            }
            case 126: {
                return "黄酒酿造工";
                
            }
            case 127: {
                return "果露酒酿造工";
                
            }
            case 128: {
                return "评茶员";
                
            }
            case 129: {
                return "乳品评鉴师";
                
            }
            case 130: {
                return "制米工";
                
            }
            case 131: {
                return "制粉工";
                
            }
            case 132: {
                return "制油工";
                
            }
            case 133: {
                return "农作物植保员";
                
            }
            case 134: {
                return "动物疫病防治员";
                
            }
            case 135: {
                return "动物检疫检验员";
                
            }
            case 136: {
                return "水生物病害防治员";
                
            }
            case 137: {
                return "林业有害生物防治员";
                
            }
            case 138: {
                return "农机修理工";
                
            }
            case 139: {
                return "沼气工";
                
            }
            case 140: {
                return "农业技术员";
                
            }
            case 141: {
                return "助听器验配师";
                
            }
            case 142: {
                return "口腔修复体制作工";
                
            }
            case 143: {
                return "眼镜验光员";
                
            }
            case 144: {
                return "眼镜定配工";
                
            }
            case 145: {
                return "健康管理师";
                
            }
            case 146: {
                return "生殖健康咨询师";
                
            }
            case 147: {
                return "信息通信网络终端维修员";
                
            }
            case 148: {
                return "汽车维修工";
                
            }
            case 149: {
                return "保健调理师";
                
            }
            case 150: {
                return "美容师";
                
            }
            case 151: {
                return "美发师";
                
            }
            case 152: {
                return "孤残儿童护理员";
                
            }
            case 153: {
                return "育婴员";
                
            }
            case 154: {
                return "保育员";
                
            }
            case 155: {
                return "有害生物防制员";
                
            }
            case 156: {
                return "工业固体废物处理处置工";
                
            }
            case 157: {
                return "水文勘测工";
                
            }
            case 158: {
                return "河道修防工";
                
            }
            case 159: {
                return "水工闸门运行工";
                
            }
            case 160: {
                return "水工监测工";
                
            }
            case 161: {
                return "地勘钻探工";
                
            }
            case 162: {
                return "地质调查员";
                
            }
            case 163: {
                return "地勘掘进工";
                
            }
            case 164: {
                return "地质实验员";
                
            }
            case 165: {
                return "物探工";
                
            }
            case 166: {
                return "农产品食品检验员";
                
            }
            case 167: {
                return "纤维检验员";
                
            }
            case 168: {
                return "贵金属首饰与宝玉石检测员";
                
            }
            case 169: {
                return "机动车检测工";
                
            }
            case 170: {
                return "大地测量员";
                
            }
            case 171: {
                return "摄影测量员";
                
            }
            case 172: {
                return "地图绘制员";
                
            }
            case 173: {
                return "不动产测绘员";
                
            }
            case 174: {
                return "工程测量员";
                
            }
            case 175: {
                return "保安员";
                
            }
            case 176: {
                return "安检员";
                
            }
            case 177: {
                return "智能楼宇管理员";
                
            }
            case 178: {
                return "安全评价师";
                
            }
            case 179: {
                return "劳动关系协调员";
                
            }
            case 180: {
                return "企业人力资源管理师";
                
            }
            case 181: {
                return "中央空调系统运行操作员";
                
            }
            case 182: {
                return "信息通信网络运行管理员";
                
            }
            case 183: {
                return "广播电视天线工";
                
            }
            case 184: {
                return "有线广播电视机线员";
                
            }
            case 185: {
                return "信息通信网络机务员";
                
            }
            case 186: {
                return "信息通信网络线务员";
                
            }
            case 187: {
                return "中式烹调师";
                
            }
            case 188: {
                return "中式面点师";
                
            }
            case 189: {
                return "西式烹调师";
                
            }
            case 190: {
                return "西式面点师";
                
            }
            case 191: {
                return "茶艺师";
                
            }
            case 192: {
                return "（粮油）仓储管理员";
                
            }
            case 193: {
                return "民航乘务员";
                
            }
            case 194: {
                return "机场运行指挥员";
                
            }
            case 195: {
                return "机动车驾驶教练员";
                
            }
            case 196: {
                return "消防员";
                
            }
            case 197: {
                return "森林消防员";
                
            }
            case 198: {
                return "应急救援员";
                
            }
        }
        return "";
    }

    public Integer getJobName() {
        return jobName;
    }

    public void setJobName(Integer jobName) {
        this.jobName = jobName;
    }

    public String getJobTypeStr() {
        switch (jobType) {
            case 27:
                return "非常紧缺的职业";
            case 28:
                return "紧缺职业";
            case 29:
                return "一般紧缺职业";
            case 30:
                return "无";
        }
        return "无";
    }

    public String getJobLevelStr() {
        switch (jobLevel) {
            case 19:
                return "高级技师";
            case 20:
                return "技师";
            case 21:
                return "高级工";
            case 22:
                return "中级工";
            case 23:
                return "初级工";
        }
        return "无";
    }

    public String getJobTitleLevelStr() {
        switch (jobTitleLevel) {
            case 1:
                return "初级职称";
            case 2:
                return "中级职称";
            case 3:
                return "高级职称";
        }
        return "无";
    }

    public String getProfessionTypeStr() {
        switch (professionType) {
            case 1:
                return "无";
            case 2:
                return "具有职称";
            case 3:
                return "具有职业资格";
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
