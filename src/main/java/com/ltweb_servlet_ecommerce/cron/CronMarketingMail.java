package com.ltweb_servlet_ecommerce.cron;

import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.dao.impl.UserDAO;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@WebListener
public class CronMarketingMail implements ServletContextListener {

    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the timer when the servlet context is started
        timer = new Timer(true);
        // Schedule a task to run periodically
        timer.scheduleAtFixedRate(new MyTask(), 0, 30L * 24L * 60L * 60L * 1000L); //30day
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cancel the timer when the servlet context is destroyed
        if (timer != null) {
            timer.cancel();
        }
    }

    private static class MyTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Cron job is running...");
            List<SubQuery> subQueryList = new ArrayList<>();
            List<Object> dataSubQueryTime = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            long currentTimeInMillis = calendar.getTimeInMillis();
            long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;
            long targetTimeInMillis = currentTimeInMillis - thirtyDaysInMillis;
            Timestamp timestamp30DayAgo = new Timestamp(targetTimeInMillis);
            dataSubQueryTime.add(timestamp30DayAgo);
            subQueryList.add(new SubQuery("lastLogged","<",dataSubQueryTime));
//            try {
//                IUserDAO userDAO = new UserDAO();
//                List<UserModel> listUserOfflineOver30Day = userDAO.findByColumnValues(subQueryList,null);
//                for (UserModel user : listUserOfflineOver30Day) {
//                    SendMailUtil.sendMail(user.getEmail(),"Nai miss you "+user.getFullName(),SendMailUtil.templateContentMail("This is mail sales","You offline Nai over 30 days. Login and shopping we saling 30% for you"));
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
