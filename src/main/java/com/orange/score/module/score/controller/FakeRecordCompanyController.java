package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.database.score.model.FakeRecordCompany;
import com.orange.score.database.score.model.FakeRecordHistory;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IFakeRecordCompanyService;
import com.orange.score.module.score.service.IFakeRecordHistoryService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by rui15 on 2019/2/27.
 */
@RestController
@RequestMapping("/api/score/fakeRecordCompany")
public class FakeRecordCompanyController {

    @Autowired
    private IFakeRecordCompanyService iFakeRecordCompanyService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IFakeRecordHistoryService iFakeRecordHistoryService;



    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_fake_record_company");
        return ResponseUtil.success(list);
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(FakeRecordCompany fakeRecordCompany,
                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<FakeRecordCompany> pageInfo = iFakeRecordCompanyService.selectByFilterAndPage(fakeRecordCompany, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/insert")
    public Result insert(FakeRecordCompany fakeRecordCompany) {
        iFakeRecordCompanyService.save(fakeRecordCompany);

        FakeRecordHistory fakeRecordHistory = new FakeRecordHistory();
        if (fakeRecordCompany.getCompanyName() != null){
            fakeRecordHistory.setName(fakeRecordCompany.getCompanyName());
        }
        if (fakeRecordCompany.getBatchId() != null){
            fakeRecordHistory.setBatchId(fakeRecordCompany.getBatchId());
        }
        if (fakeRecordCompany.getFakeContent() !=null){
            fakeRecordHistory.setContent(fakeRecordCompany.getFakeContent());
        }
        if (fakeRecordCompany.getCompanyUsid() != null){
            fakeRecordHistory.setIdCode(fakeRecordCompany.getCompanyUsid());
        }
        fakeRecordHistory.setCreateTime(new Date());
        fakeRecordHistory.setHandle("录入");
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        fakeRecordHistory.setOperator(securityUser.getLoginName());//登录名

        iFakeRecordHistoryService.save(fakeRecordHistory);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        FakeRecordCompany fakeRecordCompany = iFakeRecordCompanyService.findById(id);
        return ResponseUtil.success(fakeRecordCompany);
    }

    @PostMapping("/update")
    public Result update(FakeRecordCompany fakeRecordCompany) {
        iFakeRecordCompanyService.update(fakeRecordCompany);
        FakeRecordHistory fakeRecordHistory = new FakeRecordHistory();
        if (fakeRecordCompany.getCompanyName() != null){
            fakeRecordHistory.setName(fakeRecordCompany.getCompanyName());
        }
        if (fakeRecordCompany.getBatchId() != null){
            fakeRecordHistory.setBatchId(fakeRecordCompany.getBatchId());
        }
        if (fakeRecordCompany.getFakeContent() !=null){
            fakeRecordHistory.setContent(fakeRecordCompany.getFakeContent());
        }
        if (fakeRecordCompany.getCompanyUsid() != null){
            fakeRecordHistory.setIdCode(fakeRecordCompany.getCompanyUsid());
        }
        fakeRecordHistory.setCreateTime(new Date());
        fakeRecordHistory.setHandle("修改");
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        fakeRecordHistory.setOperator(securityUser.getLoginName());//登录名
        iFakeRecordHistoryService.save(fakeRecordHistory);
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        FakeRecordCompany fakeRecordCompany = iFakeRecordCompanyService.findById(id);
        iFakeRecordCompanyService.deleteById(id);
        FakeRecordHistory fakeRecordHistory = new FakeRecordHistory();
        if (fakeRecordCompany.getCompanyName() != null){
            fakeRecordHistory.setName(fakeRecordCompany.getCompanyName());
        }
        if (fakeRecordCompany.getBatchId() != null){
            fakeRecordHistory.setBatchId(fakeRecordCompany.getBatchId());
        }
        if (fakeRecordCompany.getFakeContent() !=null){
            fakeRecordHistory.setContent(fakeRecordCompany.getFakeContent());
        }
        if (fakeRecordCompany.getCompanyUsid() != null){
            fakeRecordHistory.setIdCode(fakeRecordCompany.getCompanyUsid());
        }
        fakeRecordHistory.setCreateTime(new Date());
        fakeRecordHistory.setHandle("删除");
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        fakeRecordHistory.setOperator(securityUser.getLoginName());//登录名
        iFakeRecordHistoryService.save(fakeRecordHistory);
        return ResponseUtil.success();
    }

}
