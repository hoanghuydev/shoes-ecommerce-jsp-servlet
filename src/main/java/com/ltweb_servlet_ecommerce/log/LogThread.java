package com.ltweb_servlet_ecommerce.log;


import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.service.ILogService;
import com.ltweb_servlet_ecommerce.service.impl.LogService;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

import java.util.List;

/**
 * A thread for saving log items to the database.
 */
public class LogThread extends Task<LogModel> {
    private ILogService logService;

    @Override
    public Integer call() throws Exception {
        logService = new LogService();
        List<LogModel> logs = getItems();
        try {
            if (logs != null && !logs.isEmpty()) {
                for (LogModel log : logs) {
//                    if (log.getLevel().equals(SystemConstant.DANGER_LEVEL)) {
//                        SendMailUtil.sendMail("21130363@st.hcmuaf.edu.vn",
//                                "Dangerous system error", SendMailUtil.templateMailDanger(log));
//                        System.out.println("Sent mail Dangerous");
//                    }
                    logService.save(log);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
