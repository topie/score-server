package com.orange.score.module.score.controller.stat;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.excel.ExcelFileUtil;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.date.DateStyle;
import com.orange.score.common.utils.date.DateUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.module.score.service.IBatchConfService;
import com.orange.score.module.score.service.IIdentityInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/score/stat/export")
public class StatExportController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Value("${upload.folder}")
    private String uploadPath;

    @Autowired
    private IBatchConfService iBatchConfService;

    @GetMapping(value = "/list1")
    @ResponseBody
    public Result list1(@RequestParam("batchId") Integer batchId,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
        if (StringUtils.isNotEmpty(idNumber)) {
            argMap.put("idNumber", idNumber);
        }
        PageInfo<Map> pageInfo = iIdentityInfoService.selectExportList1ByPage(argMap, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/export1")
    @ResponseBody
    public void export1(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("batchId") Integer batchId,
            @RequestParam(value = "idNumber", required = false) String idNumber) throws Exception {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
        if (StringUtils.isNotEmpty(idNumber)) {
            argMap.put("idNumber", idNumber);
        }
        List<Map> allList = iIdentityInfoService.selectExportList1(argMap);
        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/" + System
                .currentTimeMillis() + ".xlsx";
        ExcelFileUtil.exportXlsx(savePath, allList,
                new String[] { "受理编号", "身份证号", "姓名", "受理日期", "性别", "文化程度", "现有职业资格", "工种", "单位名称", "单位电话", "本人电话",
                        "证书编号", "发证机关", "发证日期" },
                new String[] { "ACCEPT_NUMBER", "ID_NUMBER", "NAME", "RESERVATION_DATE", "SEX", "CULTURE_DEGREE",
                        "PROFESSION_TYPE", "JOB_TYPE", "COMPANY_NAME", "COMPANY_MOBILE", "SELF_PHONE",
                        "CERTIFICATE_CODE", "ISSUING_AUTHORITY", "ISSUING_DATE" });
        ExcelFileUtil.download(response, savePath, "列表1.xlsx");
    }

    @GetMapping(value = "/list2")
    @ResponseBody
    public Result list2(@RequestParam("batchId") Integer batchId,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
        if (StringUtils.isNotEmpty(idNumber)) {
            argMap.put("idNumber", idNumber);
        }
        PageInfo<Map> pageInfo = iIdentityInfoService.selectExportList2ByPage(argMap, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/export2")
    @ResponseBody
    public void export2(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("batchId") Integer batchId,
            @RequestParam(value = "idNumber", required = false) String idNumber) throws Exception {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
        if (StringUtils.isNotEmpty(idNumber)) {
            argMap.put("idNumber", idNumber);
        }
        List<Map> allList = iIdentityInfoService.selectExportList2(argMap);
        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/" + System
                .currentTimeMillis() + ".xlsx";
        ExcelFileUtil.exportXlsx(savePath, allList,
                new String[] { "受理编号", "申请人类型", "姓名", "身份证号", "职业资格", "工种", "专业", "拟落户地区", "户口所在地" },
                new String[] { "ACCEPT_NUMBER", "APPLICANT_TYPE", "NAME", "ID_NUMBER", "PROFESSION_TYPE", "JOB_TYPE",
                        "PROFESSION", "REGION", "MOVE_REGISTERED_OFFICE" });
        ExcelFileUtil.download(response, savePath, "列表2.xlsx");
    }

    @GetMapping(value = "/list3")
    @ResponseBody
    public Result list3(@RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        BatchConf batchConf = new BatchConf();
        batchConf.setStatus(1);
        List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
        if (list.size() > 0) {
            argMap.put("batchId", list.get(0).getId());
        }
        if (StringUtils.isNotEmpty(period)) {
            String[] arr = period.split(" 到 ");
            argMap.put("acceptDateBegin", arr[0]);
            argMap.put("acceptDateEnd", arr[1]);
        }
        if (StringUtils.isNotEmpty(personName)) {
            argMap.put("personName", personName);
        }
        if (StringUtils.isNotEmpty(personIdNum)) {
            argMap.put("personIdNum", personIdNum);
        }
        if (StringUtils.isNotEmpty(companyName)) {
            argMap.put("companyName", companyName);
        }
        if (StringUtils.isNotEmpty(indicatorName)) {
            argMap.put("indicatorName", indicatorName);
        }
        if (acceptAddressId != null) {
            argMap.put("acceptAddressId", acceptAddressId);
        }
        String[] titles = new String[] { "区县名称", "部门名称", "申报批次名称", "身份证号", "姓名", "单位名称", "评分指标名称", "得分值", "状态", "受理人",
                "受理时间", "送达人", "送达时间", "打分人", "打分时间" };
        String[] fields = new String[] { "ACCEPT_ADDRESS", "OP_ROLE", "BATCH_NAME", "PERSON_ID_NUM", "PERSON_NAME",
                "COMPANY_NAME", "INDICATOR_NAME", "SCORE_VALUE", "STATUS", "ACCEPT_PERSON", "ACCEPT_DATE", "OP_USER",
                "SUBMIT_DATE", "OP_USER", "SCORE_DATE" };
        List<Map> columns = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map column = new HashMap();
            column.put("title", titles[i]);
            column.put("field", fields[i]);
            columns.add(column);
        }
        PageInfo<Map> pageInfo = iIdentityInfoService.selectExportList3ByPage(argMap, pageNum, pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map map : pageInfo.getList()) {
            /*
            2018年11月9日 xgr
            因浏览器端显示的时间有8个小时的时差，所以格式化一下
             */
            if(map.get("ACCEPT_DATE") != null){
                map.put("ACCEPT_DATE", sdf.format(map.get("ACCEPT_DATE")));
            }
            if (map.get("SUBMIT_DATE") != null){
                map.put("SUBMIT_DATE", sdf.format(map.get("SUBMIT_DATE")));
            }
            if(map.get("SCORE_DATE") != null){
                map.put("SCORE_DATE", sdf.format(map.get("SCORE_DATE")));
            }
            if(map.get("SCORE_VALUE") != null){
                map.put("SCORE_VALUE", ((BigDecimal) map.get("SCORE_VALUE")).setScale(2));
            }
            Integer status = ((BigDecimal) map.get("STATUS")).intValue();
            switch (status) {
                case 1:
                    map.put("STATUS", "材料未提交");
                    break;
                case 2:
                    map.put("STATUS", "材料未提交");
                    break;
                case 3:
                    map.put("STATUS", "材料已提交");
                    break;
                case 4:
                    map.put("STATUS", "已打分");
                    break;
                default:
                    map.put("STATUS", "未打分");
            }
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo, columns));
    }

    @GetMapping(value = "/export3")
    @ResponseBody
    public void export3(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId) throws Exception {
        Map argMap = new HashMap();
        BatchConf batchConf = new BatchConf();
        batchConf.setStatus(1);
        List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
        if (list.size() > 0) {
            argMap.put("batchId", list.get(0).getId());
        }
        if (StringUtils.isNotEmpty(period)) {
            String[] arr = period.split(" 到 ");
            argMap.put("acceptDateBegin", arr[0]);
            argMap.put("acceptDateEnd", arr[1]);
        }
        if (StringUtils.isNotEmpty(personName)) {
            argMap.put("personName", personName);
        }
        if (StringUtils.isNotEmpty(personIdNum)) {
            argMap.put("personIdNum", personIdNum);
        }
        if (StringUtils.isNotEmpty(companyName)) {
            argMap.put("companyName", companyName);
        }
        if (StringUtils.isNotEmpty(indicatorName)) {
            argMap.put("indicatorName", indicatorName);
        }
        if (acceptAddressId != null) {
            argMap.put("acceptAddressId", acceptAddressId);
        }
        List<Map> allList = iIdentityInfoService.selectExportList3(argMap);
        for (Map map : allList) {
            Integer status = ((BigDecimal) map.get("STATUS")).intValue();
            switch (status) {
                case 1:
                    map.put("STATUS", "材料未提交");
                    break;
                case 2:
                    map.put("STATUS", "材料未提交");
                    break;
                case 3:
                    map.put("STATUS", "材料已提交");
                    break;
                case 4:
                    map.put("STATUS", "已打分");
                    break;
                default:
                    map.put("STATUS", "未打分");
            }
        }
        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/" + System
                .currentTimeMillis() + ".xlsx";
        ExcelFileUtil.exportXlsx(savePath, allList,
                new String[] { "区县名称", "部门名称", "申报批次名称", "身份证号", "姓名", "单位名称", "评分指标名称", "得分值", "状态", "受理人", "受理时间",
                        "送达人", "送达时间", "打分人", "打分时间" },
                new String[] { "ACCEPT_ADDRESS", "OP_ROLE", "BATCH_NAME", "PERSON_ID_NUM", "PERSON_NAME",
                        "COMPANY_NAME", "INDICATOR_NAME", "SCORE_VALUE", "STATUS", "ACCEPT_PERSON", "ACCEPT_DATE",
                        "OP_USER", "SUBMIT_DATE", "OP_USER", "SCORE_DATE" });
        ExcelFileUtil.download(response, savePath, "列表3.xlsx");
    }

    @GetMapping(value = "/list4")
    @ResponseBody
    public Result list4(@RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        BatchConf batchConf = new BatchConf();
        batchConf.setStatus(1);
        List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
        if (list.size() > 0) {
            argMap.put("batchId", list.get(0).getId());
        }
        if (StringUtils.isNotEmpty(period)) {
            String[] arr = period.split(" 到 ");
            argMap.put("acceptDateBegin", arr[0]);
            argMap.put("acceptDateEnd", arr[1]);
        }
        if (StringUtils.isNotEmpty(personName)) {
            argMap.put("personName", personName);
        }
        if (StringUtils.isNotEmpty(personIdNum)) {
            argMap.put("personIdNum", personIdNum);
        }
        if (StringUtils.isNotEmpty(companyName)) {
            argMap.put("companyName", companyName);
        }
        if (acceptAddressId != null) {
            argMap.put("acceptAddressId", acceptAddressId);
        }
        String[] titles = new String[] { "受理编码", "身份证号码", "姓名", "本人电话", "受理日期", "受理人", "实际交件日期", "打包位置", "特殊记录", "分类",
                "备注1", "备注2", "性别", "配偶姓名", "配偶身份证号码", "文化程度", "现有职业（专业/职业）资格级别", "工种名称", "证书编码", "发证机关", "发证日期",
                "单位名称", "单位电话", "经办人姓名", "经办人电话" };
        String[] fields = new String[] { "ACCEPT_NUMBER", "ID_NUMBER", "NAME", "SELF_PHONE", "ACCEPT_DATE", "OPUSER4",
                "BLANK1", "BLANK2", "BLANK3", "BLANK4", "BLANK5", "BLANK6", "SEX", "PARTNER_NAME", "PARTNER_ID_NUMBER",
                "CULTURE_DEGREE", "JOB_LEVEL", "JOB_NAME", "CERTIFICATE_CODE", "ISSUING_AUTHORITY", "ISSUING_DATE",
                "COMPANY_NAME", "COMPANY_MOBILE", "OPERATOR", "OPERATOR_MOBILE" };
        List<Map> columns = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map column = new HashMap();
            column.put("title", titles[i]);
            column.put("field", fields[i]);
            columns.add(column);
        }
        PageInfo<Map> pageInfo = iIdentityInfoService.selectExportList4ByPage(argMap, pageNum, pageSize);
        for (Map map : pageInfo.getList()) {
            Integer sex = ((BigDecimal) map.get("SEX")).intValue();
            if (sex == 1) {
                map.put("SEX", "男");
            } else {
                map.put("SEX", "女");
            }
            Integer cd = ((BigDecimal) map.get("CULTURE_DEGREE")).intValue();
            switch (cd) {
                case 11:
                    map.put("CULTURE_DEGREE", "无");
                    break;
                case 4:
                    map.put("CULTURE_DEGREE", "本科及以上学历");
                    break;
                case 5:
                    map.put("CULTURE_DEGREE", "大专学历");
                    break;
                case 6:
                    map.put("CULTURE_DEGREE", "高级技工学校高级班");
                    break;
                default:
                    map.put("CULTURE_DEGREE", "无");
            }

            Integer pt = ((BigDecimal) map.get("PROFESSION_TYPE")).intValue();
            Integer jobName = ((BigDecimal) map.get("JOB_NAME")).intValue();
            Integer jobLevel = ((BigDecimal) map.get("JOB_LEVEL")).intValue();
            Integer jobTitleLevel = ((BigDecimal) map.get("JOB_TITLE_LEVEL")).intValue();
            Integer professionTitle = ((BigDecimal) map.get("PROFESSION_TITLE")).intValue();
            switch (pt) {
                case 1:
                    map.put("PROFESSION_TYPE", "无");
                    map.put("JOB_LEVEL", "");
                    map.put("JOB_NAME", "");
                    break;
                case 2:
                    map.put("PROFESSION_TYPE", "具有职称");
                    map.put("JOB_LEVEL", getJobTitleLevelStr(jobTitleLevel));
                    map.put("JOB_NAME", getProfessionTitleStr(professionTitle));
                    break;
                case 3:
                    map.put("PROFESSION_TYPE", "具有职业资格");
                    map.put("JOB_LEVEL", getJobLevelStr(jobLevel));
                    map.put("JOB_NAME", getJobNameStr(jobName));
                    break;
                default:
                    map.put("PROFESSION_TYPE", "无");
                    map.put("JOB_LEVEL", "");
                    map.put("JOB_NAME", "");
            }
            Integer jt = ((BigDecimal) map.get("JOB_TYPE")).intValue();
            switch (jt) {
                case 27:
                    map.put("JOB_TYPE", "非常紧缺的职业");
                    break;
                case 28:
                    map.put("JOB_TYPE", "紧缺职业");
                    break;
                case 29:
                    map.put("JOB_TYPE", "一般紧缺职业");
                    break;
                case 30:
                    map.put("JOB_TYPE", "无");
                    break;
                default:
                    map.put("JOB_TYPE", "无");
            }
            Date acceptDate = (Date) map.get("ACCEPT_DATE");
            map.put("ACCEPT_DATE", DateUtil.DateToString(acceptDate, DateStyle.YYYY_MM_DD));
        }
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo, columns));
    }

    private String getProfessionTitleStr(Integer professionTitle) {
        if (professionTitle == null) return "";
        switch (professionTitle) {
            case 1: {
                return "注册消防工程师";
            }
            case 2: {
                return "注册核安全工程师";
            }
            case 3: {
                return "注册建筑师";
            }
            case 4: {
                return "监理工程师";
            }
            case 5: {
                return "房地产估价师";

            }
            case 6: {
                return "造价工程师";

            }
            case 7: {
                return "注册城乡规划师";

            }
            case 8: {
                return "建造师";

            }
            case 9: {
                return "勘察设计注册工程师";

            }
            case 10: {
                return "注册验船师";

            }
            case 11: {
                return "拍卖师";

            }
            case 12: {
                return "医生资格";

            }
            case 13: {
                return "护士执业资格";

            }
            case 14: {
                return "注册设备监理师";

            }
            case 15: {
                return "注册计量师";

            }
            case 16: {
                return "注册安全工程师";

            }
            case 17: {
                return "执业药师";

            }
            case 18: {
                return "注册测绘师";

            }
            case 19: {
                return "工程咨询（投资）专业技术人员职业资格";

            }
            case 20: {
                return "通信专业技术人员职业资格";

            }
            case 21: {
                return "计算机技术与软件专业技术资格";

            }
            case 22: {
                return "社会工作者职业资格";

            }
            case 23: {
                return "会计专业技术资格";

            }
            case 24: {
                return "资产评估师";

            }
            case 25: {
                return "经济专业技术资格";

            }
            case 26: {
                return "土地登记代理专业人员职业资格";

            }
            case 27: {
                return "环境影响评价工程师";

            }
            case 28: {
                return "房地产经纪专业人员职业资格";

            }
            case 29: {
                return "机动车检测维修专业技术人员职业资格";

            }
            case 30: {
                return "公路水运工程试验检测专业技术人员职业资格";

            }
            case 31: {
                return "卫生专业技术资格";

            }
            case 32: {
                return "审计专业技术资格";

            }
            case 33: {
                return "税务师";

            }
            case 34: {
                return "出版专业技术人员职业资格";

            }
            case 35: {
                return "统计专业技术资格";

            }
            case 36: {
                return "银行业专业人员职业资格";

            }
            case 37: {
                return "翻译专业资格";

            }
            default: {
                return "";
            }
        }
    }

    private String getJobTitleLevelStr(Integer jobTitleLevel) {
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

    private String getJobLevelStr(Integer jobLevel) {
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

    private String getJobNameStr(Integer jobName) {
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

    @GetMapping(value = "/export4")
    @ResponseBody
    public void export4(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId) throws Exception {
        Map argMap = new HashMap();
        BatchConf batchConf = new BatchConf();
        batchConf.setStatus(1);
        List<BatchConf> list = iBatchConfService.selectByFilter(batchConf);
        if (list.size() > 0) {
            argMap.put("batchId", list.get(0).getId());
        }
        if (StringUtils.isNotEmpty(period)) {
            String[] arr = period.split(" 到 ");
            argMap.put("acceptDateBegin", arr[0]);
            argMap.put("acceptDateEnd", arr[1]);
        }
        if (StringUtils.isNotEmpty(personName)) {
            argMap.put("personName", personName);
        }
        if (StringUtils.isNotEmpty(personIdNum)) {
            argMap.put("personIdNum", personIdNum);
        }
        if (StringUtils.isNotEmpty(companyName)) {
            argMap.put("companyName", companyName);
        }
        if (acceptAddressId != null) {
            argMap.put("acceptAddressId", acceptAddressId);
        }
        List<Map> allList = iIdentityInfoService.selectExportList4(argMap);
        for (Map map : allList) {
            Integer sex = ((BigDecimal) map.get("SEX")).intValue();
            if (sex == 1) {
                map.put("SEX", "男");
            } else {
                map.put("SEX", "女");
            }
            Integer cd = ((BigDecimal) map.get("CULTURE_DEGREE")).intValue();
            switch (cd) {
                case 11:
                    map.put("CULTURE_DEGREE", "无");
                    break;
                case 4:
                    map.put("CULTURE_DEGREE", "本科及以上学历");
                    break;
                case 5:
                    map.put("CULTURE_DEGREE", "大专学历");
                    break;
                case 6:
                    map.put("CULTURE_DEGREE", "高级技工学校高级班");
                    break;
                default:
                    map.put("CULTURE_DEGREE", "无");
            }

            Integer pt = ((BigDecimal) map.get("PROFESSION_TYPE")).intValue();
            Integer jobName = ((BigDecimal) map.get("JOB_NAME")).intValue();
            Integer jobLevel = ((BigDecimal) map.get("JOB_LEVEL")).intValue();
            Integer jobTitleLevel = ((BigDecimal) map.get("JOB_TITLE_LEVEL")).intValue();
            Integer professionTitle = ((BigDecimal) map.get("PROFESSION_TITLE")).intValue();
            switch (pt) {
                case 1:
                    map.put("PROFESSION_TYPE", "无");
                    map.put("JOB_LEVEL", "");
                    map.put("JOB_NAME", "");
                    break;
                case 2:
                    map.put("PROFESSION_TYPE", "具有职称");
                    map.put("JOB_LEVEL", getJobTitleLevelStr(jobTitleLevel));
                    map.put("JOB_NAME", getProfessionTitleStr(professionTitle));
                    break;
                case 3:
                    map.put("PROFESSION_TYPE", "具有职业资格");
                    map.put("JOB_LEVEL", getJobLevelStr(jobLevel));
                    map.put("JOB_NAME", getJobNameStr(jobName));
                    break;
                default:
                    map.put("PROFESSION_TYPE", "无");
                    map.put("JOB_LEVEL", "");
                    map.put("JOB_NAME", "");
            }
            Integer jt = ((BigDecimal) map.get("JOB_TYPE")).intValue();
            switch (jt) {
                case 27:
                    map.put("JOB_TYPE", "非常紧缺的职业");
                    break;
                case 28:
                    map.put("JOB_TYPE", "紧缺职业");
                    break;
                case 29:
                    map.put("JOB_TYPE", "一般紧缺职业");
                    break;
                case 30:
                    map.put("JOB_TYPE", "无");
                    break;
                default:
                    map.put("JOB_TYPE", "无");
            }
            Date acceptDate = (Date) map.get("ACCEPT_DATE");
            map.put("ACCEPT_DATE", DateUtil.DateToString(acceptDate, DateStyle.YYYY_MM_DD));
        }
        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/" + System
                .currentTimeMillis() + ".xlsx";
        ExcelFileUtil.exportXlsx(savePath, allList,
                new String[] { "受理编码", "身份证号码", "姓名", "本人电话", "受理日期", "受理人", "实际交件日期", "打包位置", "特殊记录", "分类", "备注1",
                        "备注2", "性别", "配偶姓名", "配偶身份证号码", "文化程度", "现有职业（专业/职业）资格级别", "工种名称", "证书编码", "发证机关", "发证日期",
                        "单位名称", "单位电话", "经办人姓名", "经办人电话" },
                new String[] { "ACCEPT_NUMBER", "ID_NUMBER", "NAME", "SELF_PHONE", "ACCEPT_DATE", "OPUSER4", "BLANK1",
                        "BLANK2", "BLANK3", "BLANK4", "BLANK5", "BLANK6", "SEX", "PARTNER_NAME", "PARTNER_ID_NUMBER",
                        "CULTURE_DEGREE", "JOB_LEVEL", "JOB_NAME", "CERTIFICATE_CODE", "ISSUING_AUTHORITY",
                        "ISSUING_DATE", "COMPANY_NAME", "COMPANY_MOBILE", "OPERATOR", "OPERATOR_MOBILE" });
        ExcelFileUtil.download(response, savePath, "列表4.xlsx");
    }

}
