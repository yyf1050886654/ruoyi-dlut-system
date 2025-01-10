package com.ruoyi.dlut.enums.competition;

import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public enum CompetitionKindEnum {
    // 定义枚举常量
    HARD(1, "一类", new BigDecimal("5")),
    MIDDLE(2, "二类", new BigDecimal("2")),
    EASY(3, "三类", new BigDecimal("1"));


    private final int code;
    private final String name;
    private final BigDecimal rankPosition;
    public static CompetitionKindEnum of(String name) {
        for (CompetitionKindEnum level : CompetitionKindEnum.values()) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        throw new ServiceException("Unsupported competition kind: " + name);
    }
}
