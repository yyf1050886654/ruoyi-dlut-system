package com.ruoyi.dlut.controller.v2;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.dlut.bo.TeacherCreditSumBo;
import com.ruoyi.dlut.domain.TbSysUser;
import com.ruoyi.dlut.domain.TbTeacherAwards;
import com.ruoyi.dlut.dto.ListTeacherDetailsTopDto;
import com.ruoyi.dlut.service.ITeacherDetailsServiceImpl;
import com.ruoyi.dlut.vo.EvaluatedTeacherResp;
import com.ruoyi.dlut.vo.TeacherAwardDetailResp;
import com.ruoyi.dlut.vo.TeacherCreditSumLeafBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/dlut/details")
public class TeacherDetailsController extends BaseController {

    @Autowired
    private ITeacherDetailsServiceImpl teacherDetailsService;

    /**
     * 分数详情分页
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(ListTeacherDetailsTopDto listTeacherDetailsTopDto) {
        startPage();
        List<TeacherCreditSumBo> list = teacherDetailsService.listAllTeacherCredit(listTeacherDetailsTopDto.getUserName());
        return getDataTable(list);
    }
    /**
     * 异步详情请求
     * @return
     */
    @GetMapping("/getDetailsByUserId/{userId}")
    public AjaxResult getDetailsByUserId(@PathVariable("userId")String userId) {
        List<TeacherCreditSumLeafBo> detailsByUserId = teacherDetailsService.getDetailsByUserId(userId);
        return AjaxResult.success(detailsByUserId);
    }

    /**
     * 导出教师奖项详情列表
     */
    @Log(title = "导出积分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,TeacherCreditSumBo teacherCreditSumBo) {
        List<TeacherCreditSumBo> list = teacherDetailsService.listAllTeacherCredit(null);
        ExcelUtil<TeacherCreditSumBo> util = new ExcelUtil<TeacherCreditSumBo>(TeacherCreditSumBo.class);
        util.exportExcel(response, list, "教师奖项详情数据");
    }

    /**
     * 查询所有被评教师
     */
    @GetMapping("/getAllTeacher")
    public AjaxResult getAllTeacher() {
        List<EvaluatedTeacherResp> tbSysUsers = teacherDetailsService.listAllTeacher();
        return AjaxResult.success(tbSysUsers);
    }
}
