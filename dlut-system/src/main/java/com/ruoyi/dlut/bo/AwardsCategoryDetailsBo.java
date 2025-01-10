package com.ruoyi.dlut.bo;

import com.ruoyi.dlut.domain.TbAwardsCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardsCategoryDetailsBo {
    private String awardName;
    private List<TbAwardsCategory> details;
}
