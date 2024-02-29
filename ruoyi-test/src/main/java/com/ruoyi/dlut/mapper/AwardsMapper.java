package com.ruoyi.dlut.mapper;

import java.util.List;
import com.ruoyi.dlut.domain.Awards;

/**
 * 指标管理Mapper接口
 * 
 * @author yyf
 * @date 2024-02-29
 */
public interface AwardsMapper 
{
    /**
     * 查询指标管理
     * 
     * @param id 指标管理主键
     * @return 指标管理
     */
    public Awards selectAwardsById(Long id);

    /**
     * 查询指标管理列表
     * 
     * @param awards 指标管理
     * @return 指标管理集合
     */
    public List<Awards> selectAwardsList(Awards awards);

    /**
     * 新增指标管理
     * 
     * @param awards 指标管理
     * @return 结果
     */
    public int insertAwards(Awards awards);

    /**
     * 修改指标管理
     * 
     * @param awards 指标管理
     * @return 结果
     */
    public int updateAwards(Awards awards);

    /**
     * 删除指标管理
     * 
     * @param id 指标管理主键
     * @return 结果
     */
    public int deleteAwardsById(Long id);

    /**
     * 批量删除指标管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAwardsByIds(Long[] ids);
}
