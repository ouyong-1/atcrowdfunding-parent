package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.pojo.TMenu;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层
 * 处理侧边栏展示业务
 * @Author: ouyong
 * @Date: 2020/07/25 21:06
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private TMenuMapper menuMapper;

    @Override
    public List<TMenu> listAllMenu() {

        // 父菜单集合
        List<TMenu> parentList = new ArrayList<>();

        // 所有父菜单集合，设置主键id为key,通过这个key获取父菜单
        Map<Integer,TMenu> parentMap = new HashMap<>();


        List<TMenu> allList = menuMapper.selectByExample(null);

        for (TMenu menu : allList) {
            // 通过父id这个外键获取所有父元素
            Integer pid = menu.getPid();
            if(pid == 0){
                parentList.add(menu);
                parentMap.put(menu.getId(),menu);
            }
        }

        // 组合父子菜单
        for (TMenu menu : allList) {
            Integer pid = menu.getPid();
            if (pid != 0) {
                // 通过map中的key获取父菜单
                TMenu parentMenu = parentMap.get(pid);
                // 通过Menu父菜单设置children集合设置子菜单的值，父菜单已通过parentList设置过了
                parentMenu.getChildren().add(menu);
            }
        }

        return parentList;
    }

    @Override
    public List<TMenu> listAllMenuTree() {

        return menuMapper.selectByExample(null);

    }
}
