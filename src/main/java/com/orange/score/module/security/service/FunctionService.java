package com.orange.score.module.security.service;

import com.orange.score.common.core.IService;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.security.model.Function;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface FunctionService extends IService<Function> {

    int insertFunction(Function function);

    int updateFunction(Function function);

    Function findFunctionById(int id);

    int deleteFunctionById(int id);

    List<TreeNode> getFunctionTreeNodes(Function function);

    PageInfo<Function> findFunctionList(int pageNum, int pageSize, Function function);
}
