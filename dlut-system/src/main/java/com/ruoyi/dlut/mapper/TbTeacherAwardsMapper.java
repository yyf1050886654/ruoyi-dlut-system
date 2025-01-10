package com.ruoyi.dlut.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.dlut.bo.TeacherCreditSumBo;
import com.ruoyi.dlut.domain.TbTeacherAwards;

/**
 * 教师奖项详情Mapper接口
 *
 * @author yangyifan
 * @date 2025-01-07
 */
public interface TbTeacherAwardsMapper extends BaseMapper<TbTeacherAwards> {
    /**
     * 查询教师奖项详情
     *
     * @param id 教师奖项详情主键
     * @return 教师奖项详情
     */
    public TbTeacherAwards selectTbTeacherAwardsById(Long id);

    /**
     * 查询教师奖项详情列表
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 教师奖项详情集合
     */
    public List<TbTeacherAwards> selectTbTeacherAwardsList(TbTeacherAwards tbTeacherAwards);

    /**
     * 新增教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    public int insertTbTeacherAwards(TbTeacherAwards tbTeacherAwards);

    /**
     * 修改教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    public int updateTbTeacherAwards(TbTeacherAwards tbTeacherAwards);

    /**
     * 删除教师奖项详情
     *
     * @param id 教师奖项详情主键
     * @return 结果
     */
    public int deleteTbTeacherAwardsById(Long id);

    /**
     * 批量删除教师奖项详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbTeacherAwardsByIds(Long[] ids);

    /**
     * 查询教师总分
     * @return
     */
    public List<TeacherCreditSumBo> groupAndSumByUserId(String query);
}
