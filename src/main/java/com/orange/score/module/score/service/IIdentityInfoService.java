package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.dto.SearchDto;

import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
public interface IIdentityInfoService extends IService<IdentityInfo> {

    PageInfo<IdentityInfo> selectByFilterAndPage(IdentityInfo identityInfo, int pageNum, int pageSize);

    List<IdentityInfo> selectByFilter(IdentityInfo identityInfo);

    PageInfo<IdentityInfo> selectByCompany(SearchDto searchDto, Integer pageNum, Integer pageSize);

    PageInfo<Map> selectExportList1ByPage(Map argMap, int pageNum, int pageSize);

    List<Map> selectExportList1(Map argMap);

    PageInfo<Map> selectExportList2ByPage(Map argMap, int pageNum, int pageSize);

    List<Map> selectExportList2(Map argMap);

    List<Integer> selectApprovingRedCompanyId(IdentityInfo identityInfo,int limit);
}
