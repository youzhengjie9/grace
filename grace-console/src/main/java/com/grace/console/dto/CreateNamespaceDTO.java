package com.grace.console.dto;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Namespace;
import com.grace.common.entity.builder.NamespaceBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * 创建命名空间dto
 *
 * @author youzhengjie
 * @date 2023/10/16 17:08:17
 */
public class CreateNamespaceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间ID(不填则自动生成)
     */
    private String namespaceId;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * 命名空间描述
     */
    private String namespaceDesc;

    public CreateNamespaceDTO() {
    }

    public CreateNamespaceDTO(String namespaceId, String namespaceName, String namespaceDesc) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.namespaceDesc = namespaceDesc;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getNamespaceDesc() {
        return namespaceDesc;
    }

    public void setNamespaceDesc(String namespaceDesc) {
        this.namespaceDesc = namespaceDesc;
    }

    /**
     * 校验当前对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        // 如果namespaceName为空,则报错
        if (StringUtils.isBlank(namespaceName)) {
            throw new RuntimeException("namespaceName不能为空");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        // 如果命名空间id为空则使用UUID进行自动生成
        if (StringUtils.isBlank(namespaceId)) {
            // 随机生成的命名空间的id长度为36位
            namespaceId = UUID.randomUUID().toString().substring(0,36);
        }
        if (StringUtils.isBlank(namespaceDesc)) {
            namespaceDesc = "";
        }
    }

    /**
     * 通过CreateNamespaceDTO对象构建Namespace
     *
     * @return {@link Namespace}
     */
    public Namespace buildNamespaceByCreateNamespaceDTO() {

        return NamespaceBuilder.newBuilder()
                .namespaceId(namespaceId)
                .namespaceName(namespaceName)
                .namespaceDesc(namespaceDesc)
                .build();
    }

    @Override
    public String toString() {
        return "CreateNamespaceDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", namespaceDesc='" + namespaceDesc + '\'' +
                '}';
    }
}
