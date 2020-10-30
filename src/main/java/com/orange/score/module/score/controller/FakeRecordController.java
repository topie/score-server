package com.orange.score.module.score.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.BatchConf;
import com.orange.score.database.score.model.FakeRecord;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.score.model.PersonBatchStatusRecord;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IFakeRecordService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-04.
*/
@RestController
@RequestMapping("/api/score/fakeRecord")
public class FakeRecordController {

    @Autowired
    private IFakeRecordService iFakeRecordService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(FakeRecord fakeRecord,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<FakeRecord> pageInfo = iFakeRecordService.selectByFilterAndPage(fakeRecord, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("t_fake_record");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(FakeRecord fakeRecord) {
        /*
        2019年2月27日，添加在虚假材料库中添加此人的留痕记录
         */
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("idNumber", fakeRecord.getIdNumber());
        criteria.andEqualTo("batchId", fakeRecord.getBatchCode());
        List<IdentityInfo> list = iIdentityInfoService.findByCondition(condition);
        /**
         * 2020年10月29日
         * 因为要录入这个系统第一次用之前的申请人的虚假情况，所以不能在数据库查询申请人是否在库里
         * 跳过这个if判断，直接进入else 逻辑
         */
        if (false){
            return ResponseUtil.error("请输入正确的身份证号，信息");
        } else {
            fakeRecord.setCreateTime(new Date());
            iFakeRecordService.save(fakeRecord);
            SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
            if (securityUser == null) throw new AuthBusinessException("用户未登录");

            PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
            personBatchStatusRecord.setPersonIdNumber(fakeRecord.getIdNumber());
            personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"-录入虚假材料信息");
            personBatchStatusRecord.setStatusTime(new Date());
            personBatchStatusRecord.setStatusReason("申请人提供虚假信息办理积分落户");
            personBatchStatusRecord.setStatusTypeDesc("5年内有提供虚假信息申办积分入户记录的（包括虚假户籍、学历、专业、就业、住房、投资、纳税证明等），每条减30分");
            personBatchStatusRecord.setStatusInt(101);
            iPersonBatchStatusRecordService.save(personBatchStatusRecord);
            return ResponseUtil.success();
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {

        FakeRecord fakeRecord = iFakeRecordService.findById(id);
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("idNumber", fakeRecord.getIdNumber());
        criteria.andEqualTo("batchId", fakeRecord.getBatchCode());
        List<IdentityInfo> list = iIdentityInfoService.findByCondition(condition);
        iFakeRecordService.deleteById(id);
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");

        PersonBatchStatusRecord personBatchStatusRecord = new PersonBatchStatusRecord();
        personBatchStatusRecord.setPersonIdNumber(fakeRecord.getIdNumber());
        personBatchStatusRecord.setStatusStr(securityUser.getLoginName()+"-删除虚假材料信息");
        personBatchStatusRecord.setStatusTime(new Date());
        personBatchStatusRecord.setStatusReason("删除虚假材料信息");
        personBatchStatusRecord.setStatusTypeDesc("删除虚假材料信息");
        personBatchStatusRecord.setStatusInt(102);
        iPersonBatchStatusRecordService.save(personBatchStatusRecord);

        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(FakeRecord fakeRecord) {
        iFakeRecordService.update(fakeRecord);
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");

        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        FakeRecord fakeRecord = iFakeRecordService.findById(id);
        return ResponseUtil.success(fakeRecord);
    }
}
