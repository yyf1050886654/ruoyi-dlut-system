package com.ruoyi.dlut.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师奖项详情对象 tb_teacher_awards
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@EqualsAndHashCode(callSuper = true)
@TableName(resultMap = "com.ruoyi.dlut.mapper.TbTeacherAwardsMapper.TbTeacherAwardsResult")
@Data
public class TbTeacherAwards extends BaseEntity {

        private static final long serialVersionUID = 1L;

        /** 自增id */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        @Excel(name = "奖项id（通过\"指标管理/指标详情\"查询）")
        private Long awardId;

        /** 用户id */
        private Long userId;

        @Excel(name = "姓名")
        private String userName;

        @Excel(name = "项目名称（选填）")
        private String projectName;

        @Excel(name = "权重调整系数")
        private BigDecimal weight;

        @Excel(name = "排位折合分数")
        private BigDecimal rankPosition;

        private BigDecimal realCredit;
}
