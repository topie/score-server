package com.orange.score.module.score.service;

import com.github.pagehelper.PageInfo;
import com.orange.score.common.core.IService;
import com.orange.score.database.score.model.Document;
import java.util.List;

/**
* Created by chenJz1012 on 2018-06-18.
*/
public interface IDocumentService extends IService<Document> {

    PageInfo<Document> selectByFilterAndPage(Document document, int pageNum, int pageSize);

    List<Document> selectByFilter(Document document);
}
