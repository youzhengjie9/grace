package com.grace.console.vo;

import com.grace.common.entity.Service;

import java.util.Set;

/**
 * service列表vo
 *
 * @author youzhengjie
 * @date 2023-10-07 23:37:27
 */
public class ServiceListVO {

    private static final long serialVersionUID = 1L;

    /**
     * “分页之后/被分页后”的service集合
     */
    private Set<Service> pagedServices;

    /**
     * “分页之前”的service总记录数
     */
    private int totalCount;

    public ServiceListVO() {
    }

    public ServiceListVO(Set<Service> pagedServices, int totalCount) {
        this.pagedServices = pagedServices;
        this.totalCount = totalCount;
    }

    public Set<Service> getPagedServices() {
        return pagedServices;
    }

    public void setPagedServices(Set<Service> pagedServices) {
        this.pagedServices = pagedServices;
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
                "pagedServices=" + pagedServices +
                ", totalCount=" + totalCount +
                '}';
    }
}
