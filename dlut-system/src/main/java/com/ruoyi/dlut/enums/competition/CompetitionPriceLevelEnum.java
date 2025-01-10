package com.ruoyi.dlut.enums.competition;


import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum CompetitionPriceLevelEnum {
    // 定义枚举常量
    SPECIAL(0, "特等奖", new BigDecimal("4")),
    FIRST(1, "一等奖", new BigDecimal("2")),
    SECOND(2, "二等奖", new BigDecimal("1")),
    THIRD(3, "三等奖", new BigDecimal("0.5"));

    private final int code;
    private final String name;
    private final BigDecimal rankPosition;
    public static CompetitionPriceLevelEnum of(String name) {
        for (CompetitionPriceLevelEnum level : CompetitionPriceLevelEnum.values()) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        throw new ServiceException("Unsupported competition price level: " + name);
    }
}
