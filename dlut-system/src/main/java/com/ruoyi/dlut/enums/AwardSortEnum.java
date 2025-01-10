package com.ruoyi.dlut.enums;

import com.ruoyi.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum AwardSortEnum {

    // 枚举值：1-7, 例如：
    EDUCATION(1, "教学评价"),
    SCIENCE(2, "科研评价"),
    HONOR(3, "人才荣誉"),
    PLATFORM(4, "平台团队"),
    INTERNATIONAL(5, "国际化"),
    ACADEMIC_POSITION(6, "学术兼职"),
    TALENT_TRAINING(7, "人才培养质量");

    //1-7
    private final int code;
    //教学，科研，平台，国际化等
    private final String name;

    public static AwardSortEnum of(String name) {
        return Arrays.stream(AwardSortEnum.values()).filter(item -> item.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ServiceException("unsupported account op type: " + name));
    }
}
