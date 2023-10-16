package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.grace.common.entity.Namespace;

import java.time.LocalDateTime;

/**
 * Namespace建造者类
 *
 * @author youzhengjie
 * @date 2023-10-07 09:27:31
 */
public class NamespaceBuilder {

    /**
     * 主键，命令空间id
     */
    private String namespaceId;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * 该命名空间最大的配置数
     */
    private int maxConfigCount;

    /**
     * 命名空间描述
     */
    private String namespaceDesc;

    /**
     * 创建建造者对象
     *
     * @return {@link NamespaceBuilder}
     */
    public static NamespaceBuilder newBuilder() {
        return new NamespaceBuilder();
    }

    public NamespaceBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public NamespaceBuilder namespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
        return this;
    }

    public NamespaceBuilder maxConfigCount(int maxConfigCount) {
        this.maxConfigCount = maxConfigCount;
        return this;
    }

    public NamespaceBuilder namespaceDesc(String namespaceDesc) {
        this.namespaceDesc = namespaceDesc;
        return this;
    }

    /**
     * 构建对象
     *
     * @return {@link Namespace}
     */
    public Namespace build() {
        return new Namespace(namespaceId, namespaceName, maxConfigCount, namespaceDesc);
    }

}