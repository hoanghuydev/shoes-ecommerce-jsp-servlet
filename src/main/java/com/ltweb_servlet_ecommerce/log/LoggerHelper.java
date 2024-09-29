package com.ltweb_servlet_ecommerce.log;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.utils.IPAddressHolder;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

import java.sql.Timestamp;

@Data
@Builder
public class LoggerHelper {
    private static final LogManager logger = LogManager.getInstance();
    public static void logDetailedDangerMessage(Exception e, String action) {
        String errorMessage = e.getLocalizedMessage(); // Lấy thông điệp lỗi

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(SystemConstant.STATUS_LOG, "A serious error occurred!");
        jsonObject.put(SystemConstant.VALUE_LOG, new JSONObject().put("error_message", errorMessage));
        LoggerHelper.log(SystemConstant.DANGER_LEVEL, action, RuntimeInfo.getCallerClassNameAndLineNumber(), jsonObject);
    }

    public static void log(String level, String action, String resource, JSONObject value) {
        log(level, action, resource, null, value);
    }

    public static void log(String level, String action, String resource, JSONObject preValue, JSONObject value) {
        LogModel log = LogModel.builder()
                .ip(IPAddressHolder.getIPAddress())
                .level(level)
                .action(action)
                .resource(resource)
                .value(value)
                .preValue(preValue)
                .build();
        log.setCreateAt(new Timestamp(System.currentTimeMillis()));
        logger.submit(log);
    }

}

