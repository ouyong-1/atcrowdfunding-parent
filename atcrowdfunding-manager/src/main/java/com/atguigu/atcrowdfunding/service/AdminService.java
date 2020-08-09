package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.MyException.LoginException;
import com.atguigu.atcrowdfunding.pojo.TAdmin;
import com.atguigu.atcrowdfunding.pojo.TAdminRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: ouyong
 * @Date: 2020/07/25 19:13
 */
public interface AdminService {

    TAdmin getAdmin(String loginacct, String userpswd) throws LoginException;

    PageInfo<TAdmin> listPage(Map<String, Object> map);

    void saveAdmin(TAdmin admin);

    void deleteAdminById(Integer id);

    void updateAdminById(TAdmin admin);

    TAdmin getAdminById(Integer id);

    void deleteBatchAdmin(String ids);

    List<Integer> getRoleIdsByAdminId(Integer id);

    void deleteAdminRole(Integer adminId, List<Integer> ids);

    void insertAdminRole(Integer adminId, List<Integer> ids);
}
