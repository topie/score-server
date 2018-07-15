package com.orange.score.module.score.controller.approve;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.exception.AuthBusinessException;
import com.orange.score.common.tools.plugins.FormItem;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.database.score.model.ApplyCancel;
import com.orange.score.database.score.model.ApplyScore;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.core.service.ICommonQueryService;
import com.orange.score.module.score.service.IApplyCancelService;
import com.orange.score.module.score.service.IIdentityInfoService;
import com.orange.score.module.score.service.IPersonBatchStatusRecordService;
import com.orange.score.module.security.SecurityUser;
import com.orange.score.module.security.SecurityUtil;
import com.orange.score.module.security.service.RoleService;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-21.
 */
@RestController
@RequestMapping("/api/score/applyCancel")
public class ApplyCancelController {

    @Autowired
    private IApplyCancelService iApplyCancelService;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IPersonBatchStatusRecordService iPersonBatchStatusRecordService;

    @GetMapping(value = "/mine")
    @ResponseBody
    public Result mine(ApplyCancel applyCancel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(ApplyCancel.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("applyUserId", userId);
        if (StringUtils.isNotEmpty(applyCancel.getPersonIdNumber())) {
            criteria.andEqualTo("personIdNumber", applyCancel.getPersonIdNumber());
        }
        PageInfo<ApplyCancel> pageInfo = iApplyCancelService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/ing")
    @ResponseBody
    public Result ing(ApplyCancel applyCancel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(ApplyScore.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户没有任何部门角色");
        criteria.andIn("applyRoleId", roles);
        criteria.andEqualTo("approveStatus", 0);
        if (StringUtils.isNotEmpty(applyCancel.getPersonIdNumber())) {
            criteria.andEqualTo("personIdNumber", applyCancel.getPersonIdNumber());
        }
        PageInfo<ApplyCancel> pageInfo = iApplyCancelService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/agree")
    @ResponseBody
    public Result agree(ApplyCancel applyCancel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(ApplyScore.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户没有任何部门角色");
        criteria.andIn("applyRoleId", roles);
        criteria.andEqualTo("approveStatus", 1);
        if (StringUtils.isNotEmpty(applyCancel.getPersonIdNumber())) {
            criteria.andEqualTo("personIdNumber", applyCancel.getPersonIdNumber());
        }
        PageInfo<ApplyCancel> pageInfo = iApplyCancelService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/disAgree")
    @ResponseBody
    public Result disAgree(ApplyCancel applyCancel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null) throw new AuthBusinessException("用户未登录");
        Condition condition = new Condition(ApplyScore.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) throw new AuthBusinessException("用户没有任何部门角色");
        criteria.andIn("applyRoleId", roles);
        criteria.andEqualTo("approveStatus", 2);
        PageInfo<ApplyCancel> pageInfo = iApplyCancelService.selectByFilterAndPage(condition, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @PostMapping("/apply")
    public Result apply(@RequestParam("personId") Integer personId, @RequestParam("reason") String reason) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null) throw new AuthBusinessException("用户未登录");
        List<Integer> roles = userService.findUserDepartmentRoleByUserId(securityUser.getId());
        IdentityInfo identityInfo = iIdentityInfoService.findById(personId);
        ApplyCancel applyCancel = new ApplyCancel();
        applyCancel.setBatchId(identityInfo.getBatchId());
        applyCancel.setPersonId(personId);
        applyCancel.setPersonIdNumber(identityInfo.getIdNumber());
        applyCancel.setApplyUserId(securityUser.getId());
        applyCancel.setApplyUserType(securityUser.getUserType());
        applyCancel.setApplyUser(securityUser.getDisplayName());
        applyCancel.setApplyRoleId(roles.get(0));
        Role role = roleService.findRoleById(roles.get(0));
        applyCancel.setApplyRole(role.getRoleName());
        applyCancel.setApproveStatus(0);
        applyCancel.setApplyReason(reason);
        iApplyCancelService.save(applyCancel);
        return ResponseUtil.success();
    }

    @PostMapping("/agree")
    public Result agree(@RequestParam Integer id) {
        String userName = SecurityUtil.getCurrentUserName();
        if (userName == null) throw new AuthBusinessException("用户未登录");
        ApplyCancel applyCancel = iApplyCancelService.findById(id);
        applyCancel.setApproveContent("同意");
        applyCancel.setApproveStatus(1);
        applyCancel.setApproveUser(userName);
        iApplyCancelService.update(applyCancel);
        IdentityInfo identityInfo = iIdentityInfoService.findById(applyCancel.getPersonId());
        identityInfo.setCancelStatus(1);
        identityInfo.setHallStatus(8);
        iIdentityInfoService.update(identityInfo);
        iPersonBatchStatusRecordService
                .insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "cancelStatus", 1);
        iPersonBatchStatusRecordService.insertStatus(identityInfo.getBatchId(), identityInfo.getId(), "hallStatus", 8);
        return ResponseUtil.success();
    }

    @PostMapping("/disAgree")
    public Result disAgree(@RequestParam Integer id, @RequestParam("approveContent") String approveContent) {
        String userName = SecurityUtil.getCurrentUserName();
        if (userName == null) throw new AuthBusinessException("用户未登录");
        ApplyCancel applyCancel = iApplyCancelService.findById(id);
        applyCancel.setApproveContent(approveContent);
        applyCancel.setApproveStatus(2);
        applyCancel.setApproveUser(userName);
        iApplyCancelService.update(applyCancel);
        return ResponseUtil.success();
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> formItems = iCommonQueryService.selectFormItemsByTable("t_apply_cancel");
        List searchItems = iCommonQueryService.selectSearchItemsByTable("t_apply_cancel");
        Map result = new HashMap<>();
        result.put("formItems", formItems);
        result.put("searchItems", searchItems);
        return ResponseUtil.success(result);
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        iApplyCancelService.deleteById(id);
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ApplyCancel applyCancel = iApplyCancelService.findById(id);
        return ResponseUtil.success(applyCancel);
    }
}
