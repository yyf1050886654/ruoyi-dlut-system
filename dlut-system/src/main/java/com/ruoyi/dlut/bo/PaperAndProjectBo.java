package com.ruoyi.dlut.bo;


import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaperAndProjectBo {
    @Excel(name = "奖项id（通过\"指标管理/指标详情\"查询）")
    private Long awardId;

    @Excel(name = "负责人")
    private String userName;

    @Excel(name = "项目名称（选填）")
    private String projectName;

    @Excel(name = "合同进账（选填）")
    private BigDecimal ContractAmount;

    @Excel(name = "权重调整系数")
    private BigDecimal weight;

    @Excel(name = "排位折合分数")
    private BigDecimal rankPosition;

    @Excel(name = "备注")
    private String remark;

}
