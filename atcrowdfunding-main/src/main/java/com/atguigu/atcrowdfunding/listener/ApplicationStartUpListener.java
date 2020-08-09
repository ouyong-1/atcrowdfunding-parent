package com.atguigu.atcrowdfunding.listener;



import com.atguigu.atcrowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 自定义监听器，项目启动时调用
 * @Author: ouyong
 * @Date: 2020/07/25 18:45
 */
public class ApplicationStartUpListener implements ServletContextListener {
    // 开启日志
    private static Logger logger = LoggerFactory.getLogger(ApplicationStartUpListener.class);

    /**
     * 项目启动时调用，监听共享上下文路径到servletContext域中
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        // 获取上下文路径（项目访问url）
        String path = servletContext.getContextPath();
        // 共享数据到域中
        servletContext.setAttribute(Const.PATH,path);
        logger.info("=======================================项目启动成功==================================");
    }

    /**
     * 项目停止时调用
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("=======================================项目关闭==================================");
    }
}
