package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.Indicator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndicatorMapper extends Mapper<Indicator> {

    List<Integer> selectBindMaterialIds(@Param("id") Integer id);

    int insertBindMaterial(@Param("id") Integer id, @Param("mId") Integer mId);

    int deleteBindMaterial(@Param("id") Integer id);

    List<Integer> selectBindDepartmentIds(@Param("id") Integer id);

    int deleteBindDepartment(@Param("id") Integer id);

    int insertBindDepartment(@Param("id") Integer id, @Param("dId") Integer dId);

    List<TreeNode> selectDepartmentTreeNodes();

    List<Integer> selectDistinctIndicatorIdByMids(@Param("mIds") String[] mIds);

    Integer selectIndicatorIdByMaterialId(@Param("materialId") Integer materialId);
}
