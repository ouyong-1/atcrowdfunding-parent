package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.pojo.TRole;
import com.atguigu.atcrowdfunding.pojo.TRoleExample;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ouyong
 * @Date: 2020/07/28 15:40
 */
@Service
public class TRoleServiceImpl implements RoleService {

    @Autowired
    private TRoleMapper roleMapper;


    @Override
    public PageInfo<TRole> listPage(Map<String, Object> paramMap) {

        TRoleExample example = new TRoleExample();

        String condition = (String)paramMap.get("condition");

        if (!StringUtils.isEmpty(condition)) {
            // 执行按名字模糊查询
            example.createCriteria().andNameLike("%" + condition + "%");

        }

        List<TRole> roleList = roleMapper.selectByExample(example);

        // 封装分页 结果
        PageInfo<TRole> pageInfo = new PageInfo<>(roleList,5);

        return pageInfo;
    }

    @Override
    public void saveRole(TRole role) {

        roleMapper.insertSelective(role);

    }

    @Override
    public TRole getRoleById(Integer id) {

        return roleMapper.selectByPrimaryKey(id);

    }

    @Override
    public void updateRoleById(TRole role) {

        roleMapper.updateByPrimaryKeySelective(role);

    }

    @Override
    public void deleteRoleById(Integer id) {

        roleMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void deleteRoleByIds(String ids) {

        List<Integer> list = new ArrayList<>();
        String[] splits = ids.split(",");
        for (String split : splits) {
            int id = Integer.parseInt(split);
            list.add(id);
        }
        TRoleExample example = new TRoleExample();

        example.createCriteria().andIdIn(list);

        roleMapper.deleteByExample(example);

    }

    @Override
    public List<TRole> listAllRoles() {

        List<TRole> roleList = roleMapper.selectByExample(null);
        return roleList;

    }
}
