package com.ruoyi.dlut.service.Impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.dlut.constant.RedisConstant;
import com.ruoyi.dlut.mapper.TextDao;
import com.ruoyi.dlut.service.TextService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@DataSource(value = DataSourceType.SLAVE)
public class TextServiceImpl implements TextService {
    @Autowired
    private TextDao textDao;

    @Autowired
    private RedisCache redisCache;

    @Override
    public String getText(int kind, int sort) {
        String key = RedisConstant.LIST_INFO_BY_SORT_AND_BUTTON_PREFIX + ":" + sort +":"+kind;
        String cachedText = redisCache.getCacheObject(key);
        if (StringUtils.isNotEmpty(cachedText)){
            return cachedText;
        }
        String textFromDB = textDao.getText(sort, kind);
        //缓存60分钟
        redisCache.setCacheObject(key,textFromDB,10, TimeUnit.DAYS);
        return textFromDB;
    }
}
