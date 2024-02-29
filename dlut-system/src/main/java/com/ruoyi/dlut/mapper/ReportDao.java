package com.ruoyi.dlut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface ReportDao {
    public List<Map<String,Object>> findSortCount(@Param(value = "teacherUid") int teacherUid);
    public List<Map<String, Object>> getTeacherCreditRank();
    public List<Double> getRankList();
}
