package com.ruoyi.dlut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.dlut.bo.GuideStudentDetailBo;
import com.ruoyi.dlut.bo.PaperAndProjectBo;
import com.ruoyi.dlut.constant.ImportantConstants;
import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.ruoyi.dlut.domain.TbSysUser;
import com.ruoyi.dlut.dto.PublicServiceAwardsDto;
import com.ruoyi.dlut.dto.TeacherAwardDetailDto;
import com.ruoyi.dlut.enums.competition.*;
import com.ruoyi.dlut.service.ITbAwardsCategoryService;
import com.ruoyi.dlut.service.ITbSysUserService;
import com.ruoyi.dlut.vo.TeacherAwardDetailResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dlut.mapper.TbTeacherAwardsMapper;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.ruoyi.dlut.service.ITbTeacherAwardsService;
import javax.validation.Validator;

import static com.ruoyi.common.utils.PageUtils.startPage;


/**
 * 教师奖项详情Service业务层处理
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@Service
@Slf4j
public class TbTeacherAwardsServiceImpl extends ServiceImpl<TbTeacherAwardsMapper, TbTeacherAwards> implements ITbTeacherAwardsService {
    @Autowired
    private TbTeacherAwardsMapper tbTeacherAwardsMapper;

    @Autowired
    private ITbAwardsCategoryService tbAwardsCategoryService;

    @Autowired
    protected Validator validator;

    @Autowired
    private ITbSysUserService tbSysUserService;
    /**
     * 查询教师奖项详情
     *
     * @param id 教师奖项详情主键
     * @return 教师奖项详情
     */
    @Override
    public TbTeacherAwards selectTbTeacherAwardsById(Long id) {
        return tbTeacherAwardsMapper.selectTbTeacherAwardsById(id);
    }

    /**
     * 查询教师奖项详情列表
     *
     * @param teacherAwardDetailDto 教师奖项详情
     * @return 教师奖项详情
     */
    @Override
    public TableDataInfo selectTbTeacherAwardsList(TeacherAwardDetailDto teacherAwardDetailDto,boolean isPage) {
        LambdaQueryWrapper<TbAwardsCategory> tbAwardsCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbAwardsCategoryLambdaQueryWrapper.eq(TbAwardsCategory::getSort,teacherAwardDetailDto.getSort());
        //模糊查询业绩名
        if (StringUtils.isNotEmpty(teacherAwardDetailDto.getAwardName())){
            tbAwardsCategoryLambdaQueryWrapper.like(TbAwardsCategory::getAwardName,teacherAwardDetailDto.getAwardName());
        }
        List<TbAwardsCategory> tbAwardsCategoryList = tbAwardsCategoryService.list(tbAwardsCategoryLambdaQueryWrapper);
        if (tbAwardsCategoryList == null || tbAwardsCategoryList.isEmpty()){
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.SUCCESS);
            rspData.setMsg("无数据");
            rspData.setRows(new ArrayList<>());
            rspData.setTotal(0);
            return rspData;
        }
        Map<Long, TbAwardsCategory> tbAwardsCategoryMap = tbAwardsCategoryList.stream().collect(Collectors.toMap(TbAwardsCategory::getId, entity -> entity));
        List<Long> awardIds = tbAwardsCategoryList.stream().map(TbAwardsCategory::getId).collect(Collectors.toList());
        LambdaQueryWrapper<TbTeacherAwards> tbTeacherAwardsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbTeacherAwardsLambdaQueryWrapper.in(TbTeacherAwards::getAwardId,awardIds);
        tbTeacherAwardsLambdaQueryWrapper.orderByDesc(TbTeacherAwards::getUpdateTime);
        //模糊查询姓名
        if (StringUtils.isNotEmpty(teacherAwardDetailDto.getUserName())){
            tbTeacherAwardsLambdaQueryWrapper.like(TbTeacherAwards::getUserName,teacherAwardDetailDto.getUserName());
        }
        //模糊查询项目名
        if (StringUtils.isNotEmpty(teacherAwardDetailDto.getProjectName())){
            tbTeacherAwardsLambdaQueryWrapper.like(TbTeacherAwards::getProjectName,teacherAwardDetailDto.getProjectName());
        }
        IPage<TbTeacherAwards> page = new Page<>(teacherAwardDetailDto.getPageNum(), teacherAwardDetailDto.getPageSize());
        IPage<TbTeacherAwards> tbTeacherAwardsList = page(page, tbTeacherAwardsLambdaQueryWrapper);
        List<TeacherAwardDetailResp> response = new ArrayList<>();
        for (TbTeacherAwards tbTeacherAward : tbTeacherAwardsList.getRecords()){
            TeacherAwardDetailResp teacherAwardDetailResp = new TeacherAwardDetailResp();
            BeanUtils.copyProperties(tbTeacherAward,teacherAwardDetailResp);
            teacherAwardDetailResp.setAwardsCategory(tbAwardsCategoryMap.get(tbTeacherAward.getAwardId()));
            response.add(teacherAwardDetailResp);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setTotal(tbTeacherAwardsList.getTotal());
        rspData.setRows(response);
        rspData.setMsg("查询成功");
        rspData.setCode(HttpStatus.SUCCESS);
        return rspData;
    }

    /**
     * 新增教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    @Override
    public int insertTbTeacherAwards(TbTeacherAwards tbTeacherAwards) {
        tbTeacherAwards.setUpdateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        tbTeacherAwards.setUpdateTime(DateUtils.getNowDate());
        tbTeacherAwards.setCreateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        tbTeacherAwards.setCreateTime(DateUtils.getNowDate());
        return tbTeacherAwardsMapper.insertTbTeacherAwards(tbTeacherAwards);
    }

    /**
     * 修改教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    @Override
    public int updateTbTeacherAwards(TbTeacherAwards tbTeacherAwards) {
        tbTeacherAwards.setUpdateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        tbTeacherAwards.setUpdateTime(DateUtils.getNowDate());
        return tbTeacherAwardsMapper.updateTbTeacherAwards(tbTeacherAwards);
    }

    /**
     * 批量删除教师奖项详情
     *
     * @param ids 需要删除的教师奖项详情主键
     * @return 结果
     */
    @Override
    public int deleteTbTeacherAwardsByIds(Long[] ids) {
        return tbTeacherAwardsMapper.deleteTbTeacherAwardsByIds(ids);
    }

    /**
     * 删除教师奖项详情信息
     *
     * @param id 教师奖项详情主键
     * @return 结果
     */
    @Override
    public int deleteTbTeacherAwardsById(Long id) {
        return tbTeacherAwardsMapper.deleteTbTeacherAwardsById(id);
    }

    @Override
    public List<TbTeacherAwards> findDetailByUserId(Long id) {
        LambdaQueryWrapper<TbTeacherAwards> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbTeacherAwards::getUserId,id);
        return list(queryWrapper);
    }

    @Override
    public String batchImportData(List<TbTeacherAwards> dataList, String operName) {
        if (dataList == null || dataList.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TbTeacherAwards tbTeacherAwards : dataList) {
            try {
                //检验是否存在奖项
                if (tbTeacherAwards.getAwardId() == null || StringUtils.isEmpty(tbTeacherAwards.getUserName())){
                    throw new ServiceException("请填写姓名和奖项id");
                }
                TbAwardsCategory tbAwardsCategory = tbAwardsCategoryService.selectTbAwardsCategoryById(tbTeacherAwards.getAwardId());
                //检验是否存在教师
                TbSysUser tbSysUser = tbSysUserService.selectUserByNickName(tbTeacherAwards.getUserName());
                if (tbAwardsCategory != null && tbSysUser != null) {
                    tbTeacherAwards.setUserId(Long.valueOf(tbSysUser.getUserName()));
                    tbTeacherAwards.setUserName(tbSysUser.getNickName());
                    //计算实际分数
                    BigDecimal credit = tbAwardsCategory.getCredit();
                    BigDecimal rankPosition = tbTeacherAwards.getRankPosition() == null ? new BigDecimal(1) : tbTeacherAwards.getRankPosition();
                    BigDecimal weight = tbTeacherAwards.getWeight() == null ? new BigDecimal(1) : tbTeacherAwards.getWeight();
                    //取两位小数
                    BigDecimal realCredit = credit.multiply(rankPosition).multiply(weight).setScale(2, RoundingMode.HALF_UP);
                    tbTeacherAwards.setRealCredit(realCredit);
                    insertTbTeacherAwards(tbTeacherAwards);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、").
                            append(tbTeacherAwards.getUserName()).append("老师的").append(tbAwardsCategory.getAwardName())
                            .append("(").append(tbAwardsCategory.getPriceLevelName()).append(")")
                            .append("获得").append(realCredit).append("分。导入成功");
                }
                else if (tbAwardsCategory == null){
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(tbTeacherAwards.getAwardId()).append("奖项不存在");
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(tbTeacherAwards.getUserName()).append("老师不存在");
                }
            }
            catch (Exception e) {
                failureNum++;
                String msg = "<br/>导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public String batchImportCompetition(List<GuideStudentDetailBo> dataList, String operName) {
        if (dataList == null || dataList.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (GuideStudentDetailBo guideStudentDetailBo : dataList) {
            try {
                if (StringUtils.isEmpty(guideStudentDetailBo.getUsername())){
                    throw new ServiceException("请填写姓名");
                }
                //检验是否存在教师
                TbSysUser sysUser = tbSysUserService.selectUserByNickName(guideStudentDetailBo.getUsername());
                if (sysUser != null) {
                    BeanValidators.validateWithException(validator, sysUser);
                    TbTeacherAwards tbTeacherAwards = new TbTeacherAwards();
                    tbTeacherAwards.setUserId(Long.valueOf(sysUser.getUserName()));
                    tbTeacherAwards.setUserName(sysUser.getNickName());
                    tbTeacherAwards.setProjectName(guideStudentDetailBo.getCompetitionName());
                    tbTeacherAwards.setAwardId(ImportantConstants.COMPETITION_GUIDE_AWARD_ID);
                    //计算分数
                    BigDecimal realCredit = calculateCompetition(guideStudentDetailBo).setScale(2, RoundingMode.HALF_UP);
                    tbTeacherAwards.setRankPosition(CompetitionRankingEnum.of(guideStudentDetailBo.getRanking()).getRankPosition());
                    tbTeacherAwards.setWeight(guideStudentDetailBo.getWeight());
                    tbTeacherAwards.setRealCredit(realCredit);
                    insertTbTeacherAwards(tbTeacherAwards);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、").
                            append(guideStudentDetailBo.getUsername()).append("老师的").append(guideStudentDetailBo.getCompetitionName())
                            .append("(").append(guideStudentDetailBo.getLevel()).append(")")
                            .append("获得").append(realCredit).append("分。导入成功");
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(guideStudentDetailBo.getCompetitionName()).append("负责人不存在");
                }
            }
            catch (Exception e) {
                failureNum++;
                String msg = "<br/>导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public String batchImportPaperAndProject(List<PaperAndProjectBo> dataList, String operName) {
        if (dataList == null || dataList.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PaperAndProjectBo paperAndProjectBo : dataList) {
            try {
                if (paperAndProjectBo.getAwardId() == null || StringUtils.isEmpty(paperAndProjectBo.getUserName())){
                    throw new ServiceException("请填写姓名和奖项id");
                }
                TbAwardsCategory tbAwardsCategory = tbAwardsCategoryService.selectTbAwardsCategoryById(paperAndProjectBo.getAwardId());
                //检验是否存在教师
                TbSysUser sysUser = tbSysUserService.selectUserByNickName(paperAndProjectBo.getUserName());
                if (tbAwardsCategory != null && sysUser != null) {
                    TbTeacherAwards tbTeacherAwards = new TbTeacherAwards();
                    tbTeacherAwards.setAwardId(paperAndProjectBo.getAwardId());
                    tbTeacherAwards.setUserId(Long.valueOf(sysUser.getUserName()));
                    tbTeacherAwards.setUserName(sysUser.getNickName());
                    tbTeacherAwards.setProjectName(paperAndProjectBo.getProjectName());
                    BigDecimal rankPosition = paperAndProjectBo.getRankPosition() == null ? new BigDecimal(1) : paperAndProjectBo.getRankPosition();
                    BigDecimal weight = paperAndProjectBo.getWeight() == null ? new BigDecimal(1) : paperAndProjectBo.getWeight();
                    BigDecimal credit;
                    //其他项目
                    if (Objects.equals(tbTeacherAwards.getAwardId(), ImportantConstants.OTHER_INCOME_AWARD_ID)){
                        tbTeacherAwards.setAwardId(ImportantConstants.OTHER_INCOME_AWARD_ID);
                        if (paperAndProjectBo.getContractAmount() == null){
                            throw new ServiceException(ImportantConstants.OTHER_INCOME_AWARD_ID+"编号为其他项目，请填写合同金额！");
                        }
                        //每满20w计入一分，四舍五入
                        credit = paperAndProjectBo.getContractAmount().multiply(rankPosition).multiply(weight).divide(new BigDecimal(20), 2, RoundingMode.HALF_UP);
                    }
                    else if(Objects.equals(tbTeacherAwards.getAwardId(), ImportantConstants.TECHNOLOGY_TRANSFER_PROJECT_AWARD_ID)){
                        tbTeacherAwards.setAwardId(ImportantConstants.TECHNOLOGY_TRANSFER_PROJECT_AWARD_ID);
                        if (paperAndProjectBo.getContractAmount() == null){
                            throw new ServiceException(ImportantConstants.TECHNOLOGY_TRANSFER_PROJECT_AWARD_ID+"编号为成果转化项目，请填写合同金额！");
                        }
                        //单项转化金额在 500 万元（含）以上的
                        if (paperAndProjectBo.getContractAmount().compareTo(new BigDecimal(500)) >= 0){
                            BigDecimal original = paperAndProjectBo.getContractAmount().divide(new BigDecimal(50), 2, RoundingMode.HALF_UP);
                            credit = original.multiply(rankPosition).multiply(weight);
                        }
                        //单项转化金额在 500 万元以下 50 万（含）以上的
                        else if (paperAndProjectBo.getContractAmount().compareTo(new BigDecimal(500)) < 0
                                && paperAndProjectBo.getContractAmount().compareTo(new BigDecimal(50)) >= 0){
                            credit = new BigDecimal(5).multiply(rankPosition).multiply(weight);
                        }
                        else {
                            credit = new BigDecimal(0);
                        }
                    }
                    else {
                        credit = tbAwardsCategory.getCredit().multiply(rankPosition).multiply(weight).setScale(2, RoundingMode.HALF_UP);
                    }
                    tbTeacherAwards.setRankPosition(rankPosition);
                    tbTeacherAwards.setWeight(weight);
                    tbTeacherAwards.setRealCredit(credit);
                    insertTbTeacherAwards(tbTeacherAwards);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、").
                            append(tbTeacherAwards.getUserName()).append("老师的").append(tbAwardsCategory.getAwardName())
                            .append("(").append(tbAwardsCategory.getPriceLevelName()).append(")")
                            .append("获得").append(credit.doubleValue()).append("分。导入成功");
                }
                else if (tbAwardsCategory == null){
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(paperAndProjectBo.getAwardId()).append("奖项不存在");
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(paperAndProjectBo.getUserName()).append("负责人不存在");
                }
            }
            catch (Exception e) {
                failureNum++;
                String msg = "<br/>导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public String batchImportPublicService(List<PublicServiceAwardsDto> dataList, String operName) {
        if (dataList == null || dataList.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PublicServiceAwardsDto publicServiceAwardsDto : dataList) {
            try {
                //检验是否存在用户
                if (StringUtils.isEmpty(publicServiceAwardsDto.getUserName())){
                    throw new ServiceException("请填写姓名");
                }
                //检验时长是否填写
                if (publicServiceAwardsDto.getTime() == null){
                    throw new ServiceException("请填写时长");
                }
                TbSysUser tbSysUser = tbSysUserService.selectUserByNickName(publicServiceAwardsDto.getUserName());
                if (tbSysUser != null) {
                    publicServiceAwardsDto.setUserId(Long.valueOf(tbSysUser.getUserName()));
                    insertPublicServiceAwards(publicServiceAwardsDto);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、").
                            append(publicServiceAwardsDto.getUserName()).append("老师导入成功");
                }
                else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、").append(publicServiceAwardsDto.getUserName()).append("老师不存在");
                }
            }
            catch (Exception e) {
                failureNum++;
                String msg = "<br/>导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int insertPublicServiceAwards(PublicServiceAwardsDto dto) {
        if (dto == null || dto.getUserId() == null || StringUtils.isEmpty(dto.getUserName())){
            throw new ServiceException("参数错误");
        }
        TbTeacherAwards tbTeacherAwards = new TbTeacherAwards();
        //公共服务特殊
        tbTeacherAwards.setAwardId(ImportantConstants.PUBLIC_SERVICE_AWARD_ID);
        tbTeacherAwards.setRankPosition(new BigDecimal(1));
        tbTeacherAwards.setWeight(new BigDecimal(1));
        if (dto.getCredit() == null){
            //未达标
            if (dto.getTime().compareTo(new BigDecimal(30)) < 0){
                dto.setCredit(new BigDecimal(-3));
            }
            //达标
            else {
                BigDecimal subtract = dto.getTime().divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).subtract(new BigDecimal(1));
                dto.setCredit(subtract);
            }
        }
        if (dto.getCredit().compareTo(new BigDecimal(0)) < 0){
            tbTeacherAwards.setProjectName("公共服务未达标");
        }
        else {
            tbTeacherAwards.setProjectName("公共服务达标");
        }
        tbTeacherAwards.setUserId(dto.getUserId());
        tbTeacherAwards.setUserName(dto.getUserName());
        tbTeacherAwards.setRealCredit(dto.getCredit());
        return insertTbTeacherAwards(tbTeacherAwards);
    }

    public BigDecimal calculateCompetition(GuideStudentDetailBo guideStudentDetailBo){
        String level = guideStudentDetailBo.getLevel();
        String kind = guideStudentDetailBo.getKind();
        String priceLevel = guideStudentDetailBo.getPriceLevel();
        String guideTeacherRanking = guideStudentDetailBo.getGuideTeacherRanking();
        String ranking = guideStudentDetailBo.getRanking();
        //层次
        if (StringUtils.isEmpty(level)){
            throw new ServiceException(guideStudentDetailBo.getUsername()+"的层次为空");
        }
        //类别
        if (StringUtils.isEmpty(kind)){
            throw new ServiceException(guideStudentDetailBo.getUsername()+"的类别为空");
        }
        //竞赛等级
        if (StringUtils.isEmpty(priceLevel)){
            throw new ServiceException(guideStudentDetailBo.getUsername()+"的竞赛等级为空");
        }
        //排名
        if (StringUtils.isEmpty(ranking)){
            throw new ServiceException(guideStudentDetailBo.getUsername()+"的排名为空");
        }
        //指导教师排名
        if (StringUtils.isEmpty(guideTeacherRanking)){
            throw new ServiceException(guideStudentDetailBo.getUsername()+"的指导教师排名为空");
        }
        //手动参数
        BigDecimal weight = guideStudentDetailBo.getWeight() == null ? new BigDecimal(1) : guideStudentDetailBo.getWeight();
        return CompetitionLevelEnum.of(level).getRankPosition()
                .multiply(CompetitionKindEnum.of(kind).getRankPosition())
                .multiply(CompetitionRankingEnum.of(ranking).getRankPosition())
                .multiply(CompetitionGuideTeacherRankingEnum.of(guideTeacherRanking).getRankPosition())
                .multiply(CompetitionPriceLevelEnum.of(priceLevel).getRankPosition())
                .multiply(weight);
    }


}
