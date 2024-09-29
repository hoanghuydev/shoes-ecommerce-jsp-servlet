package com.ltweb_servlet_ecommerce.cron;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.IRoleDAO;
import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.dao.impl.OrderDAO;
import com.ltweb_servlet_ecommerce.dao.impl.RoleDAO;
import com.ltweb_servlet_ecommerce.dao.impl.UserDAO;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;
import com.ltweb_servlet_ecommerce.utils.StatusMapUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@WebListener
public class CronJob implements ServletContextListener {

    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the timer when the servlet context is started
        timer = new Timer(true);
        // Schedule a task to run periodically
        timer.scheduleAtFixedRate(new SaleForUserOfflineOver30DaysTask(), 0, 30L * 24L * 60L * 60L * 1000L); //30day
//        timer.scheduleAtFixedRate(new OrderNoneProcessOver5DaysTask(), 0, 24L * 60L * 60L * 1000L); //1day

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cancel the timer when the servlet context is destroyed
        if (timer != null) {
            timer.cancel();
        }
    }

    // This task will send for user offline over 30days from last time logged
    private static class SaleForUserOfflineOver30DaysTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Run task send mail for user offline over 30days...");
            List<SubQuery> subQueryList = new ArrayList<>();
            List<Object> dataSubQueryTime = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            long currentTimeInMillis = calendar.getTimeInMillis();
            long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;
            long targetTimeInMillis = currentTimeInMillis - thirtyDaysInMillis;
            Timestamp timestamp30DayAgo = new Timestamp(targetTimeInMillis);
            dataSubQueryTime.add(timestamp30DayAgo);
            subQueryList.add(new SubQuery("lastLogged", "<", dataSubQueryTime));
            try {
                IUserDAO userDAO = new UserDAO();
                List<UserModel> listUserOfflineOver30Day = userDAO.findByColumnValues(subQueryList, null);
//                for (UserModel user : listUserOfflineOver30Day) {
//                    SendMailUtil.sendMail(user.getEmail(),"Nai miss you "+user.getFullName(),SendMailUtil.templateContentMail("This is mail sales","You offline Nai over 30 days. Login and shopping we saling 30% for you"));
//                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    //    This task will send for admin the order which not process over 5days
    private static class OrderNoneProcessOver5DaysTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Run task send order not process over 5days for admin...");
            try {
                IOrderDAO orderDAO = new OrderDAO();
                IUserDAO userDAO = new UserDAO();
                IRoleDAO roleDAO = new RoleDAO();
                RoleModel roleAdmin = new RoleModel();
                roleAdmin.setValue(SystemConstant.ADMIN_ROLE);
                roleAdmin = roleDAO.findWithFilter(roleAdmin);
                UserModel adminModel = new UserModel();
                adminModel.setRoleId(roleAdmin.getId());
                List<OrderModel> listOrderNotProcess = orderDAO.findByNoProcessingOverDays(5,
                        StatusMapUtil.getStatusKey(SystemConstant.ORDER_PROCESSING));
                List<UserModel> listAdmin = userDAO.findAllWithFilter(adminModel, null);

                for (UserModel user : listAdmin) {
                    SendMailUtil.sendMail(user.getEmail(),
                            "Báo cáo khẩn cấp: Đơn hàng đang chờ xử lý trên 5 ngày",
                            SendMailUtil.templateMailOrderNotProcess(listOrderNotProcess));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
