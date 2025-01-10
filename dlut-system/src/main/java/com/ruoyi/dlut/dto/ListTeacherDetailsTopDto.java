package com.ruoyi.dlut.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListTeacherDetailsTopDto extends BaseEntity {
    //查询的教师姓名
    private String userName;
}
