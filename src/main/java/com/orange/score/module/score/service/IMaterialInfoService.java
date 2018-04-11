package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.model.MaterialInfo;
import java.util.List;

/**
* Created by chenJz1012 on 2018-04-11.
*/
public interface IMaterialInfoService extends IService<MaterialInfo> {

    PageInfo<MaterialInfo> selectByFilterAndPage(MaterialInfo materialInfo, int pageNum, int pageSize);

    List<MaterialInfo> selectByFilter(MaterialInfo materialInfo);

    List<TreeNode> selectMaterialTreeNodes();

}
