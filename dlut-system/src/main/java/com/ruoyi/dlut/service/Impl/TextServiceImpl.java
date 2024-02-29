package com.ruoyi.dlut.service.Impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.dlut.mapper.TextDao;
import com.ruoyi.dlut.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@DataSource(value = DataSourceType.SLAVE)
public class TextServiceImpl implements TextService {
    @Autowired
    private TextDao textDao;

    @Override
    public String getText(int kind, int sort) {
        return textDao.getText(sort,kind);
    }
}
