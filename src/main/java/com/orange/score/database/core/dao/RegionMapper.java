package com.orange.score.database.core.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.core.model.Region;

import java.util.List;

public interface RegionMapper extends Mapper<Region> {

    List<TreeNode> selectTreeNodes();

}
