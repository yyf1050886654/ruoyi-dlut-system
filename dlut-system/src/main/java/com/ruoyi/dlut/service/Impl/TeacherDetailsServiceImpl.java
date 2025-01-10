package com.ruoyi.dlut.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.dlut.bo.TeacherCreditSumBo;
import com.ruoyi.dlut.constant.ImportantConstants;
import com.ruoyi.dlut.constant.RedisConstant;
import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.ruoyi.dlut.domain.TbSysUser;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.ruoyi.dlut.dto.ListTeacherDetailsTopDto;
import com.ruoyi.dlut.mapper.TbTeacherAwardsMapper;
import com.ruoyi.dlut.service.ITbAwardsCategoryService;
import com.ruoyi.dlut.service.ITbSysUserService;
import com.ruoyi.dlut.service.ITbTeacherAwardsService;
import com.ruoyi.dlut.service.ITeacherDetailsServiceImpl;
import com.ruoyi.dlut.vo.EvaluatedTeacherAwardsDetailResp;
import com.ruoyi.dlut.vo.EvaluatedTeacherResp;
import com.ruoyi.dlut.vo.TeacherCreditSumLeafBo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TeacherDetailsServiceImpl implements ITeacherDetailsServiceImpl {

    @Autowired
    private TbTeacherAwardsMapper tbTeacherAwardsMapper;

    @Autowired
    private ITbSysUserService tbSysUserService;

    @Autowired
    private ITbTeacherAwardsService tbTeacherAwardsService;

    @Autowired
    private ITbAwardsCategoryService tbAwardsCategoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<TeacherCreditSumBo> listAllTeacherCredit(String queryParam) {
        return tbTeacherAwardsMapper.groupAndSumByUserId(queryParam);
        //return getTeacherCreditSumBos(queryParam);
    }

    @NotNull
    private ArrayList<TeacherCreditSumBo> getTeacherCreditSumBos(ListTeacherDetailsTopDto queryParam) {
        /*if (queryParam != null){
            IPage<TbSysUser> page = tbSysUserService.pageSelectUserByDepartmentId(
                    ImportantConstants.IMPORTANT_USER_DEPARTMENT_ID, queryParam.getPageNum(), queryParam.getPageSize());
        }*/
        LambdaQueryWrapper<TbTeacherAwards> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (queryParam != null && StringUtils.isNotEmpty(queryParam.getUserName())) {
            lambdaQueryWrapper.like(TbTeacherAwards::getUserName, queryParam);
        }
        lambdaQueryWrapper.orderByAsc(TbTeacherAwards::getAwardId);
        List<TbTeacherAwards> teacherAwardsList = tbTeacherAwardsService.list(lambdaQueryWrapper);
        Map<Long, TeacherCreditSumBo> collect = teacherAwardsList.stream().collect(Collectors.groupingBy(TbTeacherAwards::getUserId, Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    TeacherCreditSumBo teacherCreditSumBo = new TeacherCreditSumBo();
                    teacherCreditSumBo.setAwardCount(list.size());
                    teacherCreditSumBo.setUserId(list.get(0).getUserId());
                    teacherCreditSumBo.setUserName(list.get(0).getUserName());
                    BigDecimal internationalCredit = new BigDecimal(0);
                    BigDecimal academicPosition = new BigDecimal(0);
                    BigDecimal others = new BigDecimal(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (TbTeacherAwards tbTeacherAwards : list){
                        //国际化
                        if (tbTeacherAwards.getAwardId() >= 250 && tbTeacherAwards.getAwardId() <= 259){
                            internationalCredit = internationalCredit.add(tbTeacherAwards.getRealCredit());
                        }
                        //学术兼职
                        else if (tbTeacherAwards.getAwardId() >= 103 && tbTeacherAwards.getAwardId() <= 132){
                            academicPosition = academicPosition.add(tbTeacherAwards.getRealCredit());
                        }
                        else {
                            others = others.add(tbTeacherAwards.getRealCredit());
                        }
                    }
                    if (internationalCredit.intValue() > ImportantConstants.INTERNATIONAL_CREDIT_MAXIMUM_LIMIT){
                        stringBuilder.append("国际化").append(internationalCredit.intValue()).append("分超出上限，仅记2分");
                        internationalCredit = new BigDecimal(2);
                    }
                    if (academicPosition.intValue() > ImportantConstants.ACADEMIC_POSITION_CREDIT_MAXIMUM_LIMIT){
                        stringBuilder.append("学术兼职").append(academicPosition.intValue()).append("分超出上限，仅记4分");
                        academicPosition = new BigDecimal(4);
                    }
                    teacherCreditSumBo.setCredit(others.add(internationalCredit).add(academicPosition));
                    teacherCreditSumBo.setRemark(stringBuilder.toString());
                    return teacherCreditSumBo;
                }
        )));
        return new ArrayList<>(collect.values());
    }

    @Override
    public List<EvaluatedTeacherResp> listAllTeacher() {
        String key = RedisConstant.LIST_ALL_TEACHER_PREFIX + ":" + "allTeacher";
        List<EvaluatedTeacherResp> cachedList = redisCache.getCacheObject(key);
        if (cachedList != null){
            return cachedList;
        }
        List<TbSysUser> tbSysUsers = tbSysUserService.selectUserByDepartmentId(ImportantConstants.IMPORTANT_USER_DEPARTMENT_ID);
        //做必要的屏蔽
        List<EvaluatedTeacherResp> responses = tbSysUsers.stream().map((tbSysUser -> {
            EvaluatedTeacherResp resp = new EvaluatedTeacherResp();
            resp.setUid(tbSysUser.getUserName());
            resp.setName(tbSysUser.getNickName());
            return resp;
        })).collect(Collectors.toList());
        //缓存，过期时间30分钟
        redisCache.setCacheObject(key,responses,10, TimeUnit.DAYS);
        return responses;
    }

    @Override
    public List<TeacherCreditSumLeafBo> getDetailsByUserId(String userId) {
        if (StringUtils.isEmpty(userId)){
            throw new ServiceException("参数为空");
        }
        LambdaQueryWrapper<TbTeacherAwards> tbTeacherAwardsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbTeacherAwardsLambdaQueryWrapper.eq(TbTeacherAwards::getUserId,userId);
        tbTeacherAwardsLambdaQueryWrapper.orderByDesc(TbTeacherAwards::getRealCredit);
        List<TbTeacherAwards> originalList = tbTeacherAwardsService.list(tbTeacherAwardsLambdaQueryWrapper);
        //查询奖项
        List<TeacherCreditSumLeafBo> response = new ArrayList<>();
        for (TbTeacherAwards tbTeacherAward : originalList){
            if (tbTeacherAward.getAwardId() == null){
                throw new ServiceException("awardId为空，请联系管理员");
            }
            TbAwardsCategory tbAwardsCategory = tbAwardsCategoryService.getById(tbTeacherAward.getAwardId());
            TeacherCreditSumLeafBo teacherAwardDetailResp = new TeacherCreditSumLeafBo();
            teacherAwardDetailResp.setAwardCount(1);
            teacherAwardDetailResp.setAwardName(tbAwardsCategory.getAwardName()+"/"+tbAwardsCategory.getPriceLevelName());
            teacherAwardDetailResp.setCredit(tbTeacherAward.getRealCredit());
            teacherAwardDetailResp.setUserId(Long.valueOf(userId));
            response.add(teacherAwardDetailResp);
        }
        return response;
    }

    @Override
    public List<EvaluatedTeacherAwardsDetailResp> sumUpAllEvaluatedTeacherAwardsDetail() {
        return null;
    }


}
