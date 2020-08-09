package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.pojo.TMenu;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: ouyong
 * @Date: 2020/07/29 11:23
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    /**
     * 页面跳转
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "menu/index";
    }

    /**
     * 查询所有菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadTree")
    public List<TMenu> loadTree(){
        List<TMenu> menuList = menuService.listAllMenuTree();
        return menuList;
    }
}
