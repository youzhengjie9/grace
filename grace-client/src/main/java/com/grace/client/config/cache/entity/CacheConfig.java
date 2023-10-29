package com.grace.client.config.cache.entity;

import java.io.Serializable;

/**
 * 缓存的配置
 *
 * @author youzhengjie
 * @date 2023/10/29 14:20:23
 */
public class CacheConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;

    private String md5;

    public CacheConfig(String content, String md5) {
        this.content = content;
        this.md5 = md5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "CacheConfig{" +
                "content='" + content + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
