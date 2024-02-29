package com.ruoyi.dlut.mapper;

import com.ruoyi.dlut.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;
@Mapper
public interface MenuDao {
    public Set<Menu> findByRoleId(int roleId);
}
