package com.ruoyi.dlut.service;

import java.util.List;

import com.ruoyi.dlut.bo.AwardsCategoryDetailsBo;
import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 奖项详情Service接口
 *
 * @author yangyifan
 * @date 2025-01-07
 */
public interface ITbAwardsCategoryService extends IService<TbAwardsCategory> {
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
     * 批量删除奖项详情
     *
     * @param ids 需要删除的奖项详情主键集合
     * @return 结果
     */
    public int deleteTbAwardsCategoryByIds(Long[] ids);

    /**
     * 删除奖项详情信息
     *
     * @param id 奖项详情主键
     * @return 结果
     */
    public int deleteTbAwardsCategoryById(Long id);

    /**
     * 聚合查询奖项详情
     * @param kind 小板块
     * @param sort 大板块
     * @return
     */
    public List<AwardsCategoryDetailsBo> listCategoryByKindAndSort(Integer kind, Integer sort);
}
