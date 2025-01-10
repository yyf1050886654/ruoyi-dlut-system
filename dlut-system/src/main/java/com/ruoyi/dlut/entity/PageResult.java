package com.ruoyi.dlut.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable{

    private Long total;//总记录数
    private List<T> rows;//当前页结果
}
