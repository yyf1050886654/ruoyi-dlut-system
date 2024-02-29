package com.ruoyi.dlut.mapper;

import com.github.pagehelper.Page;
import com.ruoyi.dlut.pojo.Awards;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AwardDao {
    public void add(Awards awards);
    public Page<Awards> findByCondition(@Param(value = "value")String queryString,
                                        @Param(value = "sort")int sort);
    public long selectCountByAwardsId(Integer awardsId);
    public void deleteById(Integer id);
    public Awards findById(Integer id);
    public void edit(Awards awards);
    public List<Awards> findAll();
    public List<Integer> findAwardsIdsByTeacherUId(Integer teacherUid);
    List<Awards> findByKind(@Param(value = "kind")Integer kind,
                            @Param(value = "sort")Integer sort);
    List<String> findByKindUnique(@Param(value = "kind")Integer kind,
                            @Param(value = "sort")Integer sort);

}