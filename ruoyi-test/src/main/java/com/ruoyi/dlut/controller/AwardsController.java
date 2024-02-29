package com.ruoyi.dlut.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.dlut.domain.Awards;
import com.ruoyi.dlut.service.IAwardsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 指标管理Controller
 * 
 * @author yyf
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/dlut/awards")
public class AwardsController extends BaseController
{
    @Autowired
    private IAwardsService awardsService;

    /**
     * 查询指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:list')")
    @GetMapping("/list")
    public TableDataInfo list(Awards awards)
    {
        startPage();
        List<Awards> list = awardsService.selectAwardsList(awards);
        return getDataTable(list);
    }

    /**
     * 导出指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:export')")
    @Log(title = "指标管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Awards awards)
    {
        List<Awards> list = awardsService.selectAwardsList(awards);
        ExcelUtil<Awards> util = new ExcelUtil<Awards>(Awards.class);
        util.exportExcel(response, list, "指标管理数据");
    }

    /**
     * 获取指标管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(awardsService.selectAwardsById(id));
    }

    /**
     * 新增指标管理
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:add')")
    @Log(title = "指标管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Awards awards)
    {
        return toAjax(awardsService.insertAwards(awards));
    }

    /**
     * 修改指标管理
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:edit')")
    @Log(title = "指标管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Awards awards)
    {
        return toAjax(awardsService.updateAwards(awards));
    }

    /**
     * 删除指标管理
     */
    @PreAuthorize("@ss.hasPermi('dlut:awards:remove')")
    @Log(title = "指标管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(awardsService.deleteAwardsByIds(ids));
    }
}
