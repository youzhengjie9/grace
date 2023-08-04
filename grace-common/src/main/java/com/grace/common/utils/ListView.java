package com.grace.common.utils;

import java.util.List;

/**
 * ListView
 */
public class ListView<T> {
    
    private List<T> data;
    
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
