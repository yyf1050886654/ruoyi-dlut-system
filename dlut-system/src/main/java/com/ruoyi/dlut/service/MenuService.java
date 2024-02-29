package com.ruoyi.dlut.service;

import com.ruoyi.dlut.pojo.Menu;
import com.ruoyi.dlut.pojo.Role;

import java.util.List;
import java.util.Set;


public interface MenuService {
    public List<Menu> getMenuByRoles(Set<Role> roles);
}
