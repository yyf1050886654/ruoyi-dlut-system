package com.ruoyi.dlut.mapper;

import com.ruoyi.dlut.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;
@Mapper
public interface PermissionDao {
    public Set<Permission> findByRoleId(int roleId);
}