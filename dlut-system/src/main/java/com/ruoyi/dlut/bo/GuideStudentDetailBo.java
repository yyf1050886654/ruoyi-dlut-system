package com.ruoyi.dlut.bo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GuideStudentDetailBo {
    //负责人
    @Excel(name = "负责人")
    private String username;
    //层次（国家级，省级）
    @Excel(name = "层次（国家级、省级）")
    private String level;
    //竞赛级别
    @Excel(name = "类别（一类、二类、三类）")
    private String kind;
    //特等奖、一等奖、二等奖、三等奖
    @Excel(name = "获奖等级（特等奖、一等奖、二等奖、三等奖）")
    private String priceLevel;
    //排名（1-5）
    @Excel(name = "排名（第一名、第二名、第三名、第四名、第五名）")
    private String ranking;
    //指导教师排名（1-2）
    @Excel(name = "指导教师排名（第一指导教师、第二指导教师）")
    private String guideTeacherRanking;
    @Excel(name = "竞赛名称")
    private String CompetitionName;
    @Excel(name = "调整系数，没有默认为1，可不写")
    private BigDecimal weight;
}
