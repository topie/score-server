package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.dao.MaterialInfoMapper;
import com.orange.score.database.score.model.MaterialInfo;
import com.orange.score.module.score.service.IMaterialInfoService;
import com.orange.score.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;



/**
 * Created by chenJz1012 on 2018-04-02.
 */
@Service
@Transactional
public class MaterialInfoServiceImpl extends BaseService<MaterialInfo> implements IMaterialInfoService {

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Override
    public PageInfo<MaterialInfo> selectByFilterAndPage(MaterialInfo materialInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = selectByFilter(materialInfo);
        return new PageInfo<>(list);
    }

    @Override
    public List<MaterialInfo> selectByFilter(MaterialInfo materialInfo) {
        Condition condition = new Condition(MaterialInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return materialInfoMapper.selectByCondition(condition);
    }

    @Override
    public List<TreeNode> selectMaterialTreeNodes() {
        return materialInfoMapper.selectMaterialTreeNodes();
    }
}

