package com.orange.score.module.core.service.impl;

import com.orange.score.database.core.dao.CommonMapper;
import com.orange.score.module.core.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/6/27.
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private CommonMapper commonMapper;
    @Override
    public List<Map> selectByCommonTableBySql(String sql) {
        return commonMapper.selectByCommonTableBySql(sql);
    }
}
