/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.ruoyi.dlut.mapper;

import com.ruoyi.dlut.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;
@Mapper
public interface RoleDao {
    public Set<Role> findByUserId(int id);
}
