package ${basePackage}.module.${module}.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${basePackage}.database.${module}.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.database.${module}.model.${modelNameUpperCamel};
import ${basePackage}.module.${module}.service.I${modelNameUpperCamel}Service;
import ${basePackage}.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;



/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends BaseService<${modelNameUpperCamel}> implements I${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    @Override
    public PageInfo<${modelNameUpperCamel}> selectByFilterAndPage(${modelNameUpperCamel} ${modelNameLowerCamel}, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<${modelNameUpperCamel}> list = selectByFilter(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<${modelNameUpperCamel}> selectByFilter(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        Condition condition = new Condition(${modelNameUpperCamel}.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return ${modelNameLowerCamel}Mapper.selectByCondition(condition);
    }
}

