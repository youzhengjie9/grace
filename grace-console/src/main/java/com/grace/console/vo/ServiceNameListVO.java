package com.grace.console.vo;

import java.io.Serializable;
import java.util.List;

/**
 * service名称集合VO
 *
 * @author youzhengjie
 * @date 2023/10/05 22:41:20
 */
public class ServiceNameListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * “分页之后”的service名称集合
     */
    private List<String> serviceNameList;

    /**
     * “分页之前”的service总记录数
     */
    private int totalCount;

    public ServiceNameListVO() {
    }

    public List<String> getServiceNameList() {
        return serviceNameList;
    }

    public void setServiceNameList(List<String> serviceNameList) {
        this.serviceNameList = serviceNameList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ServiceNameListVO{" +
                "serviceNameList=" + serviceNameList +
                ", totalCount=" + totalCount +
                '}';
    }
}
