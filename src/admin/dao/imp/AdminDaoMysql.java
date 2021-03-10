package admin.dao.imp;

import admin.dao.BaseAdminDao;
import admin.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminDaoMysql implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "update EADMIN set LOGINTIME=?,LOGINIP=? where USERNAME=?";
    private static final String SQL_LOGIN = "select id from eadmin where username=? and password=?";

    /**
     * 根据用户名更新登录时间和ip
     *
     * @param username 管理员用户名
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //拿到连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //预编译SQL
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //填充参数
            state.setDate(1, new java.sql.Date(date.getTime()));
            state.setString(2, ip);
            state.setString(3, username);
            //执行
            state.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //释放资源
            DruidUtil.close(conn, state, null);
        }
    }

    /**
     * 管理员根据账号密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆的结果，true表示登录成功
     */
    @Override
    public boolean login(String username, String password) {
        //拿到连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //预编译SQL
        try {
            state = conn.prepareStatement(SQL_LOGIN);
            //填充参数
            state.setString(1, username);
            state.setString(2, password);
            //执行
            rs = state.executeQuery();
            //返回登录结果
            return rs.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //释放资源
            DruidUtil.close(conn, state, rs);
        }
        return false;
    }
}
