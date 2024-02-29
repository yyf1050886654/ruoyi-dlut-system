package com.ruoyi.dlut.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dlut.mapper.AwardsMapper;
import com.ruoyi.dlut.domain.Awards;
import com.ruoyi.dlut.service.IAwardsService;

/**
 * 指标管理Service业务层处理
 * 
 * @author yyf
 * @date 2024-02-29
 */
@Service
public class AwardsServiceImpl implements IAwardsService 
{
    @Autowired
    private AwardsMapper awardsMapper;

    /**
     * 查询指标管理
     * 
     * @param id 指标管理主键
     * @return 指标管理
     */
    @Override
    public Awards selectAwardsById(Long id)
    {
        return awardsMapper.selectAwardsById(id);
    }

    /**
     * 查询指标管理列表
     * 
     * @param awards 指标管理
     * @return 指标管理
     */
    @Override
    public List<Awards> selectAwardsList(Awards awards)
    {
        return awardsMapper.selectAwardsList(awards);
    }

    /**
     * 新增指标管理
     * 
     * @param awards 指标管理
     * @return 结果
     */
    @Override
    public int insertAwards(Awards awards)
    {
        return awardsMapper.insertAwards(awards);
    }

    /**
     * 修改指标管理
     * 
     * @param awards 指标管理
     * @return 结果
     */
    @Override
    public int updateAwards(Awards awards)
    {
        return awardsMapper.updateAwards(awards);
    }

    /**
     * 批量删除指标管理
     * 
     * @param ids 需要删除的指标管理主键
     * @return 结果
     */
    @Override
    public int deleteAwardsByIds(Long[] ids)
    {
        return awardsMapper.deleteAwardsByIds(ids);
    }

    /**
     * 删除指标管理信息
     * 
     * @param id 指标管理主键
     * @return 结果
     */
    @Override
    public int deleteAwardsById(Long id)
    {
        return awardsMapper.deleteAwardsById(id);
    }
}
