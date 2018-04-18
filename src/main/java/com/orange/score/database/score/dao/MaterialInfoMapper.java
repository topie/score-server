package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.MaterialInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialInfoMapper extends Mapper<MaterialInfo> {

    List<TreeNode> selectMaterialTreeNodes();

    List<MaterialInfo> findByIndicatorId(@Param("indicatorId") Integer indicatorId);
}
