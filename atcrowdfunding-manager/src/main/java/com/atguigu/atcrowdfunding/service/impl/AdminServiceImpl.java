package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.MyException.LoginException;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TAdminRoleMapper;
import com.atguigu.atcrowdfunding.pojo.TAdmin;
import com.atguigu.atcrowdfunding.pojo.TAdminExample;
import com.atguigu.atcrowdfunding.pojo.TAdminRole;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层
 * @Author: ouyong
 * @Date: 2020/07/25 19:13
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TAdminMapper adminMapper;

    @Autowired
    private TAdminRoleMapper adminRoleMapper;

    @Override
    public TAdmin getAdmin(String loginacct, String userpswd) throws  LoginException{

        TAdminExample example = new TAdminExample();
        // 创建查询条件
        TAdminExample.Criteria criteria = example.createCriteria();

        // 设置查询条件
        criteria.andLoginacctEqualTo(loginacct);

        // 调用mapper中方法执行查询
        List<TAdmin> adminList = adminMapper.selectByExample(example);

        // 判断集合是否为空，为空表示没有找到用户名
        if (adminList == null || adminList.size() == 0) {
            // 跑出异常，提示用户名称不存在
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }

        TAdmin admin = adminList.get(0);
        // 判断加密后的密码是否和数据库中存储的密码一致
        if(!(admin.getUserpswd().equals(MD5Util.digest(userpswd)))) {
            throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
        }

        return admin;
    }

    @Override
    public PageInfo<TAdmin> listPage(Map<String, Object> map) {

        TAdminExample example = new TAdminExample();

        // 设置查询条件
        String condition = (String) map.get("condition");

        if (!StringUtils.isEmpty(condition)) {
            // 创建查询条件
            TAdminExample.Criteria criteria1 = example.createCriteria();

            // 执行登录用户名模糊
            criteria1.andLoginacctLike("%" + condition + "%");

            // 创建查询条件
            TAdminExample.Criteria criteria2 = example.createCriteria();

            // 执行登录用户名模糊
            criteria2.andUsernameLike("%" + condition + "%");

            // 创建查询条件
            TAdminExample.Criteria criteria3 = example.createCriteria();

            // 执行登录用户名模糊
            criteria3.andEmailLike("%" + condition + "%");

            example.or(criteria1);
            example.or(criteria2);
            example.or(criteria3);

        }
        // 执行查询
        List<TAdmin> pageList = adminMapper.selectByExample(example);

        // 设置分页条显示格式
        int navigatePages = 5;

        // 将查询结果封装到PageInfo
        PageInfo<TAdmin> page = new PageInfo<>(pageList,navigatePages);
        return page;
    }

    @Override
    public void saveAdmin(TAdmin admin) {
        // 给没有值得自动设置默认值，数据库表中所有字段不能为空
        admin.setUserpswd(MD5Util.digest(Const.DEFALUT_PASSWORD));

        admin.setCreatetime(DateUtil.getFormatTime());

        adminMapper.insertSelective(admin);
    }

    @Override
    public void deleteAdminById(Integer id) {

        adminMapper.deleteByPrimaryKey(id);


    }

    @Override
    public void updateAdminById(TAdmin admin) {

        adminMapper.updateByPrimaryKeySelective(admin);

    }

    @Override
    public TAdmin getAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteBatchAdmin(String ids) {

        List<Integer> list = new ArrayList<>();
        String[] splits = ids.split(",");
        for (String split : splits) {
            // 将字符串id转换为integer类型
            int id = Integer.parseInt(split);
            list.add(id);
        }

        // 创建查询条件对象
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        // 传入转换后的集合
        criteria.andIdIn(list);

        // 执行删除
        adminMapper.deleteByExample(example);

    }

    @Override
    public List<Integer> getRoleIdsByAdminId(Integer id) {
            return adminRoleMapper.getRoleIdsByAdminId(id);
    }

    @Override
    public void deleteAdminRole(Integer adminId, List<Integer> ids) {
        adminMapper.deleteAdminRole(adminId,ids);
    }

    @Override
    public void insertAdminRole(Integer adminId, List<Integer> ids) {
        adminMapper.insertAdminRole(adminId,ids);
    }
}
