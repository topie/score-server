package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.Indicator;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-02.
*/
public interface IIndicatorService extends IService<Indicator> {

    PageInfo<Indicator> selectByFilterAndPage(Indicator indicator, int pageNum, int pageSize);

    List<Indicator> selectByFilter(Indicator indicator);

    List<Integer> selectBindMaterialIds(Integer id);

    int insertBindMaterial(Integer id, Integer mId);

    int deleteBindMaterial(Integer id);

    List<Integer> selectBindDepartmentIds(Integer id);

    int deleteBindDepartment(Integer id);

    int insertBindDepartment(Integer id, Integer dId);

    List<TreeNode> selectDepartmentTreeNodes();

    List<TreeNode> selectDepartmentTreeNodesIndicators();

    List<Integer> selectDistinctIndicatorIdByMids(String[] mIds);

    Integer selectIndicatorIdByMaterialId(Integer materialId);

    List<Integer> selectIndicatorIdByRoleId(Integer roleId);

    List<Integer> selectIndicatorIdByRoleIds(List<Integer> roles);
}
