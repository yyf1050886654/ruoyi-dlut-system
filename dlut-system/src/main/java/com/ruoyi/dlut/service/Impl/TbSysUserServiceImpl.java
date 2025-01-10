package com.ruoyi.dlut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.dlut.domain.TbSysUser;
import com.ruoyi.dlut.mapper.TbSysUserMapper;
import com.ruoyi.dlut.service.ITbSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TbSysUserServiceImpl extends ServiceImpl<TbSysUserMapper, TbSysUser> implements ITbSysUserService {
    @Override
    public List<TbSysUser> selectUserByDepartmentId(Long deptId) {
        LambdaQueryWrapper<TbSysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbSysUser::getDeptId,deptId);
        return list(lambdaQueryWrapper);
    }

    @Override
    public IPage<TbSysUser> pageSelectUserByDepartmentId(Long deptId,int pageNum,int pageSize) {
        LambdaQueryWrapper<TbSysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbSysUser::getDeptId,deptId);
        IPage<TbSysUser> page = new Page<>(pageNum,pageSize);
        return page(page, lambdaQueryWrapper);
    }

    @Override
    public TbSysUser selectUserByNickName(String name) {
        LambdaQueryWrapper<TbSysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TbSysUser::getNickName,name);
        return getOne(lambdaQueryWrapper);
    }
}
