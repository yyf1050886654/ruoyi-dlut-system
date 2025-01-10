package com.ruoyi.dlut.controller.v2;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.dlut.bo.GuideStudentDetailBo;
import com.ruoyi.dlut.bo.PaperAndProjectBo;
import com.ruoyi.dlut.dto.TeacherAwardDetailDto;
import com.ruoyi.dlut.vo.TeacherAwardDetailResp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.ruoyi.dlut.service.ITbTeacherAwardsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 教师奖项详情Controller
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@RestController
@RequestMapping("/dlut/teacherAwards")
public class TbTeacherAwardsController extends BaseController
{
    @Autowired
    private ITbTeacherAwardsService tbTeacherAwardsService;

    /**
     * 查询教师奖项详情列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody TeacherAwardDetailDto dto)
    {
         return  tbTeacherAwardsService.selectTbTeacherAwardsList(dto,true);
    }

    /**
     * 导出教师奖项详情列表
     *//*
    @PreAuthorize("@ss.hasPermi('dlut:teacherAwards:export')")
    @Log(title = "教师奖项详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbTeacherAwards tbTeacherAwards)
    {
        List<TbTeacherAwards> list = tbTeacherAwardsService.selectTbTeacherAwardsList(tbTeacherAwards);
        ExcelUtil<TbTeacherAwards> util = new ExcelUtil<TbTeacherAwards>(TbTeacherAwards.class);
        util.exportExcel(response, list, "教师奖项详情数据");
    }*/

    /**
     * 获取教师奖项详情详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbTeacherAwardsService.selectTbTeacherAwardsById(id));
    }

    /**
     * 新增教师奖项详情
     */
    @Log(title = "教师奖项详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbTeacherAwards tbTeacherAwards)
    {
        return toAjax(tbTeacherAwardsService.insertTbTeacherAwards(tbTeacherAwards));
    }

    /**
     * 修改教师奖项详情
     */
    @Log(title = "教师奖项详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbTeacherAwards tbTeacherAwards)
    {
        return toAjax(tbTeacherAwardsService.updateTbTeacherAwards(tbTeacherAwards));
    }

    /**
     * 删除教师奖项详情
     */
    @Log(title = "教师奖项详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbTeacherAwardsService.deleteTbTeacherAwardsByIds(ids));
    }

    /**
     * 导入
     */
    @Log(title = "批量上传教师奖项", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<TbTeacherAwards> util = new ExcelUtil<>(TbTeacherAwards.class);
        List<TbTeacherAwards> tbTeacherAwards = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = tbTeacherAwardsService.batchImportData(tbTeacherAwards,operName);
        return AjaxResult.success(message);
    }
    /**
     * 导入
     */
    @Log(title = "批量上传指导竞赛", businessType = BusinessType.IMPORT)
    @PostMapping("/importCompetition")
    public AjaxResult importCompetition(MultipartFile file) throws Exception
    {
        ExcelUtil<GuideStudentDetailBo> util = new ExcelUtil<>(GuideStudentDetailBo.class);
        List<GuideStudentDetailBo> guideStudentDetailBos = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = tbTeacherAwardsService.batchImportCompetition(guideStudentDetailBos,operName);
        return AjaxResult.success(message);
    }

    @Log(title = "论文及项目上传", businessType = BusinessType.IMPORT)
    @PostMapping("/importPaperAndProject")
    public AjaxResult importPaperAndProject(MultipartFile file) throws Exception
    {
        ExcelUtil<PaperAndProjectBo> util = new ExcelUtil<>(PaperAndProjectBo.class);
        List<PaperAndProjectBo> paperAndProjectBos = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = tbTeacherAwardsService.batchImportPaperAndProject(paperAndProjectBos,operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importDataTemplate")
    public void importDataTemplate(HttpServletResponse response)
    {
        ExcelUtil<TbTeacherAwards> util = new ExcelUtil<TbTeacherAwards>(TbTeacherAwards.class);
        util.importTemplateExcel(response,"业绩详情");
    }

    @PostMapping("/importCompetitionTemplate")
    public void importCompetitionTemplate(HttpServletResponse response)
    {
        ExcelUtil<GuideStudentDetailBo> util = new ExcelUtil<GuideStudentDetailBo>(GuideStudentDetailBo.class);
        util.importTemplateExcel(response,"竞赛详情");
    }

    @PostMapping("/importPaperAndProjectTemplate")
    public void importPaperAndProjectTemplate(HttpServletResponse response)
    {
        ExcelUtil<PaperAndProjectBo> util = new ExcelUtil<PaperAndProjectBo>(PaperAndProjectBo.class);
        util.importTemplateExcel(response,"论文与项目");
    }
}
