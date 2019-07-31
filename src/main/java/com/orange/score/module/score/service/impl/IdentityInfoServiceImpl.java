package com.orange.score.module.score.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.SqlUtil;
import com.orange.score.common.core.BaseService;
import com.orange.score.common.utils.MethodUtil;
import com.orange.score.common.utils.SearchItem;
import com.orange.score.common.utils.SearchUtil;
import com.orange.score.database.core.model.ColumnJson;
import com.orange.score.database.core.model.Region;
import com.orange.score.database.score.dao.IdentityInfoMapper;
import com.orange.score.database.score.model.CompanyInfo;
import com.orange.score.database.score.model.IdentityInfo;
import com.orange.score.module.core.service.IColumnJsonService;
import com.orange.score.module.score.dto.SearchDto;
import com.orange.score.module.score.service.IIdentityInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenJz1012 on 2018-04-08.
 */
@Service
@Transactional
public class IdentityInfoServiceImpl extends BaseService<IdentityInfo> implements IIdentityInfoService {

    @Autowired
    private IdentityInfoMapper identityInfoMapper;

    @Autowired
    private IColumnJsonService iColumnJsonService;

    @Autowired
    private IIdentityInfoService iIdentityInfoService;

    @Override
    public PageInfo<IdentityInfo> selectByFilterAndPage(IdentityInfo identityInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<IdentityInfo> list = selectByFilter(identityInfo);
        return new PageInfo<>(list);
    }

    @Override
    public List<IdentityInfo> selectByFilter(IdentityInfo identityInfo) {
        Page<Region> tmp = SqlUtil.getLocalPage();
        SqlUtil.clearLocalPage();
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        if (identityInfo != null) {
            if (identityInfo.getUnionApproveStatus1() != null) {
                criteria.andEqualTo("unionApproveStatus1", identityInfo.getUnionApproveStatus1());
            }
            if (identityInfo.getUnionApproveStatus2() != null) {
                criteria.andEqualTo("unionApproveStatus2", identityInfo.getUnionApproveStatus2());
            }
            if (identityInfo.getHallStatus() != null) {
                criteria.andEqualTo("hallStatus", identityInfo.getHallStatus());
            }
            if (identityInfo.getPoliceApproveStatus() != null) {
                criteria.andEqualTo("policeApproveStatus", identityInfo.getPoliceApproveStatus());
                criteria.andNotEqualTo("reservationStatus", 10);
            }
            if (identityInfo.getRensheAcceptStatus() != null) {
                criteria.andEqualTo("rensheAcceptStatus", identityInfo.getRensheAcceptStatus());
                criteria.andNotEqualTo("reservationStatus", 10);
            }
            if (identityInfo.getCancelStatus() != null) {
                criteria.andEqualTo("cancelStatus", identityInfo.getCancelStatus());
            }
            if (identityInfo.getBatchId() != null) {
                criteria.andEqualTo("batchId", identityInfo.getBatchId());
            }
            if (identityInfo.getCompanyId() != null) {
                criteria.andEqualTo("companyId", identityInfo.getCompanyId());
            }
            if (identityInfo.getAcceptAddressId() != null) {
                criteria.andEqualTo("acceptAddressId", identityInfo.getAcceptAddressId());
            }
            if (identityInfo.getPreApprove() != null){
                criteria.andEqualTo("preApprove",identityInfo.getPreApprove());
            }
            /*
            2018年10月15日，增加一个锁定人的搜索条件
             */
//            List<IdentityInfo> identityInfos = iIdentityInfoService.findAll();
//            String lockUser2 = null;
//            for (IdentityInfo idenInfo : identityInfos){
//                if(idenInfo.getId() == identityInfo.getId()){
//                    lockUser2 = idenInfo.getLockUser2();
//                }
//            }
            if (identityInfo.getLockUser2() != null && identityInfo.getLockUser2() != "") {
                criteria.andEqualTo("lockUser2", identityInfo.getLockUser2());
            }
            ColumnJson columnJson = new ColumnJson();
            columnJson.setTableName("t_identity_info");
            List<ColumnJson> list = iColumnJsonService.selectByFilter(columnJson);
            if (list.size() > 0) {
                List<SearchItem> searchItems = new ArrayList<>();
                columnJson = list.get(0);
                JSONArray jsonArray = JSONArray.parseArray(columnJson.getSearchConf());
                for (Object o : jsonArray) {
                    SearchItem searchItem = new SearchItem();
                    searchItem.setLabel(((JSONObject) o).getString("label"));
                    searchItem.setName(((JSONObject) o).getString("name"));
                    searchItem.setType(((JSONObject) o).getString("type"));
                    searchItem.setSearchType(((JSONObject) o).getString("searchType"));
                    if (StringUtils.isNotEmpty(searchItem.getName())) {
                        Object value = MethodUtil.invokeGet(identityInfo, searchItem.getName());
                        if (value != null) {
                            if (value instanceof String) {
                                if (StringUtils.isNotEmpty((String) value)) searchItem.setValue(value);
                            } else {
                                searchItem.setValue(value);
                            }
                        }
                    }
                    searchItems.add(searchItem);
                }
                SearchUtil.convert(criteria, searchItems);
            }
            if (identityInfo.getUnionApproveStatus2() != null && identityInfo.getUnionApproveStatus2() == 4 && identityInfo.getIdNumber() == "" && identityInfo.getName()== ""){
                criteria.orEqualTo("materialStatus",1);
            }
            if (StringUtils.isNotEmpty(identityInfo.getOrderByColumn())) {
                String[] temp = identityInfo.getOrderByColumn().split(",");
                if (StringUtils.isNotEmpty(identityInfo.getOrderBy()) && "desc".equals(identityInfo.getOrderBy())) {
                    for (int i=0;i<temp.length-1;i++){
                        condition.orderBy(temp[i]);
                    }
                    condition.orderBy(temp[temp.length-1]).desc();
                } else {
                    for (int i=0;i<temp.length-1;i++){
                        condition.orderBy(temp[i]);
                    }
                    //condition.orderBy(identityInfo.getOrderByColumn()).asc();
                    condition.orderBy(temp[temp.length-1]).asc();
                }

            } else {
                condition.orderBy("id").desc();
            }
        }
        if (tmp != null) SqlUtil.setLocalPage(tmp);

        return identityInfoMapper.selectByCondition(condition);
    }

    @Override
    public PageInfo<IdentityInfo> selectByCompany(SearchDto searchDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<IdentityInfo> list = identityInfoMapper.selectByCompany(searchDto);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map> selectExportList1ByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = selectExportList1(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map> selectExportList5ByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = selectExportList5(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> selectExportList1(Map argMap) {
        return identityInfoMapper.selectExportList1(argMap);
    }

    @Override
    public List<Map> selectExportList5(Map argMap) {
        return identityInfoMapper.selectExportList5(argMap);
    }

    @Override
    public PageInfo<Map> selectExportList2ByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = selectExportList2(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> selectExportList2(Map argMap) {
        return identityInfoMapper.selectExportList2(argMap);
    }

    @Override
    public List<Integer> selectApprovingRedCompanyId(IdentityInfo identityInfo, int limit) {
        return identityInfoMapper.selectApprovingRedCompanyId(identityInfo, limit);
    }

    @Override
    public int countByCondition(Condition condition) {
        return identityInfoMapper.selectCountByCondition(condition);
    }

    @Override
    public List<Map> selectExportList3(Map argMap) {
        return identityInfoMapper.selectExportList3(argMap);
    }

    @Override
    public PageInfo<Map> selectExportList3ByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = selectExportList3(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> selectExportList4(Map argMap) {
        return identityInfoMapper.selectExportList4(argMap);
    }

    @Override
    public PageInfo<Map> selectExportList4ByPage(Map argMap, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map> list = selectExportList4(argMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> selectIdentityInfoRecipientList(Map argMap) {
        return identityInfoMapper.selectIdentityInfoRecipientList(argMap);
    }

    @Override
    public List<Map> selectIdentityInfoRecipientList2(Map argMap) {
        return identityInfoMapper.selectIdentityInfoRecipientList2(argMap);
    }

    @Override
    public List<IdentityInfo> selectByFilter2(IdentityInfo identityInfo) {
//        identityInfoMapper.selectByFilter2();
        Condition condition = new Condition(IdentityInfo.class);
        tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
        return identityInfoMapper.selectByCondition(condition);
    }
}

