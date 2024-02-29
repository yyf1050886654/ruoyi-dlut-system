package com.ruoyi.dlut.service;

import com.ruoyi.dlut.pojo.User;

/**
 * 用户服务接口
 */
public interface UserService {
    public User findByUsername(String username);
    public void add(User user);
    public void addRole(int userId,int roleId);
    public void delete(int id);
    public void deleteRole(int id);
    public void changePassword(String password,int uid);

}