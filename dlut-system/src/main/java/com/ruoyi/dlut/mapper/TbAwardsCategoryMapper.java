package com.ruoyi.dlut.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.dlut.domain.TbAwardsCategory;

/**
 * 奖项详情Mapper接口
 *
 * @author yangyifan
 * @date 2025-01-07
 */
public interface TbAwardsCategoryMapper extends BaseMapper<TbAwardsCategory> {
    /**
     * 查询奖项详情
     *
     * @param id 奖项详情主键
     * @return 奖项详情
     */
    public TbAwardsCategory selectTbAwardsCategoryById(Long id);

    /**
     * 查询奖项详情列表
     *
     * @param tbAwardsCategory 奖项详情
     * @return 奖项详情集合
     */
    public List<TbAwardsCategory> selectTbAwardsCategoryList(TbAwardsCategory tbAwardsCategory);

    /**
     * 新增奖项详情
     *
     * @param tbAwardsCategory 奖项详情
     * @return 结果
     */
    public int insertTbAwardsCategory(TbAwardsCategory tbAwardsCategory);

    /**
     * 修改奖项详情
     *
     * @param tbAwardsCategory 奖项详情
     * @return 结果
     */
    public int updateTbAwardsCategory(TbAwardsCategory tbAwardsCategory);

    /**
     * 删除奖项详情
     *
     * @param id 奖项详情主键
     * @return 结果
     */
    public int deleteTbAwardsCategoryById(Long id);

    /**
     * 批量删除奖项详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbAwardsCategoryByIds(Long[] ids);
}
