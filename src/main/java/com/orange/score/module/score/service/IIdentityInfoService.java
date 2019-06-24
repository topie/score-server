package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.dto.SearchDto;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
public interface IIdentityInfoService extends IService<IdentityInfo> {

    PageInfo<IdentityInfo> selectByFilterAndPage(IdentityInfo identityInfo, int pageNum, int pageSize);

    List<IdentityInfo> selectByFilter(IdentityInfo identityInfo);

    /*
    2018年10月13日，添加另外一个获取所有锁定人名字的方法
     */
    List<IdentityInfo> selectByFilter2(IdentityInfo identityInfo);

    PageInfo<IdentityInfo> selectByCompany(SearchDto searchDto, Integer pageNum, Integer pageSize);

    PageInfo<Map> selectExportList1ByPage(Map argMap, int pageNum, int pageSize);

    PageInfo<Map> selectExportList5ByPage(Map argMap, int pageNum, int pageSize);

    List<Map> selectExportList1(Map argMap);

    List<Map> selectExportList5(Map argMap);

    PageInfo<Map> selectExportList2ByPage(Map argMap, int pageNum, int pageSize);

    List<Map> selectExportList2(Map argMap);

    List<Integer> selectApprovingRedCompanyId(IdentityInfo identityInfo,int limit);

    int countByCondition(Condition condition);

    List<Map> selectExportList3(Map argMap);

    PageInfo<Map> selectExportList3ByPage(Map argMap, int pageNum, int pageSize);

    List<Map> selectExportList4(Map argMap);

    PageInfo<Map> selectExportList4ByPage(Map argMap, int pageNum, int pageSize);

    //根据batchid和score_value的数据获取申请人
    List<Map> selectIdentityInfoRecipientList(Map argMap);

    List<Map> selectIdentityInfoRecipientList2(Map argMap);
}
