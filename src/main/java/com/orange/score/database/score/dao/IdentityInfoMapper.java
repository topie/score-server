package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.score.dto.SearchDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IdentityInfoMapper extends Mapper<IdentityInfo> {

    List<IdentityInfo> selectByCompany(@Param("search") SearchDto searchDto);

    List<Map> selectExportList1(@Param("item") Map argMap);

    List<Map> selectExportList5(@Param("item") Map argMap);

    List<Map> selectExportList6(@Param("item") Map argMap);

    List<Map> selectExportList7_1(@Param("item") Map argMap);

    List<Map> selectExportList7(@Param("item") Map argMap);

    List<Map> selectExportList2(@Param("item") Map argMap);

    List<Integer> selectApprovingRedCompanyId(@Param("item") IdentityInfo identityInfo, @Param("limit") int limit);

    List<Map> selectExportList3(@Param("item") Map argMap);

    List<Map> selectExportList4(@Param("item") Map argMap);

    List<IdentityInfo> selectByFilter2(@Param("item") IdentityInfo identityInfo);

    //根据batchid和score_value的数据获取申请人
    List<Map> selectIdentityInfoRecipientList(@Param("item") Map argMap);

    List<Map> selectIdentityInfoRecipientList2(@Param("item") Map argMap);

    List<Map> exportDisqualified(@Param("item") Map argMap);
}
