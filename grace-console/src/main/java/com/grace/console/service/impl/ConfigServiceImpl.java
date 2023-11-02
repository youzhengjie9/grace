package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Config;
import com.grace.common.entity.ConfigVersion;
import com.grace.common.entity.builder.ConfigBuilder;
import com.grace.common.entity.builder.ConfigVersionBuilder;
import com.grace.common.enums.ConfigOperationTypeEnum;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.MD5Utils;
import com.grace.common.utils.SnowId;
import com.grace.console.event.ConfigModifiedEvent;
import com.grace.console.mapper.ConfigMapper;
import com.grace.console.mapper.ConfigVersionMapper;
import com.grace.console.service.ConfigService;
import com.grace.console.vo.ConfigListItemVO;
import com.grace.security.utils.SecurityContext;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * ConfigService实现类
 *
 * @author youzhengjie
 * @date 2023/10/17 23:40:01
 */
@Service
@Transactional // 开启单体事务
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigVersionMapper configVersionMapper;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Boolean publishConfig(Config config, HttpServletRequest request) {
        try {
            String namespaceId = config.getNamespaceId();
            String groupName = config.getGroupName();
            String dataId = config.getDataId();
            // 生成当前最新的配置版本的id（因为我们后面要生成一条配置版本）
            long versionId = SnowId.nextId();
            LocalDateTime currentTime = LocalDateTime.now();
            // 设置新增和修改共用的属性（这样避免在下面写两份同样的代码）
            config.setMd5(MD5Utils.md5Hex(config.getContent(), Constants.ENCODE));
            config.setLastUpdateTime(currentTime);
            // 如果数据库中没有该配置,则“新增”该配置
            if (getConfig(namespaceId, groupName, dataId) == null) {
                config.setId(SnowId.nextId());
                // 设置当前配置对应的最新的配置版本id
                config.setCurrentVersionId(versionId);
                // TODO: 2023/10/18 userid暂时写死
                config.setCreateUserId(SecurityContext.getCurrentUserId());
                config.setCreateUserIp(IpUtils.getIpAddrByHttpServletRequest(request));
                config.setCreateTime(currentTime);
                int insertConfigResult = configMapper.insert(config);
                // 如果“新增”成功就生成一个类型为“新增”的配置版本
                if (insertConfigResult > 0) {
                    // 创建一个类型为“新增”的配置版本
                    ConfigVersion configVersion = ConfigVersionBuilder
                            .directBuild(config, versionId, ConfigOperationTypeEnum.INSERT, request);
                    // 生成类型为“新增”的配置版本
                    int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                    if (insertConfigVersionResult > 0) {
                        return true;
                    } else {
                        // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configVersionMapper.insert(configVersion)）
                        throw new RuntimeException();
                    }
                }
            }
            // 如果数据库中有该配置,则“修改”该配置
            else {
                LambdaUpdateWrapper<Config> updateWrapper =
                        new LambdaUpdateWrapper<Config>()
                                // 更新当前配置对应的最新的配置版本id
                                .set(Config::getCurrentVersionId, versionId)
                                .set(Config::getConfigDesc, config.getConfigDesc())
                                .set(Config::getType, config.getType())
                                .set(Config::getContent, config.getContent())
                                .set(Config::getMd5, config.getMd5())
                                .set(Config::getLastUpdateTime, config.getLastUpdateTime())
                                .eq(Config::getNamespaceId, namespaceId)
                                .eq(Config::getGroupName, groupName)
                                .eq(Config::getDataId, dataId);
                // 修改配置
                int updateResult = configMapper.update(null, updateWrapper);
                // 如果“修改”成功就生成一个类型为“修改”的配置版本
                if (updateResult > 0) {
                    // 创建一个类型为“修改”的配置版本
                    ConfigVersion configVersion = ConfigVersionBuilder
                            .directBuild(config, versionId, ConfigOperationTypeEnum.UPDATE, request);
                    // 生成类型为“修改”的配置版本
                    int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                    if (insertConfigVersionResult > 0) {
                        // 创建配置被修改事件
                        ConfigModifiedEvent configModifiedEvent =
                                new ConfigModifiedEvent(this, namespaceId, groupName, dataId);
                        // 发布配置被修改事件
                        applicationContext.publishEvent(configModifiedEvent);
                        return true;
                    } else {
                        // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.update(null,updateWrapper)）
                        throw new RuntimeException();
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // 触发事务回滚
            throw new RuntimeException();
        }

    }

    @Override
    public Config getConfig(String namespaceId, String groupName, String dataId) {

        return configMapper.selectOne(
                new LambdaQueryWrapper<Config>()
                        .eq(Config::getNamespaceId, namespaceId)
                        .eq(Config::getGroupName, groupName)
                        .eq(Config::getDataId, dataId)
        );
    }

    @Override
    public List<ConfigListItemVO> getConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size, Boolean fuzzySearch) {
        return configMapper.getConfigListItemVOByPage(namespaceId, groupName, dataId, page, size, fuzzySearch);
    }

    @Override
    public int getConfigTotalCount(String namespaceId, String groupName, String dataId, Boolean fuzzySearch) {
        return configMapper.getConfigTotalCount(namespaceId, groupName, dataId, fuzzySearch);
    }

    @Override
    public Boolean deleteConfig(String namespaceId, String groupName, String dataId, HttpServletRequest request) {

        try {
            // 先通过namespaceId, groupName, dataId查询该需要被删除的配置（注意: 必须放在删除配置的代码之前）
            Config config = getConfig(namespaceId, groupName, dataId);
            // 配置版本id
            long versionId = SnowId.nextId();
            // 删除配置
            int deleteConfigResult = configMapper.deleteConfig(namespaceId, groupName, dataId);
            // 如果删除配置成功
            if (deleteConfigResult > 0) {
                // 创建一个类型为“删除”的配置版本
                ConfigVersion configVersion = ConfigVersionBuilder
                        .directBuild(config, versionId, ConfigOperationTypeEnum.DELETE, request);
                // 生成类型为“删除”的配置版本
                int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                // 如果生成配置版本成功
                if (insertConfigVersionResult > 0) {
                    return true;
                } else {
                    // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.deleteConfig(namespaceId, groupName, dataId)
                    throw new RuntimeException();
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // 触发回滚
            throw new RuntimeException();
        }

    }

    @Override
    public long getCurrentVersionId(String namespaceId, String groupName, String dataId) {
        Long currentVersionId = configMapper.getCurrentVersionId(namespaceId, groupName, dataId);
        // 如果currentVersionId为空,说明该配置在sys_config表中不存在（这种情况比较罕见,但是也有可能）
        if (currentVersionId == null) {
            return 0;
        }
        return currentVersionId;
    }

    @Override
    public Boolean importConfig(String namespaceId, String groupName, MultipartFile configFile, String configConflictPolicy, HttpServletRequest request) {
        try {
            // dataId
            String dataId = configFile.getOriginalFilename();
            // 配置类型
            String configType = getFileType(dataId);
            // 读取MultipartFile的配置（文件）内容
            String configContent = new String(configFile.getBytes(), StandardCharsets.UTF_8);
            // 如果符合下面的格式
            if (configType.equalsIgnoreCase("yaml")
                    || configType.equalsIgnoreCase("properties")
                    || configType.equalsIgnoreCase("json")) {
                // 通过namespaceId, groupName, dataId获取指定的配置
                Config config = getConfig(namespaceId, groupName, dataId);
                // 如果config为空，说明配置没有发生冲突
                if (config == null) {
                    config = ConfigBuilder.newBuilder()
                            .namespaceId(namespaceId)
                            .groupName(groupName)
                            .dataId(dataId)
                            .content(configContent)
                            .configDesc("")
                            .type(configType)
                            .build();
                    // 发布配置
                    return this.publishConfig(config, request);
                }
                // 如果config不为空，说明配置发生了冲突
                else {
                    // 跳过
                    if (StringUtils.isBlank(configConflictPolicy) ||
                            configConflictPolicy.equalsIgnoreCase("skip")) {
                        // 直接返回即可,啥也不用做（这就是跳过）
                        return true;
                    }
                    // 覆盖
                    else if (configConflictPolicy.equalsIgnoreCase("cover")) {
                        // 删除配置
                        this.deleteConfig(namespaceId, groupName, dataId, request);
                        config = ConfigBuilder.newBuilder()
                                .namespaceId(namespaceId)
                                .groupName(groupName)
                                .dataId(dataId)
                                .content(configContent)
                                .configDesc("")
                                .type(configType)
                                .build();
                        // 重新发布配置
                        return this.publishConfig(config, request);
                    }
                }
            }
            log.warn("不支持该配置格式.{}", configType);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚
            throw new RuntimeException();
        }
    }

    private String getFileType(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        // 不能直接通过"."号进行分隔,要转成"\\."才行
        String[] arr = fileName.split("\\.");
        return arr[arr.length - 1];
    }

    @Override
    public Boolean batchDeleteConfig(List<String> batchDeleteConfigList, HttpServletRequest request) {

        try {
            batchDeleteConfigList.forEach(batchDeleteConfig -> {
                String[] config = batchDeleteConfig.split("@@");
                String namespaceId = config[0];
                String groupName = config[1];
                String dataId = config[2];
                this.deleteConfig(namespaceId, groupName, dataId, request);
            });
            return true;
        } catch (Exception e) {
            // 触发事务的回滚
            throw new RuntimeException();
        }

    }

//    @Override
//    public ResponseEntity<FileSystemResource> exportSelectedConfig(List<String> exportConfigIdList, HttpServletResponse response) throws IOException {
//
//        ArrayList<File> files = new ArrayList<>();
//        //文件路径
//        files.add(new File("C:\\Users\\youzhengjie666\\Desktop\\test\\asd.properties"));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        // 导出zip的目录
//        String zipDir = "C:\\";
//        // 导出zip的压缩包名
//        String zipName = "导出配置-"+simpleDateFormat.format(new Date()) + ".zip";
//        File zipFile = new File(zipDir + zipName);
//        try (ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(new FileOutputStream(zipFile))) {
//            for (File file : files) {
//                ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
//                zipOutputStream.putArchiveEntry(entry);
//
//                try (FileInputStream fileInputStream = new FileInputStream(file)) {
//                    byte[] buffer = new byte[1024];
//                    int length;
//                    while ((length = fileInputStream.read(buffer)) > 0) {
//                        zipOutputStream.write(buffer, 0, length);
//                    }
//                }
//                zipOutputStream.closeArchiveEntry();
//            }
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", zipFile.getName());
//
//        return new ResponseEntity<>(new FileSystemResource(zipFile), headers, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<FileSystemResource> exportSelectedConfig(List<String> exportConfigIdList, HttpServletResponse response) throws IOException {
        // 临时目录
        File tempDir = null;
        try {
            // 获取需要导出的配置id列表
            List<Config> configList = configMapper.getConfigList(exportConfigIdList);
            if(configList != null && configList.size()>0){
                // 存放我们等等创建的所有配置文件的file对象
                List<File> fileList = Collections.synchronizedList(new ArrayList<>());
                // 临时目录的路径,用于存储我们通过configList生成的配置文件,用完会删除这个目录(临时目录名称采用雪花算法随机生成)
                String tempDirPath = "C:\\" + SnowId.nextId()+"\\";
                // 自旋（为了保证临时目录的路径的唯一性，防止临时目录冲突）
                while (true){
                    tempDir = new File(tempDirPath);
                    // 如果这个临时目录存在,
                    if(tempDir.exists() && tempDir.isDirectory()){
                        // 重新生成临时目录的路径（防止目录冲突）
                        tempDirPath = "C:\\" + SnowId.nextId()+"\\";
                    }else{
                        // 如果这个临时目录不存在,则直接break;
                        break;
                    }
                }
                // 创建临时目录(走到这里,临时目录的路径就是唯一的了,不用担心遇到相同的文件夹,可以直接创建这个文件夹)
                tempDir.mkdir();
                // 通过configList在临时目录生成（并写入）配置文件
                for (Config config : configList) {
                    String dataId = config.getDataId();
                    String configContent = config.getContent();
                    File configFile = new File(tempDirPath + dataId);
                    // 如果配置文件不存在则创建配置文件
                    if(!configFile.exists()){
                        configFile.createNewFile();
                    }
                    // 将配置文件对象保存到文件列表中
                    fileList.add(configFile);
                    // 创建配置文件的写入流
                    FileWriter configFileWriter = new FileWriter(configFile);
                    // 往我们刚刚创建好的配置文件中写入配置内容
                    configFileWriter.write(configContent);
                    // 刷新缓冲区才算真正的写入成功
                    configFileWriter.flush();
                    // 关闭配置文件的写入流
                    configFileWriter.close();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                // 导出zip的目录
                String zipDir = "C:\\";
                // 导出zip的压缩包名
                String zipName = "导出配置-"+simpleDateFormat.format(new Date()) + ".zip";
                File zipFile = new File(zipDir + zipName);
                try (ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(new FileOutputStream(zipFile))) {
                    for (File file : fileList) {
                        ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                        zipOutputStream.putArchiveEntry(entry);

                        try (FileInputStream fileInputStream = new FileInputStream(file)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fileInputStream.read(buffer)) > 0) {
                                zipOutputStream.write(buffer, 0, length);
                            }
                        }
                        zipOutputStream.closeArchiveEntry();
                    }
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", zipFile.getName());
                return new ResponseEntity<>(new FileSystemResource(zipFile), headers, HttpStatus.OK);
            }
            throw new RuntimeException("没有选择需要导出的配置文件");
        }finally {
            // 删除临时目录
            if(tempDir != null){
                // 递归删除非空目录
                deleteDirectory(tempDir);
            }
        }

    }

    /**
     * 递归删除非空目录
     *
     * @param directory
     * @return boolean
     */
    private static boolean deleteDirectory(File directory){
        if(directory.isDirectory()){
            File[] files = directory.listFiles();
            if(files != null && files.length > 0){
                for(File file : files){
                    if(file.isDirectory()){
                        deleteDirectory(file);
                    }else{
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }
}

