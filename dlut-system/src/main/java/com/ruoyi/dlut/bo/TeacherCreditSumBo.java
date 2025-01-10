package com.ruoyi.dlut.bo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherCreditSumBo extends BaseEntity {

    @Excel(name = "职工号")
    private Long userId;
    @Excel(name = "教师姓名")
    private String userName;
    @Excel(name = "本年度申报数量")
    private Integer awardCount;
    @Excel(name = "总分")
    private BigDecimal credit;
    @Excel(name = "备注")
    private String remark;
}
