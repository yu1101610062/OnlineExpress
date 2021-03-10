package admin.dao;

import java.util.Date;

/**
 * 用于定义数据库eadmin表格的操作规范
 * */
public interface BaseAdminDao {

    /**
     * 根据用户名更新登录时间和ip
     * @param username 管理员用户名
     * */
    void updateLoginTime(String username,Date date,String ip);

    /**
     * 管理员根据账号密码登录
     * @param username 用户名
     * @param password 密码
     * @return 登陆的结果，true表示登录成功
     * */
    boolean login(String username,String password);
}
