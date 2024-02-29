/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.controller;

import com.ruoyi.dlut.constant.MessageConstant;
import com.ruoyi.dlut.entity.PageResult;
import com.ruoyi.dlut.entity.QueryPageBean;
import com.ruoyi.dlut.entity.Result;
import com.ruoyi.dlut.pojo.TeacherAwards;
import com.ruoyi.dlut.service.TeacherAwardsService;
import com.ruoyi.dlut.service.TeacherService;
import com.ruoyi.dlut.utils.QiniuUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/teacherAward")
public class TeacherAwardController {
    @Autowired
    private TeacherAwardsService teacherAwardsService;

    @Autowired
    private TeacherService teacherService;

    //分页查询
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean,
                               @RequestParam("sort") Integer sort,
                               @RequestParam("teacherUid") Integer teacherUid) throws UnsupportedEncodingException {
        return teacherAwardsService.findPage(queryPageBean,sort,teacherUid);
    }
    @PostMapping("/findPage4All/{sort}")
    public PageResult findPage4All(@RequestBody QueryPageBean queryPageBean,
                               @PathVariable("sort") Integer sort) throws UnsupportedEncodingException {
        return teacherAwardsService.findPage4All(queryPageBean,sort);
    }
    @PostMapping("/add/{sort}")
    public Result add(@RequestBody TeacherAwards teacherAwards,@PathVariable("sort") Integer sort){
        try{
            boolean temp=teacherAwardsService.add(teacherAwards,sort);
            if (!temp){
                return new Result(temp, MessageConstant.EXCEEDED_THE_MAXIMUM_SCORE);
            }
            else return new Result(temp, MessageConstant.ADD_TEACHER_AWARDS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TEACHER_AWARD_FAIL);
        }
    }
    //根据id删除检查项
    @DeleteMapping("/delete/{id}/{credit}/{teacherUid}")
    /*@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")*/
    public Result delete(@PathVariable("id") Integer id,
                         @PathVariable("credit") double credit,
                         @PathVariable("teacherUid") Integer teacherUid){
        try{
            teacherAwardsService.delete(id,credit,teacherUid);//发送请求
            return new Result(true, MessageConstant.DELETE_TEACHER_AWARD_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }
    //根据id删除检查项
    @DeleteMapping("/bulkDelete/{ids}/{credits}/{teachers}")
    /*@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")*/
    public Result bulkDelete(@PathVariable("ids")List<Integer> ids,
                             @PathVariable("credits")List<Double> credits,
                             @PathVariable("teachers")List<Integer> teachers){
        try{
            for (int i=0 ;i<ids.size();i++){
                teacherAwardsService.delete(ids.get(i),credits.get(i),teachers.get(i));//发送请求
            }
            return new Result(true, MessageConstant.DELETE_TEACHER_AWARD_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }
    @RequestMapping("/manageFindPage.do")
    public PageResult manageFindPage(@RequestBody QueryPageBean queryPageBean){
        return teacherAwardsService.manageFindPage(queryPageBean);
    }
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id){
        try{
            TeacherAwards awards = teacherAwardsService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_TEACHER_AWARD_SUCCESS,awards);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEACHER_AWARD_FAIL);
        }
    }
    @PutMapping("/edit")
    public Result edit(@RequestBody TeacherAwards awards){
        try{
            teacherAwardsService.edit(awards);//发送请求
            return new Result(true, MessageConstant.EDIT_TEACHER_AWARD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TEACHER_AWARD_FAIL);
        }
    }

    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile,
                         @RequestParam("currentTeacher") String name){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            /*String fileName = name+"/"+UUID.randomUUID().toString() + suffix;*/
            String fileName = name+"/"+originalFilename;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.ZIP_UPLOAD_SUCCESS);
            result.setData(fileName);
            //jedisPool.getResource().sadd(RedisConstant.UPLOAD_ZIP_RESOURCES,fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.ZIP_UPLOAD_FAIL);
        }
    }

    @GetMapping("/downLoadExcel/{sorts}/{teachers}")
    public Result downLoadExcel(@PathVariable("sorts") List<Integer> sorts,
                         @PathVariable("teachers") List<Integer> teachers,
                               HttpServletResponse response){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (int i=0;i<teachers.size();i++){
                //一个老师对应的所选种类的全部项目
                List<TeacherAwards> teacherAwardsList = teacherAwardsService.findAwardsByTeacherAndSorts(sorts,teachers.get(i));
                XSSFSheet sheet = workbook.createSheet(teacherService.findById(teachers.get(i)).getName());
                XSSFRow row = sheet.createRow(0);
                row.createCell(0).setCellValue("姓名");
                row.createCell(1).setCellValue("奖项名称");
                row.createCell(2).setCellValue("项目名称");
                row.createCell(3).setCellValue("排位折分系数");
                row.createCell(4).setCellValue("调整系数");
                row.createCell(5).setCellValue("积分");
                row.createCell(6).setCellValue("开始日期");
                row.createCell(7).setCellValue("结束日期");
                row.createCell(8).setCellValue("备注");
                row.createCell(9).setCellValue("下载链接");

                int count=1;
                for (TeacherAwards teacherAwards:teacherAwardsList){
                    XSSFRow rowTemp = sheet.createRow(count++);
                    rowTemp.createCell(0).setCellValue(teacherAwards.getTeacher().getName());
                    rowTemp.createCell(1).setCellValue(teacherAwards.getAwards().getAwardName());
                    rowTemp.createCell(2).setCellValue(teacherAwards.getProjectName());
                    rowTemp.createCell(3).setCellValue(teacherAwards.getRankPosition());
                    rowTemp.createCell(4).setCellValue(teacherAwards.getWeight());
                    rowTemp.createCell(5).setCellValue(teacherAwards.getRealCredit());
                    rowTemp.createCell(6).setCellValue(teacherAwards.getBeginDate());
                    rowTemp.createCell(7).setCellValue(teacherAwards.getEndDate());
                    rowTemp.createCell(8).setCellValue(teacherAwards.getInfo());
                    rowTemp.createCell(9).setCellValue(teacherAwards.getZip());
                }
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DOWNLOAD_EXCEL_FAIL);
        }
    }
    @RequestMapping("/remove.do")
    public Result remove(@RequestParam("name") String name) throws UnsupportedEncodingException {

        name=new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        try{
            QiniuUtils.deleteFileFromQiniu(name);
            Result result = new Result(true, MessageConstant.ZIP_DELETE_SUCCESS);
            result.setData(name);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.ZIP_DELETE_FAIL);
        }
    }

}
