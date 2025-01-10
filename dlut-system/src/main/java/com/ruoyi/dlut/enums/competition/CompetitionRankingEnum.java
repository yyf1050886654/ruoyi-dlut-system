package com.ruoyi.dlut.enums.competition;

import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CompetitionRankingEnum {

    // 定义枚举常量
    FIRST(1, "第一名", new BigDecimal("1")),
    SECOND(2, "第二名", new BigDecimal("0.7")),
    THIRD(3, "第三名", new BigDecimal("0.5")),
    FOURTH(4, "第四名", new BigDecimal("0.3")),
    FIFTH(5, "第五名", new BigDecimal("0.2"));
    private final int code;
    private final String name;
    private final BigDecimal rankPosition;
    public static CompetitionRankingEnum of(String name) {
        for (CompetitionRankingEnum level : CompetitionRankingEnum.values()) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        throw new ServiceException("Unsupported competition ranking: " + name);
    }

}
