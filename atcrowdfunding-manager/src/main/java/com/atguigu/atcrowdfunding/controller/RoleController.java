package com.atguigu.atcrowdfunding.controller;


import com.atguigu.atcrowdfunding.pojo.TRole;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ouyong
 * @Date: 2020/07/28 15:39
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到角色维护
     * @return
     */
    @RequestMapping("/index")
    public String index(){

        return "role/index";
    }

    /**
     * 分页查询所有
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    @RequestMapping("/loadPage")
    @ResponseBody
    public PageInfo<TRole> page(@RequestParam(value = "pageNum",required = false,defaultValue = "")Integer pageNum,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "")Integer pageSize,
                                @RequestParam(value = "condition",required = false,defaultValue = "")String condition){

        // 开启分页查询
        PageHelper.startPage(pageNum,pageSize);

        // 封装查询条件
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("condition",condition);

        PageInfo<TRole> pageInfo = roleService.listPage(paramMap);


        return pageInfo;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRole")
    public String addRole(TRole role){

        roleService.saveRole(role);

        return "ok";
    }

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleById")
    public TRole getRoleById(Integer id){

        return roleService.getRoleById(id);
    }

    /**
     * 根据id修改角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateRole")
    public String updateRole(TRole role){

        roleService.updateRoleById(role);

        return "ok";
    }

    /**
     * 根据底删除角色
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRole")
    public String deleteRole(Integer id){

        roleService.deleteRoleById(id);

        return "ok";
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids){
        roleService.deleteRoleByIds(ids);

        return "ok";
    }
}
