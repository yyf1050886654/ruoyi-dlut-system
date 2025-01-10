package com.ruoyi.dlut.enums.competition;

import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CompetitionLevelEnum {
    // 定义枚举常量
    NATIONAL(1, "国家级", new BigDecimal("1")),
    PROVINCIAL(2, "省级", new BigDecimal("0.5"));

    private final int code;
    private final String name;
    private final BigDecimal rankPosition;
    public static CompetitionLevelEnum of(String name) {
        for (CompetitionLevelEnum level : CompetitionLevelEnum.values()) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        throw new ServiceException("Unsupported competition level: " + name);
    }
}
