package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.CloneConfigDTO;
import com.grace.common.entity.Config;
import com.grace.console.vo.ConfigListItemVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ConfigService接口
 *
 * @author youzhengjie
 * @date 2023/10/17 23:40:14
 */
public interface ConfigService extends IService<Config> {

    /**
     * 发布配置(如果不存在该配置则“新增配置”,如果存在该配置则“修改配置”)
     *
     * @param config  publishConfigDTO
     * @param request request
     * @return {@link Boolean}
     */
    Boolean publishConfig(Config config, HttpServletRequest request);


    /**
     * 获取配置
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param dataId dataId
     * @return {@link Config}
     */
    Config getConfig(String namespaceId,String groupName,String dataId);


    /**
     * 分页获取ConfigListItemVO
     *
     * @param namespaceId namespaceId（"精确"搜索）
     * @param groupName   groupName（模糊搜素）
     * @param dataId      dataId（模糊搜索）
     * @param page        page
     * @param size        size
     * @param fuzzySearch 是否为模糊查询
     * @return {@link List}<{@link ConfigListItemVO}>
     */
    List<ConfigListItemVO> getConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size,Boolean fuzzySearch);

    /**
     * 获取配置总数
     *
     * @param namespaceId namespaceId（"精确"搜索）
     * @param groupName groupName（模糊搜素）
     * @param dataId dataId（模糊搜索）
     * @return int
     */
    int getConfigTotalCount(String namespaceId, String groupName, String dataId,Boolean fuzzySearch);

    /**
     * 删除配置
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param dataId dataId
     * @param request request
     * @return {@link Boolean}
     */
    Boolean deleteConfig(String namespaceId, String groupName, String dataId,HttpServletRequest request);

    /**
     * 获取当前配置的版本id
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName groupName（“精确”搜索素）
     * @param dataId dataId（“精确”搜索）
     * @return long
     */
    long getCurrentVersionId(String namespaceId, String groupName, String dataId);

    /**
     * 导入配置
     *
     * @return {@link Boolean}
     */
    Boolean importConfig(String namespaceId,String groupName,
                         MultipartFile configFile,String configConflictPolicy,
                         HttpServletRequest request);

    /**
     * 批量删除配置
     *
     * @return {@link Boolean}
     */
    Boolean batchDeleteConfig(List<String> batchDeleteConfigIdList, HttpServletRequest request);

    /**
     * 根据配置的id列表来导出配置
     *
     * @param exportConfigIdList 配置的id列表
     * @return {@link ResponseEntity}<{@link FileSystemResource}>
     */
    ResponseEntity<FileSystemResource> exportSelectedConfig(List<String> exportConfigIdList, HttpServletResponse response) throws IOException;

    /**
     * 克隆配置
     *
     * @param cloneConfigDTO 克隆配置DTO
     * @param request
     * @return {@link Boolean}
     */
    Boolean cloneConfig(CloneConfigDTO cloneConfigDTO,HttpServletRequest request);


    /**
     * 判断配置是否存在
     *
     * @return {@link Boolean}
     */
    Boolean hasConfig(String namespaceId, String groupName, String dataId);

}
