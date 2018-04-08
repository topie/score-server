package com.orange.score.common.utils;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class SearchUtil {

    public static void convert(Example.Criteria criteria, List<SearchItem> searchItemList) {
        for (SearchItem searchItem : searchItemList) {
            convert(criteria, searchItem);
        }
    }

    private static void convert(Example.Criteria criteria, SearchItem searchItem) {
        if (searchItem.getValue() == null) return;
        switch (searchItem.getSearchType()) {
            case "like":
                criteria.andLike(searchItem.getName(), "%" + searchItem.getValue() + "%");
                break;
            case "equalTo":
                criteria.andEqualTo(searchItem.getName(), searchItem.getValue());
                break;
            default:
                break;
        }
    }
}
