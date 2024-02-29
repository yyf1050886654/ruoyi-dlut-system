/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.controller;

import com.ruoyi.dlut.constant.MessageConstant;
import com.ruoyi.dlut.entity.Result;
import com.ruoyi.dlut.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
public class TextController {
    @Autowired
    private TextService textService;
    @GetMapping("/getInfo/{kind}/{sort}")
    public Result getInfo(@PathVariable("sort") Integer sort,
                          @PathVariable("kind") Integer kind){
        try{

            return new Result(true, MessageConstant.QUERY_TEXT_SUCCESS,textService.getText(kind,sort));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TEXT_FAIL);
        }
    }
}
