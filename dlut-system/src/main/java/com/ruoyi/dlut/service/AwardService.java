package com.ruoyi.dlut.service;

import com.ruoyi.dlut.entity.PageResult;
import com.ruoyi.dlut.entity.QueryPageBean;
import com.ruoyi.dlut.pojo.Awards;

import java.util.List;

public interface AwardService {
    public void add(Awards awards);
    public PageResult findPage(QueryPageBean queryPageBean, int sort);
    public void delete(Integer id);
    public Awards findById(Integer id);
    public void edit(Awards awards);
    public List<Awards> findAll();
    public List<Integer> findAwardsIdsByTeacherUId(Integer TeacherUid);
    public List<Awards> findByKind(Integer kind, Integer sort);

    public List<String> findByKindUnique(Integer kind, Integer sort);

}
