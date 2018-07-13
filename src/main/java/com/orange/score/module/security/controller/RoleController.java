package com.orange.score.module.security.controller;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.Result;
import com.orange.score.common.utils.PageConvertUtil;
import com.orange.score.common.utils.ResponseUtil;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.security.dto.HasRoleUserDTO;
import com.orange.score.module.security.service.RoleService;
import com.orange.score.module.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgj on 2016/4/9.
 */
@Controller
@RequestMapping("/api/security/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Result roles(Role role, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Role> pageInfo = roleService.findRoleList(pageNum, pageSize, role);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertRole(Role role) {
        int result = roleService.insertRole(role);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRole(Role role) {
        int result = roleService.updateRole(role);
        if (result > 0) {
            roleService.refreshAuthAndResource(role.getId());
        }
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result loadRole(@PathVariable(value = "roleId") Integer roleId) {
        Role role = roleService.findRoleById(roleId);
        List functions = roleService.findFunctionByRoleId(roleId);
        if (functions != null) role.setFunctions(functions);
        return ResponseUtil.success(role);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam(value = "roleId") Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public Object treeNodes(Role role) {
        List<TreeNode> list = roleService.getRoleTreeNodes(role);
        return list;
    }

    @RequestMapping(value = "/treeNodesDiff", method = RequestMethod.POST)
    @ResponseBody
    public Object treeNodesDiff() {
        List<TreeNode> result = new ArrayList<>();
        TreeNode department = new TreeNode();
        department.setId(-1);
        department.setName("部门");
        department.setNocheck(true);
        result.add(department);
        TreeNode function = new TreeNode();
        function.setId(-2);
        function.setName("权限");
        function.setNocheck(true);
        result.add(function);
        Role role = new Role();
        role.setRoleType(1);
        List<TreeNode> list1 = roleService.getRoleTreeNodes(role);
        for (TreeNode treeNode : list1) {
            treeNode.setpId(-2);
        }
        result.addAll(list1);
        role.setRoleType(0);
        List<TreeNode> list2 = roleService.getRoleTreeNodes(role);
        for (TreeNode treeNode : list2) {
            treeNode.setpId(-1);
        }
        result.addAll(list2);
        return result;
    }

    @RequestMapping(value = "/functions", method = RequestMethod.POST)
    @ResponseBody
    public Object functions(@RequestParam(value = "roleIds", required = false) String roleIds) {
        List<TreeNode> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(roleIds)) {
            List<Integer> roleIdsList = new ArrayList<>();
            String[] roleIdsStr = roleIds.split(",");
            for (String roleId : roleIdsStr) {
                if (StringUtils.isNotEmpty(roleId)) {
                    roleIdsList.add(Integer.valueOf(roleId));
                }
            }
            list = roleService.selectRoleFunctions(roleIdsList);
        }
        return list;
    }

    @RequestMapping(value = "/hasRoleUserList", method = RequestMethod.GET)
    @ResponseBody
    public Result hasRoleUserList(HasRoleUserDTO hasRoleUserDTO,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        if (hasRoleUserDTO.getRoleId() == null) {
            return ResponseUtil.error("角色id不能为空");
        }
        PageInfo<HasRoleUserDTO> pageInfo = roleService
                .findHasRoleUserDtoListByRoleId(pageNum, pageSize, hasRoleUserDTO);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    @ResponseBody
    public Result selectUser(@RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        userService.insertUserRole(userId, roleId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/cancelUser", method = RequestMethod.GET)
    @ResponseBody
    public Result cancelUser(@RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        userService.deleteUserRole(userId, roleId);
        return ResponseUtil.success();
    }

}
