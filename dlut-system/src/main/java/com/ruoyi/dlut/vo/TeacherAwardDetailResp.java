package com.ruoyi.dlut.vo;

import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherAwardDetailResp extends TbTeacherAwards {
    private TbAwardsCategory awardsCategory;
}
