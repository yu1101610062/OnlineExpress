package admin.service;

import admin.dao.BaseAdminDao;
import admin.dao.imp.AdminDaoMysql;

import java.util.Date;

public class AdminService {

    private static BaseAdminDao dao = new AdminDaoMysql();
    public static void updateLoginTimeAndIP(String username, Date date, String ip){
        dao.updateLoginTime(username, date, ip);
    }

    public  static boolean login(String username,String password){
        return dao.login(username, password);
    }


}
