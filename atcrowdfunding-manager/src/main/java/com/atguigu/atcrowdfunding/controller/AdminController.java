package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.pojo.Data;
import com.atguigu.atcrowdfunding.pojo.TAdmin;
import com.atguigu.atcrowdfunding.pojo.TAdminRole;
import com.atguigu.atcrowdfunding.pojo.TRole;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户维护模块
 * @Author: ouyong
 * @Date: 2020/07/27 12:53
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "3")Integer pageSize,
                        @RequestParam(value = "condition",required = false,defaultValue = "")String condition,
                        Model model){
        // 启动分页插件
        PageHelper.startPage(pageNum,pageSize);

        // 封装查询条件，查询条件不止一个
        Map<String,Object> map = new HashMap<>();
        map.put("condition",condition);

        // 调用service，返回分页结果
        PageInfo<TAdmin> page = adminService.listPage(map);

        model.addAttribute("page",page);
        return "admin/index";
    }

    /**
     * 转发到添加用户界面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){

        return "admin/add";
    }

    /**
     * 添加用户信息
     * @param admin
     * @return
     */
    @RequestMapping("/doAdd")
    public String saveAdmin(TAdmin admin){

        adminService.saveAdmin(admin);

        return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;// 重定向到添加数据那一页
    }

    /**
     * 根据底删除用户
     * @param id
     * @param pageNum
     * @return
     */
    @RequestMapping("/doDelete")
    public String deleteAdminById(Integer id,Integer pageNum){

        adminService.deleteAdminById(id);

        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    /**
     * 携带数据转发到修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam("id")Integer id,Model model){


        TAdmin admin = adminService.getAdminById(id);

        model.addAttribute("admin",admin);

        // 转发到修改页面
        return "admin/update";
    }

    /**
     * 更新用户信息
     * @param admin
     * @param pageNum
     * @return
     */
    @RequestMapping("/doUpdate")
    public String updateAdmin(TAdmin admin,Integer pageNum){

        adminService.updateAdminById(admin);

        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    /**
     * 批量删除
     * @param ids
     * @param pageNum
     * @return
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatchAdmin(String ids,Integer pageNum){

        adminService.deleteBatchAdmin(ids);

        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    @RequestMapping("/toAddAdminRole")
    public String toAddAdminRole(Integer id,Map<String,Object> map){
        // 查询所有角色
        List<TRole> allRole = roleService.listAllRoles();

        // 根据用户id查询用户角色表的角色id
        List<Integer> rolesId = adminService.getRoleIdsByAdminId(id);

        // 已分配的角色
        List<TRole> assign = new ArrayList<>();
        // 未分配的角色
        List<TRole> unassign = new ArrayList<>();

        for (TRole role : allRole) {
            if(rolesId.contains(role.getId())){
                assign.add(role);
            }else {
                unassign.add(role);
            }
        }
        // 共享数据
        map.put("assign",assign);
        map.put("unassign",unassign);

        return "admin/addAdminToRole";
    }


    @ResponseBody
    @RequestMapping("/unassign")
    public String unassign(Integer adminId, Data ds) {
        adminService.deleteAdminRole(adminId,ds.getIds());
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/assign")
    public String assign(Integer adminId,Data ds) {
        adminService.insertAdminRole(adminId,ds.getIds());
        return "ok";
    }


}
