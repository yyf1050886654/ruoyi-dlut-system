package com.ruoyi.dlut.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.dlut.entity.PageResult;
import com.ruoyi.dlut.entity.QueryPageBean;
import com.ruoyi.dlut.mapper.AwardDao;
import com.ruoyi.dlut.mapper.TeacherAwardDao;
import com.ruoyi.dlut.mapper.TeacherDao;
import com.ruoyi.dlut.pojo.Awards;
import com.ruoyi.dlut.pojo.Teacher;
import com.ruoyi.dlut.pojo.TeacherAwards;
import com.ruoyi.dlut.service.TeacherAwardsService;
import com.ruoyi.dlut.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@DataSource(value = DataSourceType.SLAVE)
public class TeacherAwardsServiceImpl implements TeacherAwardsService {
    @Autowired
    private TeacherAwardDao teacherAwardDao;
    @Autowired
    private AwardDao awardDao;
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean, int sort, int teacherUid) throws UnsupportedEncodingException {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//ThreadLocal
        if (queryString != null){
            queryString = "%"+queryString+"%";
        }
        PageHelper.startPage(currentPage,pageSize);//分页插件，会在执行sql之前将分页关键字追加到SQL后面
        Page<TeacherAwards> page = teacherAwardDao.findByCondition(queryString,sort,teacherUid);
        List<TeacherAwards> teacherAwardsList = page.getResult();
        for (TeacherAwards award:teacherAwardsList){
            Awards temp = awardDao.findById(award.getAwardsId());
            award.setAwards(temp);

            award.setZip(QiniuUtils.downLoadFileFromQiniu(award.getZip()));
        }
        return new PageResult(page.getTotal(),teacherAwardsList);
    }

    @Override
    public PageResult findPage4All(QueryPageBean queryPageBean, int sort) throws UnsupportedEncodingException {
        if (!checkPermission(sort)){
            return null;
        }
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//ThreadLocal
        if (queryString != null){
            queryString = "%"+queryString+"%";
        }
        PageHelper.startPage(currentPage,pageSize);//分页插件，会在执行sql之前将分页关键字追加到SQL后面
        Page<TeacherAwards> page = teacherAwardDao.findByCondition4All(queryString,sort);
        List<TeacherAwards> teacherAwardsList = page.getResult();
        for (TeacherAwards award:teacherAwardsList){
            Awards temp = awardDao.findById(award.getAwardsId());
            award.setAwards(temp);
            Teacher teacher = teacherDao.findById(award.getTeacherUid());
            award.setTeacher(teacher);
            award.setZip(QiniuUtils.downLoadFileFromQiniu(award.getZip()));
        }
        return new PageResult(page.getTotal(),teacherAwardsList);
    }

    @Override
    public PageResult manageFindPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//ThreadLocal
        PageHelper.startPage(currentPage,pageSize);//分页插件，会在执行sql之前将分页关键字追加到SQL后面
        Page<TeacherAwards> page = teacherAwardDao.manageFindByCondition(queryString);
        List<TeacherAwards> teacherAwardsList = page.getResult();
        for (TeacherAwards award:teacherAwardsList){
            Awards temp = awardDao.findById(award.getAwardsId());
            award.setAwards(temp);
            Teacher teacher = teacherDao.findById(award.getTeacherUid());
            award.setTeacher(teacher);
        }
        return new PageResult(page.getTotal(),teacherAwardsList);
    }

    @Override
    public List<TeacherAwards> findAwardsByTeacherAndSorts(List<Integer> sorts, int teacherUid) {
        System.out.println(sorts);
        System.out.println(teacherUid);
        List<TeacherAwards> sumList = new ArrayList<>();
        for (int sort:sorts){
            System.out.println(sort);
            List<TeacherAwards> teacherAwardsList = teacherAwardDao.findAwardsByTeacherAndSorts(sort,teacherUid);
            for (TeacherAwards award:teacherAwardsList){
                Awards temp = awardDao.findById(award.getAwardsId());
                award.setAwards(temp);
                Teacher teacher = teacherDao.findById(award.getTeacherUid());
                award.setTeacher(teacher);
            }
            sumList.addAll(teacherAwardsList);
        }
        return sumList;
    }

    @Override
    public boolean add(TeacherAwards teacherAwards,int sort) {

        if (sort == 5){
            String temp = teacherAwardDao.sortXSum(sort,teacherAwards.getTeacherUid());
            if (temp != null){
                double sort5Sum = Double.parseDouble(temp);
                if (sort5Sum+teacherAwards.getRealCredit() > 2){
                    return false;
                }
            }
        }
        if (sort == 6){
            String temp = teacherAwardDao.sortXSum(sort,teacherAwards.getTeacherUid());
            if (temp != null){
                double sort5Sum = Double.parseDouble(temp);
                if (sort5Sum+teacherAwards.getRealCredit() > 4){
                    return false;
                }
            }
        }
        teacherAwardDao.add(teacherAwards);
        /*if (!Objects.equals(teacherAwards.getZip(), "")){
            savePic2Redis(teacherAwards.getZip());
        }*/
        teacherDao.addCredit(teacherAwards.getTeacherUid(),teacherAwards.getRealCredit());
        return true;
    }

    @Override
    public void delete(Integer id,double credit,Integer teacherUid) {
        String zipName = teacherAwardDao.findZipNameById(id);
        /*if (!Objects.equals(zipName, "")){
            deletePic2Redis(zipName);
        }*/
        //QiniuUtils.deleteFileFromQiniu(zipName);
        teacherAwardDao.deleteById(id);
        teacherDao.deleteCredit(teacherUid,credit);
    }

    @Override
    public double addCreditBySort(String teacherName, int sort) {
        String temp = teacherAwardDao.sortXSum(sort,teacherDao.findByName(teacherName));
        if (temp != null){
            return Double.parseDouble(temp);
        }
        return 0;
    }

    @Override
    public TeacherAwards findById(Integer id) {
        TeacherAwards teacherAwards = teacherAwardDao.findById(id);
        Awards temp = awardDao.findById(teacherAwards.getAwardsId());
        teacherAwards.setAwards(temp);
        Teacher teacher = teacherDao.findById(teacherAwards.getTeacherUid());
        teacherAwards.setTeacher(teacher);
        return teacherAwards;
    }

    @Override
    public void edit(TeacherAwards awards) {
        teacherAwardDao.edit(awards);
    }

    public boolean checkPermission(Integer sort){
        String permission = "manage:"+sort+":all";
        return SecurityUtils.hasPermi(permission);
    }
}

