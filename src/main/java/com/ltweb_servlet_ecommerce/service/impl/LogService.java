package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.ILogDAO;
import com.ltweb_servlet_ecommerce.dao.impl.LogDAO;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ILogService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.cacheMemory.LocationCache;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

public class LogService implements ILogService {

    private ILogDAO logDAO = new LogDAO();

    @Override
    public List<LogModel> findAllWithFilter(LogModel model, Pageble pageble) {
        return logDAO.findAllWithFilter(model, pageble);
    }

    @Override
    public LogModel findWithFilter(LogModel model) {
        return logDAO.findWithFilter(model);
    }

    @Override
    public List<LogModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) {
        return logDAO.findByColumnValues(subQueryList, pageble);
    }

    @Override
    public LogModel update(LogModel model) {
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        logDAO.update(model);
        return logDAO.findById(model.getId());
    }

    @Override
    public boolean delete(Long[] ids) {
        boolean result = true;
        for (Long id : ids) {
            result = result && logDAO.softDelete(id);
        }
        return result;
    }

    @Override
    public List<LogModel> findAll(Pageble pageble) {
        List<LogModel> list = logDAO.findAll(pageble);
        return list;
    }

    @Override
    public LogModel findById(Long id) {
        return logDAO.findById(id);
    }

    @Override
    public LogModel save(LogModel model) {
        String ip = model.getIp();
        if (ip != null) {
            String location = getLocationFromCacheOrApi(ip.trim());
            model.setLocation(location);
        }
        Long id = logDAO.save(model);
        return logDAO.findById(id);
    }

    private String getLocationFromCacheOrApi(String ip) {
        LocationCache locationCache = LocationCache.getInstance();
        String location = locationCache.getLocation(ip);
        if (location == null) {
            LogModel cachedModel = findCachedModel(ip);
            if (cachedModel != null && !StringUtils.isBlank(cachedModel.getLocation())) {
                location = cachedModel.getLocation();
                locationCache.putLocation(ip, location);
            } else {
                location = getLocationFromApi(ip);
                locationCache.putLocation(ip, location);
            }
        }
        return location;
    }

    private LogModel findCachedModel(String ip) {
        return logDAO.findWithFilter(LogModel.builder().ip(ip).build());
    }

    private String getLocationFromApi(String ip) {
        String location = null;
        String ip2locationURL = "https://api.ip2location.io/?key=AB1186A50CFD7285B96E005A17727A20&ip=";

            try {
                String apiUrl = ip2locationURL + ip;
                URL url = new URL(apiUrl);
                InputStream inputStream = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line = reader.readLine();
                if (line != null) {
                    JSONObject jsonObject = new JSONObject(line);
                    Object cityName = jsonObject.get("city_name");
                    Object countryName = jsonObject.get("country_name");
                    if (!jsonObject.has("error") || cityName != null || countryName != null) {
                        location = cityName + " , <br/>" + countryName;
                    }
                }
                // Đóng luồng đọc
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return location;
    }
}