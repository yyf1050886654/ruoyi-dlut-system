package com.ruoyi.dlut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.dlut.domain.TbSysUser;

import java.util.List;

public interface ITbSysUserService extends IService<TbSysUser> {
    public List<TbSysUser> selectUserByDepartmentId(Long deptId);

    public IPage<TbSysUser> pageSelectUserByDepartmentId(Long deptId, int pageNum, int pageSize);

    /**
     * 这个表里的教师姓名字段是nick_name
     * @param name
     * @return
     */
    public TbSysUser selectUserByNickName(String name);
}
