package admin.service;

import admin.bean.User;
import admin.dao.BaseUserDao;
import admin.dao.imp.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class UserService {
    private static BaseUserDao dao = new UserDaoMysql();


    public static Map<String, Integer> console() {
        return dao.console();
    }

    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    public static User findByPhone(String phoneNumber) {
        return dao.findByPhone(phoneNumber);
    }

    public static void loginTime(String phoneNumber){
        dao.loginTime(phoneNumber);
    }

    public static boolean insert(User u) {
        return dao.insert(u);
    }

    public static boolean update(int id, User newUser) {
        return dao.update(id, newUser);
    }

    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
