package com.ltweb_servlet_ecommerce.cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class CronMarketingMail implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the timer when the servlet context is started
        timer = new Timer(true);
        // Schedule a task to run periodically
        timer.scheduleAtFixedRate(new MyTask(), 0, 60 * 1000); //60s
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
            // Your periodic task logic goes here
            System.out.println("Cron job is running...");
        }
    }
}
