package com.ruoyi.dlut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TextDao {
    public String getText(@Param(value = "sort")int sort,
                          @Param(value = "kind")int kind);
}
