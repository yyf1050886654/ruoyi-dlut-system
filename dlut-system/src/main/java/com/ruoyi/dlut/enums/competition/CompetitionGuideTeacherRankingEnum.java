package com.ruoyi.dlut.enums.competition;


import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum CompetitionGuideTeacherRankingEnum {

    FIRST(1, "第一指导老师", new BigDecimal("1")),
    SECOND(2, "第二指导老师", new BigDecimal("0.3"));
    private final int code;
    private final String name;
    private final BigDecimal rankPosition;
    public static CompetitionGuideTeacherRankingEnum of(String name) {
        for (CompetitionGuideTeacherRankingEnum level : CompetitionGuideTeacherRankingEnum.values()) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        throw new ServiceException("Unsupported competition price level: " + name);
    }
}
