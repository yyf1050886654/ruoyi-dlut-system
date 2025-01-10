package com.ruoyi.dlut.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 奖项详情对象 tb_awards_category
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@EqualsAndHashCode(callSuper = true)
@TableName(resultMap = "com.ruoyi.dlut.mapper.TbAwardsCategoryMapper.TbAwardsCategoryResult")
@Data
public class TbAwardsCategory extends BaseEntity
        {
private static final long serialVersionUID = 1L;

        /** 自增id */
            @TableId(value = "id",type = IdType.AUTO)
            @Excel(name = "奖项id")
        private Long id;

        /** 1-7板块 */
                @Excel(name = "1-7板块")
        private Long sort;

        /** 奖项名称 */
                @Excel(name = "奖项名称")
        private String awardName;

        /** 等级，由高到低（0是最高级别） */
        private Long priceLevel;

        /** 几等奖 */
                @Excel(name = "几等奖")
        private String priceLevelName;

        /** 各个板块的小类 */
                @Excel(name = "各个板块的小类")
        private Long kind;

        /** 积分 */
                @Excel(name = "积分")
        private BigDecimal credit;
        }
