package com.grace.console.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class NamespaceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命令空间id
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

    public NamespaceDTO() {
    }

    public NamespaceDTO(String namespaceId, String namespaceName, String namespaceDesc) {
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
     * 校验NamespaceDTO对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        fillDefaultValue();
        if (namespaceId == null) {
            throw new RuntimeException("namespaceId不能为空");
        }
        if(namespaceName == null){
            throw new RuntimeException("namespaceName不能为空");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        if(StringUtils.isBlank(namespaceDesc)){
            namespaceDesc=StringUtils.EMPTY;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamespaceDTO that = (NamespaceDTO) o;
        return Objects.equals(namespaceId, that.namespaceId) && Objects.equals(namespaceName, that.namespaceName) && Objects.equals(namespaceDesc, that.namespaceDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceId, namespaceName, namespaceDesc);
    }

    @Override
    public String toString() {
        return "NamespaceDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", namespaceDesc='" + namespaceDesc + '\'' +
                '}';
    }

}
