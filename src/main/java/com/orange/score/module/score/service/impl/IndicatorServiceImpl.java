package com.orange.score.module.score.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.TreeNode;
import com.orange.score.database.score.dao.IndicatorMapper;
import com.orange.score.database.score.model.Indicator;
import com.orange.score.module.score.service.IIndicatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by chenJz1012 on 2018-04-02.
 */
@Service
@Transactional
public class IndicatorServiceImpl extends BaseService<Indicator> implements IIndicatorService {

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Override
    public PageInfo<Indicator> selectByFilterAndPage(Indicator indicator, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Indicator> list = selectByFilter(indicator);
        return new PageInfo<>(list);
    }

    @Override
    public List<Indicator> selectByFilter(Indicator indicator) {
        Condition condition = new Condition(Indicator.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (indicator!=null && StringUtils.isNotEmpty(indicator.getName())) {
            criteria.andLike("name", "%" + indicator.getName() + "%");
        }
        return indicatorMapper.selectByCondition(condition);
    }

    @Override
    public List<Integer> selectBindMaterialIds(Integer id) {
        return indicatorMapper.selectBindMaterialIds(id);
    }

    @Override
    public int insertBindMaterial(Integer id, Integer mId) {
        return indicatorMapper.insertBindMaterial(id, mId);
    }

    @Override
    public int deleteBindMaterial(Integer id) {
        return indicatorMapper.deleteBindMaterial(id);
    }

    @Override
    public List<Integer> selectBindDepartmentIds(Integer id) {
        return indicatorMapper.selectBindDepartmentIds(id);
    }

    @Override
    public int deleteBindDepartment(Integer id) {
        return indicatorMapper.deleteBindDepartment(id);
    }

    @Override
    public int insertBindDepartment(Integer id, Integer dId) {
        return indicatorMapper.insertBindDepartment(id, dId);
    }

    @Override
    public List<TreeNode> selectDepartmentTreeNodes() {
        return indicatorMapper.selectDepartmentTreeNodes();
    }

    @Override
    public List<TreeNode> selectDepartmentTreeNodesIndicators() {
        return indicatorMapper.selectDepartmentTreeNodesIndicators();
    }

    @Override
    public List<Integer> selectDistinctIndicatorIdByMids(String[] mIds) {
        return indicatorMapper.selectDistinctIndicatorIdByMids(mIds);
    }

    @Override
    public Integer selectIndicatorIdByMaterialId(Integer materialId) {
        return indicatorMapper.selectIndicatorIdByMaterialId(materialId);
    }

    @Override
    public List<Integer> selectIndicatorIdByRoleId(Integer roleId) {
        return indicatorMapper.selectIndicatorIdByRoleId(roleId);
    }

    @Override
    public List<Integer> selectIndicatorIdByRoleIds(List<Integer> roles) {
        return indicatorMapper.selectIndicatorIdByRoleIds(roles);
    }

}

