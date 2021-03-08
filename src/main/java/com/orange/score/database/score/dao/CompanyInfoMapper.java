package com.orange.score.database.score.dao;

import com.orange.score.common.core.Mapper;
import com.orange.score.database.score.model.CompanyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CompanyInfoMapper extends Mapper<CompanyInfo> {

    CompanyInfo getCompanyInfoByIdentityInfoId(@Param("identityInfoId") Integer identityInfoId);

}