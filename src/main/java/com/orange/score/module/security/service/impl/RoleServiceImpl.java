package com.orange.score.module.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.security.dao.RoleMapper;
import com.orange.score.database.security.model.Role;
import com.orange.score.module.security.SecurityMetadataSourceImpl;
import com.orange.score.module.security.UserCache;
import com.orange.score.module.security.dto.HasRoleUserDTO;
import com.orange.score.module.security.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/3 说明：
 */
@Service("roleService")
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserCache userCache;

    @Override
    public int insertRole(Role role) {
        int result = roleMapper.insertSelective(role);
        if (result > 0) {
            if (CollectionUtils.isNotEmpty(role.getFunctions())) {
                for (Integer functionId : role.getFunctions()) {
                    insertRoleFunction(role.getId(), functionId);
                }
            }
        }
        refreshAuthAndResource(role.getId());
        return result;
    }

    @Override
    public int updateRole(Role role) {
        int result = roleMapper.updateByPrimaryKey(role);
        if (result > 0) {
            deleteFunctionByRoleId(role.getId());
            if (CollectionUtils.isNotEmpty(role.getFunctions())) {
                for (Integer functionId : role.getFunctions()) {
                    insertRoleFunction(role.getId(), functionId);
                }
            }
        }
        refreshAuthAndResource(role.getId());
        return result;
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRole(Integer id) {
        int result = roleMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            roleMapper.deleteUserRoleRelateByRoleId(id);
            roleMapper.deleteFunctionByRoleId(id);
            refreshAuthAndResource(id);
        }
        return result;
    }

    @Override
    public int insertRoleFunction(Integer roleId, Integer functionId) {
        int result = roleMapper.insertRoleFunction(roleId, functionId);
        return result;
    }

    @Override
    public void refreshAuthAndResource(Integer roleId) {
        List<Integer> userIds = roleMapper.findHasRoleUserIdsByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (Integer userId : userIds) {
                userCache.removeUserFromCacheByUserId(userId);
            }
        }
        SecurityMetadataSourceImpl.refreshResourceMap();
    }

    @Override
    public Integer selectRoleIdByRoleName(String roleName) {
        return roleMapper.selectRoleIdByRoleName(roleName);
    }

    @Override
    public List<TreeNode> selectRoleFunctions(List<Integer> roleIdsList) {
        return roleMapper.selectRoleFunctions(roleIdsList);
    }

    @Override
    public List<Map> findRoleMatchUpFunctions() {
        return roleMapper.findRoleMatchUpFunctions();
    }

    @Override
    public List<TreeNode> getRoleTreeNodes(Role role) {
        return roleMapper.selectRoleTreeNodes(role);
    }

    @Override
    public PageInfo<Role> findRoleList(int pageNum, int pageSize, Role role) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleMapper.findRoleList(role);
        PageInfo<Role> page = new PageInfo<Role>(list);
        return page;
    }

    @Override
    public int deleteFunctionByRoleId(Integer roleId) {
        return roleMapper.deleteFunctionByRoleId(roleId);
    }

    @Override
    public List<Integer> findFunctionByRoleId(Integer roleId) {
        return roleMapper.findFunctionByRoleId(roleId);
    }

    @Override
    public List<Integer> findHasRoleUserIdsByRoleId(Integer roleId) {
        return roleMapper.findHasRoleUserIdsByRoleId(roleId);
    }

    @Override
    public PageInfo<HasRoleUserDTO> findHasRoleUserDtoListByRoleId(int pageNum, int pageSize,
            HasRoleUserDTO hasRoleUserDTO) {
        PageHelper.startPage(pageNum, pageSize);
        List<HasRoleUserDTO> list = roleMapper.findHasRoleUserDtoList(hasRoleUserDTO);
        PageInfo<HasRoleUserDTO> page = new PageInfo<HasRoleUserDTO>(list);
        return page;
    }
}
