package com.grace.console.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.entity.RevisionsConfig;
import com.grace.console.mapper.RevisionsConfigMapper;
import com.grace.console.service.RevisionsConfigService;
import com.grace.console.vo.RevisionsConfigListItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * revisions config service impl
 *
 * @author youzhengjie
 * @date 2023/10/19 15:10:57
 */
@Service
public class RevisionsConfigServiceImpl extends ServiceImpl<RevisionsConfigMapper, RevisionsConfig> implements RevisionsConfigService {

    private static final Logger log = LoggerFactory.getLogger(RevisionsConfigServiceImpl.class);

    @Autowired
    private RevisionsConfigMapper revisionsConfigMapper;

    @Override
    public List<RevisionsConfigListItemVO> getRevisionsConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size) {
        return null;
    }

    @Override
    public int getRevisionsConfigTotalCount(String namespaceId, String groupName, String dataId) {
        return 0;
    }
}
