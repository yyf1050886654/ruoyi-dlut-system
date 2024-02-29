package com.ruoyi.dlut.service.Impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.dlut.mapper.MenuDao;
import com.ruoyi.dlut.pojo.Menu;
import com.ruoyi.dlut.pojo.Role;
import com.ruoyi.dlut.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@DataSource(value = DataSourceType.SLAVE)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Menu> getMenuByRoles(Set<Role> roles) {
        if(roles != null && !roles.isEmpty()){
            Set<Menu> menus = null;
            Set<Menu> RootMenuSet = new HashSet<>();
            for(Role role : roles){
                Integer roleId = role.getId();
                menus = menuDao.findByRoleId(roleId);
            }
            for(Menu menu : menus){
                Integer temp = menu.getParentMenuId();
                if (temp == null){
                    RootMenuSet.add(menu);
                }
            }
            for(Menu menu : menus){
                Integer temp = menu.getParentMenuId();
                if (temp != null){
                    findMenuById(RootMenuSet,menu);
                }
            }
            return set2List(RootMenuSet);
        }else return null;
    }
    public List<Menu> set2List(Set<Menu> menus){
        List<Menu> menuList = new ArrayList<>();
        int length = menus.size();
        for (int i=0;i<length;i++){
            int min = Integer.MAX_VALUE;
            Menu temp = null;
            for (Menu menu: menus){
                if (menu.getPriority()<min){
                    min = menu.getPriority();
                    temp = menu;
                }
            }
            menus.remove(temp);
            menuList.add(temp);
        }

        return menuList;
    }

    public void findMenuById(Set<Menu> menus,Menu m){
        for(Menu menu : menus){
            if (Objects.equals(menu.getId(), m.getParentMenuId())){
                menu.addChildren(m);
            }
        }
    }
}
