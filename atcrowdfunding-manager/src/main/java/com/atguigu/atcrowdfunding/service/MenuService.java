package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.pojo.TMenu;

import java.util.List;

/**
 * @Author: ouyong
 * @Date: 2020/07/25 21:05
 */
public interface MenuService {
    List<TMenu> listAllMenu();

    List<TMenu> listAllMenuTree();
}
