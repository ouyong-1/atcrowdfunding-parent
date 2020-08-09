package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.pojo.TRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: ouyong
 * @Date: 2020/07/28 15:40
 */
public interface RoleService {
    PageInfo<TRole> listPage(Map<String, Object> paramMap);

    void saveRole(TRole role);

    TRole getRoleById(Integer id);

    void updateRoleById(TRole role);

    void deleteRoleById(Integer id);

    void deleteRoleByIds(String ids);

    List<TRole> listAllRoles();
}
