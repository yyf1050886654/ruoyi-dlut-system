package com.ruoyi.dlut.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherCreditSumLeafBo extends BaseEntity {
    private Long userId;
    private String userName;
    private Integer awardCount;
    private String awardName;
    private BigDecimal credit;
}
