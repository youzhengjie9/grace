package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
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
     * 主键,命令空间id
     */
    private String namespaceId;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * 配额
     */
    private int quota;

    /**
     * 配置数量
     */
    private int configCount;

    /**
     * 命名空间类型。（ 0 : Global configuration， 1 : Default private namespace ，2 : Custom namespace.）
     */
    private int type;


    /**
     * 命名空间描述
     */
    private String namespaceDesc;

    /**
     * 创建建造者对象
     *
     * @return {@link NamespaceBuilder}
     */
    public static NamespaceBuilder newBuilder(){
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

    public NamespaceBuilder quota(int quota) {
        this.quota = quota;
        return this;
    }

    public NamespaceBuilder configCount(int configCount) {
        this.configCount = configCount;
        return this;
    }

    public NamespaceBuilder type(int type) {
        this.type = type;
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
        //使用bean拷贝,将当前对象（NamespaceBuilder），转成建造出来的对象（Namespace）
        return BeanUtil.copyProperties(this, Namespace.class);
    }

}