package com.ruoyi.dlut.constant;

/**
 * 关键常数，做特判用
 * 随数据库更改，否则会出问题
 * @author yangyifan
 * @date 2025-01-09
 */
public class ImportantConstants {
    //sys_user特判普通用户的部门id，“被评教师”部门为206
    public static final Long IMPORTANT_USER_DEPARTMENT_ID = 206L;
    //国际化最高2分
    public static final Integer INTERNATIONAL_CREDIT_MAXIMUM_LIMIT = 2;
    //学术兼职最高两分
    public static final Integer ACADEMIC_POSITION_CREDIT_MAXIMUM_LIMIT = 4;
    //指导竞赛的awardId
    public static final Long COMPETITION_GUIDE_AWARD_ID = 94L;
    //成果转化项目
    public static final Long TECHNOLOGY_TRANSFER_PROJECT_AWARD_ID = 223L;
    //其他进款
    public static final Long OTHER_INCOME_AWARD_ID = 224L;

}
