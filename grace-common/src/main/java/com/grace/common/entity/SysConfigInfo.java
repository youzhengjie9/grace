package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace配置中心的配置信息表的实体类
 *
 * @author youzhengjie
 * @date 2023-06-16 08:47:09
 */
@TableName("sys_name_space")
public class SysConfigInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,命令空间id
     * mp会自动为@TableId("id")属性生成id（默认是雪花算法生成的分布式id）。
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("id")
    private Long id;

    /**
     * 配置信息的名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 该配置信息所属的分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 配置信息的内容
     */
    @TableField("content")
    private String content;

    /**
     * md5值,每一次修改配置内容这个值都要重新生成,用于判断配置文件是否被修改
     */
    @TableField("md5")
    private String md5;

    /**
     * 描述
     */
    @TableField("desc")
    private String desc;

    /**
     * 该配置所属的命名空间id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("name_space_id")
    private Long nameSpaceId;

    /**
     * 配置信息内容的类型(例如:text、yml或yaml、properties、json、xml)
     */
    @TableField("type")
    private String type;

    /**
     * 创建该配置信息的用户id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("user_id")
    private Long userId;

    /**
     * 创建该配置信息的用户的ip地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后一次修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic
    @TableField("del_flag")
    private int delFlag;

    public SysConfigInfo() {
    }

    public SysConfigInfo(Long id, String configName, String groupName, String content, String md5, String desc, Long nameSpaceId, String type, Long userId, String ip, LocalDateTime createTime, LocalDateTime updateTime, int delFlag) {
        this.id = id;
        this.configName = configName;
        this.groupName = groupName;
        this.content = content;
        this.md5 = md5;
        this.desc = desc;
        this.nameSpaceId = nameSpaceId;
        this.type = type;
        this.userId = userId;
        this.ip = ip;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.delFlag = delFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getNameSpaceId() {
        return nameSpaceId;
    }

    public void setNameSpaceId(Long nameSpaceId) {
        this.nameSpaceId = nameSpaceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysConfigInfo{" +
                "id=" + id +
                ", configName='" + configName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", content='" + content + '\'' +
                ", md5='" + md5 + '\'' +
                ", desc='" + desc + '\'' +
                ", nameSpaceId=" + nameSpaceId +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}