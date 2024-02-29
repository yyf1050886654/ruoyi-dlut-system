/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.dlut.constant.MessageConstant;
import com.ruoyi.dlut.entity.PageResult;
import com.ruoyi.dlut.entity.QueryPageBean;
import com.ruoyi.dlut.entity.Result;
import com.ruoyi.dlut.pojo.Teacher;
import com.ruoyi.dlut.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    /*//新增
    @RequestMapping("/add.do")
    public Result add(@RequestBody Teacher awards){
        try{
            teacherService.add(awards);//发送请求
            return new Result(true, MessageConstant.ADD_TEACHER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TEACHER_FAIL);
        }
    }
*/
    //分页查询
    @PreAuthorize("@ss.hasPermi('teacher:manage:list')")
    @PostMapping("/findPage/{roleId}")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean,
                               @PathVariable("roleId")int roleId){
        return teacherService.findPage(queryPageBean,roleId);
    }

    //根据id删除检查项
    @PreAuthorize("@ss.hasPermi('teacher:manage:delete')")
    @Log(title = "教师管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        try{
            teacherService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_TEACHER_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }

    //根据id查询检查项
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id")Integer id){
        try{
            Teacher teacher = teacherService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_TEACHER_SUCCESS,teacher);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_FAIL);
        }
    }

    //编辑
    @PutMapping("/edit")
    @PreAuthorize("@ss.hasPermi('teacher:manage:edit')")
    @Log(title = "教师管理", businessType = BusinessType.UPDATE)
    public Result edit(@RequestBody Teacher teacher){
        try{
            teacherService.edit(teacher);//发送请求
            return new Result(true, MessageConstant.EDIT_TEACHER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TEACHER_FAIL);
        }
    }

    //查询所有老师
    @RequestMapping("/findAll.do")
    public Result findAll(){
        try{
            List<Teacher> list = teacherService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_TEACHER_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_FAIL);
        }
    }
    @GetMapping("/findRank/{credit}")
    public Result findRank(@PathVariable("credit") double credit){
        try{
            return new Result(true, MessageConstant.QUERY_TEACHER_RANK_SUCCESS,teacherService.findRank(credit));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_RANK_FAIL);
        }
    }

}
