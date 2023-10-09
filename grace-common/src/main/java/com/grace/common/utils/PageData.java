package com.grace.common.utils;

import java.util.List;

/**
 * 封装分页数据,返回给前端
 */
public class PageData<T> {

    // 分页后的列表
    private List<T> pagedList;

    // 分页前数据的总记录数
    private int totalCount;


    public PageData() {
    }

    public PageData(List<T> pagedList, int totalCount) {
        this.pagedList = pagedList;
        this.totalCount = totalCount;
    }

    public List<T> getPagedList() {
        return pagedList;
    }

    public void setPagedList(List<T> pagedList) {
        this.pagedList = pagedList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pagedList=" + pagedList +
                ", totalCount=" + totalCount +
                '}';
    }
}
