package com.ruoyi.dlut.dto;

import com.ruoyi.dlut.domain.TbTeacherAwards;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherAwardDetailDto extends TbTeacherAwards {
    private int sort;
    private String awardName;
    private Integer pageNum;
    private Integer pageSize;
}
