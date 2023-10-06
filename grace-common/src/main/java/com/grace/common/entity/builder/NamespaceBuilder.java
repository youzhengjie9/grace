package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
import com.grace.common.entity.Namespace;

import java.time.LocalDateTime;

/**
 * Namespace建造者类
 *
 * @author youzhengjie
 * @date 2023/07/02 21:56:30
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
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private int delFlag;

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

    public NamespaceBuilder desc(String desc) {
        this.desc = desc;
        return this;
    }

    public NamespaceBuilder createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public NamespaceBuilder updateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public NamespaceBuilder delFlag(int delFlag) {
        this.delFlag = delFlag;
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