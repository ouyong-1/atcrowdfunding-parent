package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.MyException.LoginException;
import com.atguigu.atcrowdfunding.pojo.TAdmin;
import com.atguigu.atcrowdfunding.pojo.TMenu;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.atguigu.atcrowdfunding.util.Const;
import javafx.fxml.LoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 登录 or 退出
 * @Author: ouyong
 * @Date: 2020/07/25 19:11
 */

@Controller
public class DispatcherController {
    private static Logger logger = LoggerFactory.getLogger(DispatcherController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/main")
    public String main(HttpSession session){
        List<TMenu> parentMenuList = (List<TMenu>)session.getAttribute("parentMenuList");
        if(parentMenuList == null){
            // 查询所有子父子菜单
            parentMenuList = menuService.listAllMenu();
            session.setAttribute("parentMenuList",parentMenuList);
        }

        // 重定向到主页面
        return "main";
    }

    @RequestMapping("/login")
    public String login(String loginacct, String userpswd, Model model, HttpSession session) throws LoginException {

        try {
            TAdmin admin = adminService.getAdmin(loginacct,userpswd);
            session.setAttribute(Const.LOGIN_ADMIN,admin);
            // 密码验证成功后转发
            return "redirect:/main";
        } catch (LoginException e) {
            // e.printStackTrace();
            // 获取自定义异常信息
            model.addAttribute("message",e.getMessage());
            logger.info(e.getMessage());
            return "forward:/login.jsp";
        } catch (Exception e) {
            // e.printStackTrace();
            model.addAttribute("message","系统走丢了，请联系管理员！");
            logger.info(e.getMessage());
            return "forward:/login.jsp";
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        if(session != null){
            // 移除session
            session.invalidate();
        }
        return "redirect:/login.jsp";
    }

}
