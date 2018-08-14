package com.orange.score.module.score.controller.stat;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.tools.excel.ExcelFileUtil;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.module.score.service.IIdentityInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/score/stat/export")
public class StatExportController {

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Value("${upload.folder}")
    private String uploadPath;

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
    public Result list3(@RequestParam(value = "batchId", required = false) Integer batchId,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
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
        for (Map map : pageInfo.getList()) {
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
            @RequestParam(value = "batchId", required = false) Integer batchId,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId) throws Exception {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
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
    public Result list4(@RequestParam(value = "batchId", required = false) Integer batchId,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
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
        for (Map map : pageInfo.getList()) {
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

    @GetMapping(value = "/export4")
    @ResponseBody
    public void export4(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "batchId", required = false) Integer batchId,
            @RequestParam(value = "personName", required = false) String personName,
            @RequestParam(value = "personIdNum", required = false) String personIdNum,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "indicatorName", required = false) String indicatorName,
            @RequestParam(value = "acceptAddressId", required = false) Integer acceptAddressId) throws Exception {
        Map argMap = new HashMap();
        argMap.put("batchId", batchId);
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
        String savePath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/" + System
                .currentTimeMillis() + ".xlsx";
        ExcelFileUtil.exportXlsx(savePath, allList,
                new String[] { "受理编码", "身份证号码", "姓名", "受理日期", "实际交件日期", "打包位置", "特殊记录", "分类", "备注1", "备注2", "性别",
                        "配偶姓名", "配偶身份证号码", "文化程度", "现有职业（专业/职业）资格级别", "工种名称", "证书编码", "发证机关", "发证日期", "单位名称", "单位电话",
                        "经办人姓名", "本人电话", "受理经办人姓名" },
                new String[] { "accept_number", "id_number", "name" });
        ExcelFileUtil.download(response, savePath, "列表3.xlsx");
    }

}
