package com.ruoyi.dlut.controller.v2;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.dlut.bo.AwardsCategoryDetailsBo;
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
import com.ruoyi.dlut.domain.TbAwardsCategory;
import com.ruoyi.dlut.service.ITbAwardsCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 奖项详情Controller
 *
 * @author yangyifan
 * @date 2025-01-07
 */
@RestController
@RequestMapping("/dlut/award")
public class TbAwardsCategoryController extends BaseController
{
    @Autowired
    private ITbAwardsCategoryService tbAwardsCategoryService;

    /**
     * 查询奖项详情列表
     */

    @GetMapping("/list")
    public TableDataInfo list(TbAwardsCategory tbAwardsCategory)
    {
        startPage();
        List<TbAwardsCategory> list = tbAwardsCategoryService.selectTbAwardsCategoryList(tbAwardsCategory);
        return getDataTable(list);
    }

    /**
     * 导出奖项详情列表
     */

    @Log(title = "奖项详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbAwardsCategory tbAwardsCategory)
    {
        List<TbAwardsCategory> list = tbAwardsCategoryService.selectTbAwardsCategoryList(tbAwardsCategory);
        ExcelUtil<TbAwardsCategory> util = new ExcelUtil<TbAwardsCategory>(TbAwardsCategory.class);
        util.exportExcel(response, list, "奖项详情数据");
    }

    /**
     * 获取奖项详情详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbAwardsCategoryService.selectTbAwardsCategoryById(id));
    }

    /**
     * 新增奖项详情
     */

    @Log(title = "奖项详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbAwardsCategory tbAwardsCategory)
    {
        return toAjax(tbAwardsCategoryService.insertTbAwardsCategory(tbAwardsCategory));
    }

    /**
     * 修改奖项详情
     */
    @Log(title = "奖项详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbAwardsCategory tbAwardsCategory)
    {
        return toAjax(tbAwardsCategoryService.updateTbAwardsCategory(tbAwardsCategory));
    }

    /**
     * 删除奖项详情
     */
    @Log(title = "奖项详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbAwardsCategoryService.deleteTbAwardsCategoryByIds(ids));
    }


    /**
     * 聚合查找展示
     * @param kind 小类
     * @param sort 大类
     * @return 聚合结果
     */
    @GetMapping("/findByKind/{kind}/{sort}")
    public AjaxResult remove(@PathVariable("kind")Integer kind,
                             @PathVariable("sort")Integer sort) {
        List<AwardsCategoryDetailsBo> awardsCategoryDetailsBoList = tbAwardsCategoryService.listCategoryByKindAndSort(kind, sort);
        return AjaxResult.success(awardsCategoryDetailsBoList);
    }
}
