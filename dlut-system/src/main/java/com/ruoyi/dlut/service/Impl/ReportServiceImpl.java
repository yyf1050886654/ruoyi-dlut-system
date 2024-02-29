package com.ruoyi.dlut.service.Impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.dlut.mapper.ReportDao;
import com.ruoyi.dlut.mapper.TeacherDao;
import com.ruoyi.dlut.mapper.UserDao;
import com.ruoyi.dlut.service.ReportService;
import com.ruoyi.dlut.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service
@DataSource(value = DataSourceType.SLAVE)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Map<String, Object>> findSortCount(int teacherUid) {
        return reportDao.findSortCount(teacherUid);
    }

    @Override
    public List<Map<String, Object>> getTeacherCreditRank() {
        return reportDao.getTeacherCreditRank();
    }

    @Override
    public Map<String, Object> getReportData () throws Exception {
        //获得当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("teacherNum",userDao.getUserNum(2));
        result.put("adminNum",userDao.getUserNum(3));
        result.put("maxCredit",teacherDao.getMaxCredit());
        result.put("minCredit",teacherDao.getMinCredit());
        result.put("averageCredit",teacherDao.getAVGCredit());
        List<Double> list = reportDao.getRankList();
        int middleNum = list.size()/2;
        result.put("middleCredit",list.get(middleNum));
        return result;
    }
}
