package com.ruoyi.dlut.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.dlut.bo.TeacherCreditSumBo;
import com.ruoyi.dlut.domain.TbSysUser;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.ruoyi.dlut.dto.ListTeacherDetailsTopDto;
import com.ruoyi.dlut.vo.EvaluatedTeacherAwardsDetailResp;
import com.ruoyi.dlut.vo.EvaluatedTeacherResp;
import com.ruoyi.dlut.vo.TeacherAwardDetailResp;
import com.ruoyi.dlut.vo.TeacherCreditSumLeafBo;

import java.util.List;

public interface ITeacherDetailsServiceImpl {
    /**
     * 查询所有老师总分
     * @param queryParam
     * @return
     */
    public List<TeacherCreditSumBo> listAllTeacherCredit(String queryParam);

    /**
     * 查询所有被评教师
     * @return 教师详情
     */
    public List<EvaluatedTeacherResp> listAllTeacher();

    public List<TeacherCreditSumLeafBo> getDetailsByUserId(String userId);

    public List<EvaluatedTeacherAwardsDetailResp> sumUpAllEvaluatedTeacherAwardsDetail();

}
