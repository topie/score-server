package ${basePackage}.module.${module}.controller;

import com.github.pagehelper.PageInfo;
import ${basePackage}.common.core.Result;
import ${basePackage}.common.tools.plugins.FormItem;
import ${basePackage}.common.utils.PageConvertUtil;
import ${basePackage}.common.utils.ResponseUtil;
import ${basePackage}.database.${module}.model.${modelNameUpperCamel};
import ${basePackage}.module.core.service.ICommonQueryService;
import ${basePackage}.module.${module}.service.I${modelNameUpperCamel}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("/api/${module}/${modelNameLowerCamel}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private I${modelNameUpperCamel}Service i${modelNameUpperCamel}Service;

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(${modelNameUpperCamel} ${modelNameLowerCamel},
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<${modelNameUpperCamel}> pageInfo = i${modelNameUpperCamel}Service.selectByFilterAndPage(${modelNameLowerCamel}, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @GetMapping(value = "/formItems")
    @ResponseBody
    public Result formItems() {
        List<FormItem> list = iCommonQueryService.selectFormItemsByTable("${tableName}");
        return ResponseUtil.success(list);
    }

    @PostMapping("/insert")
    public Result insert(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        i${modelNameUpperCamel}Service.save(${modelNameLowerCamel});
        return ResponseUtil.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        i${modelNameUpperCamel}Service.deleteById(id);
        return ResponseUtil.success();
    }

    @PostMapping("/update")
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        i${modelNameUpperCamel}Service.update(${modelNameLowerCamel});
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = i${modelNameUpperCamel}Service.findById(id);
        return ResponseUtil.success(${modelNameLowerCamel});
    }
}
