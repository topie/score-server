package com.orange.sbs.module.security.service;

import com.orange.sbs.common.core.IService;
import com.orange.sbs.common.utils.TreeNode;
import com.orange.sbs.database.security.model.Role;
import com.orange.sbs.module.security.dto.HasRoleUserDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/3 说明：
 */
public interface RoleService extends IService<Role> {

    int insertRole(Role role);

    int updateRole(Role role);

    Role findRoleById(Integer id);

    int deleteRole(Integer id);

    int insertRoleFunction(Integer roleId, Integer functionId);

    int deleteFunctionByRoleId(Integer roleId);

    List<Map> findRoleMatchUpFunctions();

    List<Integer> findFunctionByRoleId(Integer roleId);

    List<TreeNode> getRoleTreeNodes(Role role);

    PageInfo<Role> findRoleList(int pageNum, int pageSize, Role role);

    List<Integer> findHasRoleUserIdsByRoleId(Integer roleId);

    PageInfo<HasRoleUserDTO> findHasRoleUserDtoListByRoleId(int pageNum, int pageSize, HasRoleUserDTO hasRoleUserDTO);

    void refreshAuthAndResource(Integer roleId);

    Integer selectRoleIdByRoleName(String roleName);

    List<TreeNode> selectRoleFunctions(List<Integer> roleIdsList);

}
