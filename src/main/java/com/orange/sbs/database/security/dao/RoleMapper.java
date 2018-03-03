package com.orange.sbs.database.security.dao;

import com.orange.sbs.common.utils.TreeNode;
import com.orange.sbs.database.security.model.Role;
import com.orange.sbs.module.security.dto.HasRoleUserDTO;
import org.apache.ibatis.annotations.Param;
import com.orange.sbs.common.core.Mapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends Mapper<Role> {

    int insertRoleFunction(@Param("roleId") Integer roleId, @Param("functionId") Integer functionId);

    List<Map> findRoleMatchUpFunctions();

    List<TreeNode> selectRoleTreeNodes(Role role);

    List<Role> findRoleList(Role role);

    List<Integer> findFunctionByRoleId(@Param("roleId") Integer roleId);

    int deleteFunctionByRoleId(@Param("roleId") Integer roleId);

    List<HasRoleUserDTO> findHasRoleUserDtoList(HasRoleUserDTO hasRoleUserDTO);

    void deleteUserRoleRelateByRoleId(Integer id);

    List<Integer> findHasRoleUserIdsByRoleId(@Param("roleId") Integer roleId);

    int selectRoleIdByRoleName(@Param("roleName") String roleName);

    List<TreeNode> selectRoleFunctions(@Param("roleIds") List<Integer> roleIdsList);
}
