package ${basePackage}.module.${module}.service;

import com.github.pagehelper.PageInfo;
import ${basePackage}.common.core.IService;
import ${basePackage}.database.${module}.model.${modelNameUpperCamel};
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
public interface I${modelNameUpperCamel}Service extends IService<${modelNameUpperCamel}> {

    PageInfo<${modelNameUpperCamel}> selectByFilterAndPage(${modelNameUpperCamel} ${modelNameLowerCamel}, int pageNum, int pageSize);

    List<${modelNameUpperCamel}> selectByFilter(${modelNameUpperCamel} ${modelNameLowerCamel});
}
