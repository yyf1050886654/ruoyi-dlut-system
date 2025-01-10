package com.ruoyi.dlut.service;

import java.util.List;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.dlut.bo.GuideStudentDetailBo;
import com.ruoyi.dlut.bo.PaperAndProjectBo;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dlut.dto.TeacherAwardDetailDto;
import com.ruoyi.dlut.vo.TeacherAwardDetailResp;

/**
 * 教师奖项详情Service接口
 *
 * @author yangyifan
 * @date 2025-01-07
 */
public interface ITbTeacherAwardsService extends IService<TbTeacherAwards> {
    /**
     * 查询教师奖项详情
     *
     * @param id 教师奖项详情主键
     * @return 教师奖项详情
     */
    public TbTeacherAwards selectTbTeacherAwardsById(Long id);

    /**
     * 查询教师奖项详情列表
     *
     * @param dto 教师奖项详情
     * @return 教师奖项详情集合
     */
    public TableDataInfo selectTbTeacherAwardsList(TeacherAwardDetailDto dto,boolean isPage);

    /**
     * 新增教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    public int insertTbTeacherAwards(TbTeacherAwards tbTeacherAwards);

    /**
     * 修改教师奖项详情
     *
     * @param tbTeacherAwards 教师奖项详情
     * @return 结果
     */
    public int updateTbTeacherAwards(TbTeacherAwards tbTeacherAwards);

    /**
     * 批量删除教师奖项详情
     *
     * @param ids 需要删除的教师奖项详情主键集合
     * @return 结果
     */
    public int deleteTbTeacherAwardsByIds(Long[] ids);

    /**
     * 删除教师奖项详情信息
     *
     * @param id 教师奖项详情主键
     * @return 结果
     */
    public int deleteTbTeacherAwardsById(Long id);

    /**
     * 找单独一个教师的详情
     * @param id 教师id
     * @return 奖项详情
     */
    public List<TbTeacherAwards> findDetailByUserId(Long id);

    /**
     * 批量导入数据
     * @param dataList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    public String batchImportData(List<TbTeacherAwards> dataList, String operName);

    /**
     * 批量导入竞赛数据
     * @param dataList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    public String batchImportCompetition(List<GuideStudentDetailBo> dataList, String operName);

    /**
     * 批量导入论文及项目上传
     * @param dataList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    public String batchImportPaperAndProject(List<PaperAndProjectBo> dataList, String operName);

}
