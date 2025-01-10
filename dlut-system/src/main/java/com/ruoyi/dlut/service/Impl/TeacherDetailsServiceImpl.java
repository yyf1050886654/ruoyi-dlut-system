package com.ruoyi.dlut.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.dlut.bo.ExportAwardDetailsBo;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public List<ExportAwardDetailsBo> exportAwardDetails() {
        List<TbTeacherAwards> teacherAwardsList = tbTeacherAwardsService.list();
        List<ExportAwardDetailsBo> exportAwardDetailsBos = new ArrayList<>();
        for (TbTeacherAwards tbTeacherAwards :teacherAwardsList){
            if (tbTeacherAwards.getAwardId() == null){
                continue;
            }
            TbAwardsCategory tbAwardsCategory = tbAwardsCategoryService.selectTbAwardsCategoryById(tbTeacherAwards.getAwardId());
            if (tbAwardsCategory != null){
                ExportAwardDetailsBo exportAwardDetailsBo = getExportAwardDetailsBo(tbTeacherAwards, tbAwardsCategory);
                exportAwardDetailsBos.add(exportAwardDetailsBo);
            }
        }
        return exportAwardDetailsBos;
    }

    @NotNull
    private static ExportAwardDetailsBo getExportAwardDetailsBo(TbTeacherAwards tbTeacherAwards, TbAwardsCategory tbAwardsCategory) {
        ExportAwardDetailsBo exportAwardDetailsBo = new ExportAwardDetailsBo();
        exportAwardDetailsBo.setAwardName(tbAwardsCategory.getAwardName());
        exportAwardDetailsBo.setPriceLevelName(tbAwardsCategory.getPriceLevelName());
        exportAwardDetailsBo.setUserName(tbTeacherAwards.getUserName());
        exportAwardDetailsBo.setRankPosition(tbTeacherAwards.getRankPosition());
        exportAwardDetailsBo.setProjectName(tbTeacherAwards.getProjectName());
        exportAwardDetailsBo.setCredit(tbTeacherAwards.getRealCredit());
        return exportAwardDetailsBo;
    }

    @Override
    public List<EvaluatedTeacherAwardsDetailResp> sumUpAllEvaluatedTeacherAwardsDetail() {
        return null;
    }


}
