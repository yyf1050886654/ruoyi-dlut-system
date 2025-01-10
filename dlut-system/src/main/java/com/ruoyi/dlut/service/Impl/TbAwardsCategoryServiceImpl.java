package com.ruoyi.dlut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.dlut.bo.AwardsCategoryDetailsBo;
import com.ruoyi.dlut.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dlut.mapper.TbAwardsCategoryMapper;
import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.ruoyi.dlut.service.ITbAwardsCategoryService;

import com.ruoyi.common.utils.SecurityUtils;

/**
 * 奖项详情Service业务层处理
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@Service
public class TbAwardsCategoryServiceImpl extends ServiceImpl<TbAwardsCategoryMapper, TbAwardsCategory> implements ITbAwardsCategoryService {
    @Autowired
    private TbAwardsCategoryMapper tbAwardsCategoryMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询奖项详情
     *
     * @param id 奖项详情主键
     * @return 奖项详情
     */
    @Override
    public TbAwardsCategory selectTbAwardsCategoryById(Long id) {
        return tbAwardsCategoryMapper.selectTbAwardsCategoryById(id);
    }

    /**
     * 查询奖项详情列表
     *
     * @param tbAwardsCategory 奖项详情
     * @return 奖项详情
     */
    @Override
    public List<TbAwardsCategory> selectTbAwardsCategoryList(TbAwardsCategory tbAwardsCategory) {
        return tbAwardsCategoryMapper.selectTbAwardsCategoryList(tbAwardsCategory);
    }

    /**
     * 新增奖项详情
     *
     * @param tbAwardsCategory 奖项详情
     * @return 结果
     */
    @Override
    public int insertTbAwardsCategory(TbAwardsCategory tbAwardsCategory) {
        //增加就删缓存
        redisCache.deleteObject(RedisConstant.LIST_CATEGORY_BY_SORT_AND_KIND_PREFIX);
        tbAwardsCategory.setUpdateBy(SecurityUtils.getUsername());
        tbAwardsCategory.setUpdateTime(DateUtils.getNowDate());
        tbAwardsCategory.setCreateBy(SecurityUtils.getUsername());
        tbAwardsCategory.setCreateTime(DateUtils.getNowDate());
        return tbAwardsCategoryMapper.insertTbAwardsCategory(tbAwardsCategory);
    }

    /**
     * 修改奖项详情
     *
     * @param tbAwardsCategory 奖项详情
     * @return 结果
     */
    @Override
    public int updateTbAwardsCategory(TbAwardsCategory tbAwardsCategory) {
        tbAwardsCategory.setUpdateBy(SecurityUtils.getUsername());
        tbAwardsCategory.setUpdateTime(DateUtils.getNowDate());
        return tbAwardsCategoryMapper.updateTbAwardsCategory(tbAwardsCategory);
    }

    /**
     * 批量删除奖项详情
     *
     * @param ids 需要删除的奖项详情主键
     * @return 结果
     */
    @Override
    public int deleteTbAwardsCategoryByIds(Long[] ids) {
        return tbAwardsCategoryMapper.deleteTbAwardsCategoryByIds(ids);
    }

    /**
     * 删除奖项详情信息
     *
     * @param id 奖项详情主键
     * @return 结果
     */
    @Override
    public int deleteTbAwardsCategoryById(Long id) {
        return tbAwardsCategoryMapper.deleteTbAwardsCategoryById(id);
    }

    @Override
    public List<AwardsCategoryDetailsBo> listCategoryByKindAndSort(Integer kind, Integer sort) {
        String key = RedisConstant.LIST_CATEGORY_BY_SORT_AND_KIND_PREFIX + ":" + sort + ":" + kind;
        List<AwardsCategoryDetailsBo> cacheList = redisCache.getCacheObject(key);
        if (cacheList != null){
            return cacheList;
        }
        LambdaQueryWrapper<TbAwardsCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbAwardsCategory::getKind,kind)
                .eq(TbAwardsCategory::getSort,sort)
                .orderByAsc(TbAwardsCategory::getPriceLevel);
        List<TbAwardsCategory> awardsCategories = list(lambdaQueryWrapper);
        //根据奖项名称聚合
        Map<String,List<TbAwardsCategory>> listMap = awardsCategories.stream().collect(Collectors.groupingBy(TbAwardsCategory::getAwardName));
        List<AwardsCategoryDetailsBo> awardsCategoryDetailsBoList = new ArrayList<>();
        listMap.forEach((awardName,awardsCategoryList) ->{
            AwardsCategoryDetailsBo awardsCategoryDetailsBo = new AwardsCategoryDetailsBo(awardName, awardsCategoryList);
            awardsCategoryDetailsBoList.add(awardsCategoryDetailsBo);
        });
        //缓存，30分钟
        redisCache.setCacheObject(key,awardsCategoryDetailsBoList,10, TimeUnit.DAYS);
        return awardsCategoryDetailsBoList;
    }


}
