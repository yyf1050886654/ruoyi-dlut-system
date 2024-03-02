/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.controller.v1;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.dlut.constant.MessageConstant;
import com.ruoyi.dlut.entity.PageResult;
import com.ruoyi.dlut.entity.QueryPageBean;
import com.ruoyi.dlut.entity.Result;
import com.ruoyi.dlut.pojo.Awards;
import com.ruoyi.dlut.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@Anonymous
@RequestMapping("/awards")
public class AwardController {
    @Autowired
    private AwardService awardService;
    //新增

    @PreAuthorize("@ss.hasPermi('dlut:awards:add')")
    @Log(title = "指标管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public Result add(@RequestBody Awards awards){
        try{
            awardService.add(awards);//发送请求
            return new Result(true, MessageConstant.ADD_AWARDS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_AWARD_FAIL);
        }
    }


    //分页查询
    @PreAuthorize("@ss.hasPermi('dlut:awards:list')")
    @PostMapping("/findPage/{sort}")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean,@PathVariable("sort")Integer sort){
        return awardService.findPage(queryPageBean,sort);
    }

    //根据id删除检查项
    @PreAuthorize("@ss.hasPermi('dlut:awards:remove')")
    @Log(title = "指标管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    /*@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")*/
    public Result delete(@PathVariable("id") Integer id){
        try{
            awardService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_AWARD_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }


    //根据id查询检查项
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id){
        try{
            Awards awards = awardService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awards);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    @GetMapping("/findByKind/{kind}/{sort}")
    public Result findById(@PathVariable("kind")Integer kind, @PathVariable("sort")Integer sort){
        try{
            List<Awards> awards=awardService.findByKind(kind,sort);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awards);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    //编辑

    @PreAuthorize("@ss.hasPermi('dlut:awards:edit')")
    @Log(title = "指标管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public Result edit(@RequestBody Awards awards){
        try{
            awardService.edit(awards);//发送请求
            return new Result(true, MessageConstant.EDIT_AWARD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_AWARD_FAIL);
        }
    }

    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<Awards> list = awardService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    //根据老师uid找到他对应的奖项id
    @RequestMapping("/findAwardsIdsByTeacherUId")
    public Result findAwardsIdsByTeacherUId(Integer TeacherUid){
        try{
            List<Integer> list = awardService.findAwardsIdsByTeacherUId(TeacherUid);
            return new Result(true,MessageConstant.QUERY_AWARD_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_AWARD_FAIL);
        }
    }
    @GetMapping("/findByKindUnique/{kind}/{sort}")
    public Result findByIdUnique(@PathVariable("kind")Integer kind, @PathVariable("sort")Integer sort){
        try{
            List<String> awardsName=awardService.findByKindUnique(kind,sort);//发送请求
            return new Result(true, MessageConstant.QUERY_AWARD_SUCCESS,awardsName);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_AWARD_FAIL);
        }
    }

}
