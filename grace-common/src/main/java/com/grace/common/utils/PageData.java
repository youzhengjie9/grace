package com.grace.common.utils;

import java.util.List;

/**
 * 分页数据展示
 */
public class PageData<T> {

    // 分页后的数据
    private List<T> data;

    // 分页前数据的总记录数
    private int count;
    
    public List<T> getData() {
        return data;
    }
    
    public void setData(List<T> data) {
        this.data = data;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    @Override
    public String toString() {
        return "ListView{" + "data=" + data + ", count=" + count + '}';
    }
}
