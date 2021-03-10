package admin.dao.imp;

import admin.bean.User;
import admin.dao.BaseUserDao;
import admin.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMysql implements BaseUserDao {
    public static final String SQL_CONSOLE =
            "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(joinTime)=TO_DAYS(NOW()) OR NULL) data1_day FROM ExpressOnline.user";
    public static final String SQL_FIND_ALL =
            "SELECT * FROM ExpressOnline.user";
    public static final String SQL_FIND_LIMIT =
            "SELECT * FROM ExpressOnline.user LIMIT ?,?";
    public static final String SQL_FIND_BY_PHONE =
            "SELECT * FROM ExpressOnline.user WHERE phoneNumber = ?";
    public static final String SQL_INSERT =
            "INSERT INTO ExpressOnline.user (userName,phoneNumber,password,joinTime) VALUES(?,?,?,NOW())";
    public static final String SQL_UPDATE =
            "UPDATE ExpressOnline.user SET userName = ?,phoneNumber = ?,password = ? WHERE id = ?";
    public static final String SQL_DELETE =
            "DELETE FROM ExpressOnline.user WHERE id = ?";
    public static final String SQL_LOGIN_TIME =
            "UPDATE ExpressOnline.user SET loginTime = NOW() WHERE phoneNumber = ?";
    /**
     * 用于查询数据库中全部用户(总数+今日新增)
     *
     * @return [{size:总数,day:新增}]
     */
    @Override
    public Map<String, Integer> console() {
        Map<String, Integer> data = new HashMap<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            rs = state.executeQuery();
            if (rs.next()) {
                data.put("data1_size", rs.getInt("data1_size"));
                data.put("data1_day", rs.getInt("data1_day"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return data;
    }

    /**
     * 用于查询所有用户
     *
     * @param limit      是否分页 默认分页(true)
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 用户的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        List<User> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String UserName = rs.getString("UserName");
                String phoneNumber = rs.getString("phoneNumber");
                String password = rs.getString("password");
                Timestamp joinTime = rs.getTimestamp("joinTime");
                Timestamp loginTime = rs.getTimestamp("loginTime");
                User u = new User(id,UserName,phoneNumber,password,joinTime,loginTime);
                data.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return data;
    }

    /**
     * 根据用户手机号查询信息
     *
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    @Override
    public User findByPhone(String phoneNumber) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            state.setString(1,phoneNumber);
            rs = state.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                Timestamp joinTime = rs.getTimestamp("joinTime");
                Timestamp loginTime = rs.getTimestamp("loginTime");
                return new User(id,userName,phoneNumber,password,joinTime,loginTime);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return null;
    }

    /**
     * 根据用户手机号设置登录时间
     *
     * @param phoneNumber 手机号
     */
    @Override
    public void loginTime(String phoneNumber) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_LOGIN_TIME);
            state.setString(1,phoneNumber);
            state.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
    }

    /**
     * 用户的录入
     *
     * @param u 录入的对象
     * @return 录入的结果
     */
    @Override
    public boolean insert(User u) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,u.getUserName());
            state.setString(2,u.getPhoneNumber());
            state.setString(3,u.getPassword());
            return state.executeUpdate() > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 用户的修改
     *
     * @param id      要修改的id
     * @param newUser 新的用户对象(userName,phoneNumber,password)
     * @return 修改的结果
     */
    @Override
    public boolean update(int id, User newUser) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newUser.getUserName());
            state.setString(2,newUser.getPhoneNumber());
            state.setString(3,newUser.getPassword());
            state.setInt(4,id);
            return state.executeUpdate() > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id删除用户
     *
     * @param id 删除的用户id
     * @return 删除结果
     */
    @Override
    public boolean delete(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1,id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
