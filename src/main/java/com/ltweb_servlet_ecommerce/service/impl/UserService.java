package com.ltweb_servlet_ecommerce.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.dao.IRoleDAO;
import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.dao.impl.*;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.JSONObjectUtil;
import com.ltweb_servlet_ecommerce.utils.ObjectComparator;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import org.json.JSONObject;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    @Inject
    IUserDAO userDAO;
    @Inject
    IRoleDAO roleDAO;
    @Inject
    CartDAO cartDAO;
    @Inject
    OpinionDAO opinionDAO;
    @Inject
    UserAddressDAO userAddressDAO;
    @Inject
    UserOrderDAO userOrderDAOl;

    @Override
    public boolean changePassword(long userId, String password) {
        try {
            UserModel user = userDAO.findById(userId);
            if (user == null) {
                return false;
            }
            BCrypt.Hasher hasher = BCrypt.withDefaults();
            String hashedPassword = hasher.hashToString(12, password.toCharArray());
            user.setPassword(hashedPassword);
            user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            userDAO.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserModel> getListAdmin() {
        RoleModel roleAdmin = new RoleModel(SystemConstant.ADMIN_ROLE);
        try {
            roleDAO = new RoleDAO();
            userDAO = new UserDAO();
            roleAdmin = roleDAO.findWithFilter(roleAdmin);
            UserModel adminModel = new UserModel();
            adminModel.setRoleId(roleAdmin.getId());
            List<UserModel> listAdmin = userDAO.findAllWithFilter(adminModel, null);
            return listAdmin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // This method performs a soft delete operation on multiple users
    public boolean softDelete(Long[] ids) {
        // Initialize a flag to true. This flag will be used to indicate if the operation was successful
        boolean flag = true;

        // Iterate over the array of user IDs
        for (Long id : ids) {
            try {
                // Find the user with the current ID
                UserModel model = findById(id);

                // Set the update timestamp to the current time
                model.setUpdateAt(new Timestamp(System.currentTimeMillis()));

                // Mark the user as deleted
                model.setIsDeleted(true);

                // Update the user in the database
                update(model);

                // Mark the user's carts as deleted
                cartDAO.update("UPDATE carts SET isDeleted = 1 WHERE userId = ?", List.of(id));

                // Mark the user's opinions as deleted
                opinionDAO.update("UPDATE opinions SET isDeleted = 1 WHERE userId = ?", List.of(id));

                // Mark the user's addresses as deleted
                userAddressDAO.update("UPDATE user_addresses SET isDeleted = 1 WHERE userId = ?", List.of(id));

                // Mark the user's orders as deleted
                userOrderDAOl.update("UPDATE user_orders SET isDeleted = 1 WHERE userId = ?", List.of(id));
            } catch (SQLException e) {
                // If an exception occurs, set the flag to false
                flag = false;
            }
        }

        // Return the flag. If it's still true, the operation was successful. If it's false, an exception occurred
        return flag;
    }

    private List<UserModel> appendRoleForUsers(List<UserModel> listUser) throws SQLException {
        List<RoleModel> listRole = roleDAO.findAll(null);
        Map<Long, RoleModel> roleMap = listRole.stream()
                .collect(Collectors.toMap(RoleModel::getId, role -> role));
//        Loop để lấy role
        for (UserModel user : listUser) {
            if (user.getRoleId() != null) {
                RoleModel matchedRole = roleMap.get(user.getRoleId());
                user.setRole(matchedRole);
            }
        }
        return listUser;
    }

    @Override
    public List<UserModel> findAllWithFilter(UserModel model, Pageble pageble) throws SQLException {
        List<UserModel> listUser = userDAO.findAllWithFilter(model, pageble);
        return appendRoleForUsers(listUser);
    }

    @Override
    public UserModel findWithFilter(UserModel model) throws SQLException {
        UserModel user = userDAO.findWithFilter(model);
        if (user != null) {
            RoleModel role = roleDAO.findById(user.getRoleId());
            user.setRole(role);
        }
        return user;
    }

    @Override
    public List<UserModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        List<UserModel> listUser = userDAO.findByColumnValues(subQueryList, pageble);
        return appendRoleForUsers(listUser);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return userDAO.findWithCustomSQL(sql, params);
    }


    @Override
    public UserModel update(UserModel model) throws SQLException {
        model.setRole(null);
        UserModel oldModel = findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userDAO.update(model);
        UserModel newModel = findById(model.getId());
        LinkedHashMap<String, String>[] results = ObjectComparator.compareObjects(oldModel, newModel);

        // Logging
        JSONObject preValue = new JSONObject();
        JSONObject preValueObject = new JSONObject(results[0]);
        JSONObjectUtil.removeKeys(preValueObject, List.of("password", "userName", "role"));
        preValue.put(SystemConstant.VALUE_LOG, preValueObject);

        JSONObject value = new JSONObject();
        value.put(SystemConstant.STATUS_LOG, "UpdatedAt and lastLogged fields successfully updated");
        JSONObject valueObject = new JSONObject(results[1]);
        JSONObjectUtil.removeKeys(valueObject, List.of("password", "userName", "role"));
        value.put(SystemConstant.VALUE_LOG, valueObject);

        LoggerHelper.log(SystemConstant.WARN_LEVEL, "UPDATE", RuntimeInfo.getCallerClassNameAndLineNumber(), preValue, value);
        return newModel;
    }


    @Override
    public UserModel delete(Long id) throws SQLException {
        UserModel oldModel = findById(id);
        userDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<UserModel> findAll(Pageble pageble) throws SQLException {
        List<UserModel> listUser = userDAO.findAll(pageble);
        return appendRoleForUsers(listUser);
    }


    @Override
    public UserModel findById(Long id) throws SQLException {
        UserModel user = userDAO.findById(id);
        if (user != null) {
            RoleModel role = roleDAO.findById(user.getRoleId());
            user.setRole(role);
        }
        return user;
    }

    @Override
    public UserModel save(UserModel model) throws SQLException {
        model.setRole(null);
        Long productId = userDAO.save(model);
        return findById(productId);
    }

    @Override
    public boolean validateString(String input) {
        // Check special character
        boolean hasSpecialChar = input.matches(".*[^a-zA-Z0-9].*");

        // Check number
        boolean hasDigit = input.matches(".*\\d.*");

        // Check Uppercase
        boolean hasUpperCase = input.matches(".*[A-Z].*");

        // check all of them
        return hasSpecialChar && hasDigit && hasUpperCase;
    }

    @Override
    public int getUserCount() {
        IUserDAO u = new UserDAO();
        int count = 0;
        try {
            List<UserModel> list = u.findAll(null);
            count = list.size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

}
