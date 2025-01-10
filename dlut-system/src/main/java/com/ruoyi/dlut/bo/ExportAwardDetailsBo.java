package com.ruoyi.dlut.bo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExportAwardDetailsBo {

    @Excel(name = "姓名")
    private String userName;
    @Excel(name = "项目")
    private String awardName;
    @Excel(name = "奖项等级")
    private String priceLevelName;
    @Excel(name = "排位折分系数")
    private BigDecimal rankPosition;
    @Excel(name = "详情")
    private String projectName;
    @Excel(name = "实际分数")
    private BigDecimal credit;
}
