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
}
