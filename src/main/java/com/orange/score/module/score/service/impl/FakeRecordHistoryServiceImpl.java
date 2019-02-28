package com.orange.score.module.score.service.impl;

import com.orange.score.common.core.BaseService;
import com.orange.score.database.score.dao.FakeRecordHistoryMapper;
import com.orange.score.database.score.dao.FakeRecordMapper;
import com.orange.score.database.score.model.FakeRecordHistory;
import com.orange.score.module.score.service.IFakeRecordHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rui15 on 2019/2/28.
 */
@Service
@Transactional
public class FakeRecordHistoryServiceImpl extends BaseService<FakeRecordHistory> implements IFakeRecordHistoryService {

}
