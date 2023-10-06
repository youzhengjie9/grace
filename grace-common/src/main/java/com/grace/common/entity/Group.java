package com.grace.common.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 分组
 *
 * @author youzhengjie
 * @date 2023/10/06 15:25:27
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 该分组下的服务集合
     */
    private Set<Service> services;


    public Group() {
    }

    public Group(String groupName, Set<Service> services) {
        this.groupName = groupName;
        this.services = services;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", services=" + services +
                '}';
    }
}
