/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.controller.v1;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.dlut.constant.MessageConstant;
import com.ruoyi.dlut.entity.Result;
import com.ruoyi.dlut.pojo.Teacher;
import com.ruoyi.dlut.pojo.User;
import com.ruoyi.dlut.service.MenuService;
import com.ruoyi.dlut.service.TeacherService;
import com.ruoyi.dlut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MenuService menuService;
    //获取当前登录用户的用户名
    @RequestMapping("/getTeacher")
    public Result getTeacher()throws Exception{
        try{
            SysUser user = SecurityUtils.getLoginUser().getUser();
            User thisUser = userService.findByUsername(user.getUserName());
            Teacher currentTeacher = teacherService.findById(thisUser.getId());
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,currentTeacher);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
    @GetMapping("/getAllTeacher")
    public Result getAllTeacher()throws Exception{
        try{
            return new Result(true, MessageConstant.QUERY_ALL_TEACHER_SUCCESS,teacherService.findAll());
        }catch (Exception e){
            return new Result(false, MessageConstant.QUERY_ALL_TEACHER_SUCCESS);
        }
    }
    /*@RequestMapping("/getMenu.do")
    public Result getMenu()throws Exception{
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User thisUser = userService.findByUsername(user.getUsername());
            List<Menu> menus = menuService.getMenuByRoles(thisUser.getRoles());
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,menus);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_MENU_FAIL,e.getMessage());
        }
    }*/
    @PostMapping("/register/{roleId}")
    @PreAuthorize("@ss.hasPermi('teacher:manage:add')")
    @Log(title = "教师管理", businessType = BusinessType.INSERT)
    public Result register(@RequestBody User user,@PathVariable("roleId")int roleId)throws Exception{
        try{
            userService.add(user);
            int uid = userService.findByUsername(user.getUsername()).getId();
            user.getTeacher().setUid(uid);
            userService.addRole(uid,roleId);
            teacherService.add(user.getTeacher());//发送请求
            return new Result(true, MessageConstant.ADD_TEACHER_AWARDS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TEACHER_AWARD_FAIL);
        }
    }
    @RequestMapping("/delete.do")
    public Result delete(String id)throws Exception{
        int temp = Integer.parseInt(id);
        try{
            teacherService.delete(temp);//发送请求
            userService.deleteRole(temp);
            userService.delete(temp);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }
    @RequestMapping("/changePassword.do")
    public Result changePassword(@RequestParam("psw") String password,
                                 @RequestParam("id") Integer id){
        try{
            userService.changePassword(password,id);
            return new Result(true, MessageConstant.CHANGE_PASSWORD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.CHANGE_PASSWORD_FAIL);
        }
    }

}