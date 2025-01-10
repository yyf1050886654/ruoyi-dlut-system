package com.ruoyi.dlut.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublicServiceAwardsDto {
    private Long userId;
    @Excel(name = "姓名")
    private String userName;
    @Excel(name = "服务时间")
    private BigDecimal time;
    private BigDecimal credit;
}
