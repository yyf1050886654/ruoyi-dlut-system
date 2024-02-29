package com.ruoyi.dlut.mapper;

import com.github.pagehelper.Page;
import com.ruoyi.dlut.pojo.TeacherAwards;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TeacherAwardDao {
    public Page<TeacherAwards> findByCondition(@Param(value = "value")String queryString,
                                               @Param(value = "sort")int sort,
                                               @Param(value = "teacherUid") int teacherUid);
    public Page<TeacherAwards> findByCondition4All(@Param(value = "value")String queryString,
                                               @Param(value = "sort")int sort);
    public void add(TeacherAwards teacherAwards);
    public void deleteById(Integer id);
    public Page<TeacherAwards> manageFindByCondition(@Param(value = "value")String queryString);
    public String findZipNameById(@Param(value = "id") int id);
    public List<TeacherAwards> findAwardsByTeacherAndSorts(@Param(value = "sort")int sort,
                                                           @Param(value = "teacherUid")int teacherUid);
    public String sortXSum(@Param(value = "sort")int sort,
                           @Param(value = "teacherUid") int teacherUid);
    public TeacherAwards findById(Integer id);
    public void edit(TeacherAwards awards);
    public double sortXKindY(@Param(value = "sort")int sort,
                             @Param(value = "kind") int kind,
                             @Param(value = "teacherUid") int teacherUid);
}