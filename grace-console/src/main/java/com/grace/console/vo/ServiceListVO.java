package com.grace.console.vo;

import java.io.Serializable;
import java.util.List;

/**
 * service列表vo
 *
 * @author youzhengjie
 * @date 2023-10-07 23:37:27
 */
public class ServiceListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * “分页之后/被分页后”的service列表
     */
    private List<ServiceListItem> pagedServiceList;

    /**
     * “分页之前”的service总记录数
     */
    private int totalCount;

    public ServiceListVO() {
    }

    public ServiceListVO(List<ServiceListItem> pagedServiceList, int totalCount) {
        this.pagedServiceList = pagedServiceList;
        this.totalCount = totalCount;
    }

    public List<ServiceListItem> getPagedServiceList() {
        return pagedServiceList;
    }

    public void setPagedServiceList(List<ServiceListItem> pagedServiceList) {
        this.pagedServiceList = pagedServiceList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ServiceListVO{" +
                "pagedServiceList=" + pagedServiceList +
                ", totalCount=" + totalCount +
                '}';
    }
}
