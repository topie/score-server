package com.orange.sbs.database.security.dao;

import com.orange.sbs.common.core.Mapper;
import com.orange.sbs.common.utils.TreeNode;
import com.orange.sbs.database.security.model.Function;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface FunctionMapper extends Mapper<Function> {

    public List<TreeNode> selectFunctionTreeNodes(Function function);

    List<Function> findFunctionList(Function function);

    int deleteRoleFunctionByFunctionId(int id);

    List<Integer> findRoleIdsByFunctionId(@Param("functionId") int id);
}
