package com.ltweb_servlet_ecommerce.cron;

import com.ltweb_servlet_ecommerce.cacheMemory.BlockedIPCache;
import com.ltweb_servlet_ecommerce.dao.ILogDAO;
import com.ltweb_servlet_ecommerce.dao.impl.LogDAO;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.service.impl.UserService;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class MonitorLog implements ServletContextListener {

    private BlockedIPCache ipCache;
    private ILogDAO logDAO = new LogDAO();
    private Timer timer;
    private IUserService userService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ipCache = BlockedIPCache.getInstance();
        userService = new UserService();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                monitorLogs();
            }
        }, 0, 60 * 1000); // 1 minute
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ipCache.cleanupExpiredBlocks();
            }
        }, 0, 5 * 60 * 1000);// 5 minutes
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void monitorLogs() {
        System.out.println("Monitor logs");
        List<LogModel> list = logDAO.checkAccessCount();
        if (!list.isEmpty()) {
            List<UserModel> listAdmin = userService.getListAdmin();

            for (LogModel log : list) {
                System.out.println("Block IP: " + log.getIp());
                ipCache.blockIP(log.getIp());
            }

            listAdmin.forEach(admin -> {
                SendMailUtil.templateMailAccessCount(list, admin.getEmail());
            });
            // Send mail access count
            System.out.println("Send mail access count");

            ipCache.printBlockedIPs();
        }
    }


}
/**
 UPDATE logs SET createAt = NOW() WHERE id BETWEEN 650 and 678;

 SELECT ip, resource, location, COUNT(ip) AS accessCount, createAt
 FROM logs
 WHERE createAt > DATE_SUB(NOW() , INTERVAL 1 MINUTE) AND isDeleted = 0
 GROUP BY ip, resource, createAt
 HAVING COUNT(ip) > 10

 */