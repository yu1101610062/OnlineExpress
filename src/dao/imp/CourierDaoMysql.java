package dao.imp;

import bean.Courier;
import dao.BaseCourierDao;
import util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierDaoMysql implements BaseCourierDao {
    //查询控制台需要的快递员总数以及日新增快递员数据
    public static final String SQL_CONSOLE =
            "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(joinTime)=TO_DAYS(NOW()) OR NULL) data1_day FROM ExpressOnline.courier";
    public static final String SQL_FIND_ALL =
            "SELECT * FROM ExpressOnline.courier";
    public static final String SQL_FIND_LIMIT =
            "SELECT * FROM ExpressOnline.courier LIMIT ?,?";
    public static final String SQL_FIND_BY_PHONE =
            "SELECT * FROM ExpressOnline.courier WHERE phoneNumber = ?";
    public static final String SQL_INSERT =
            "INSERT INTO ExpressOnline.courier (courierName,phoneNumber,idNumber,password,joinTime) VALUES(?,?,?,?,NOW())";
    public static final String SQL_UPDATE =
            "UPDATE ExpressOnline.courier SET courierName = ?,phoneNumber = ?,idNumber = ?,password = ? WHERE id = ?";
    public static final String SQL_DELETE =
            "DELETE FROM ExpressOnline.courier WHERE id = ?";

    /**
     * 用于查询数据库中全部快递员(总数+今日新增)
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
     * 用于查询所有快递员
     *
     * @param limit      是否分页 默认分页(true)
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        List<Courier> data = new ArrayList<>();
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
                String courierName = rs.getString("courierName");
                String phoneNumber = rs.getString("phoneNumber");
                String idNumber = rs.getString("idNumber");
                String password = rs.getString("password");
                int sendExpress = rs.getInt("sendExpress");
                Timestamp joinTime = rs.getTimestamp("joinTime");
                Timestamp loginTime = rs.getTimestamp("loginTime");
                Courier e = new Courier(id,courierName,phoneNumber, idNumber,password,sendExpress,joinTime,loginTime);
                data.add(e);
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
     * @return 快递员信息
     */
    @Override
    public Courier findByPhone(String phoneNumber) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            state.setString(1,phoneNumber);
            rs = state.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String courierName = rs.getString("courierName");
                String idNumber = rs.getString("idNumber");
                String password = rs.getString("password");
                int sendExpress = rs.getInt("sendExpress");
                Timestamp joinTime = rs.getTimestamp("joinTime");
                Timestamp loginTime = rs.getTimestamp("loginTime");
                return new Courier(id,courierName,phoneNumber, idNumber,password,sendExpress,joinTime,loginTime);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return null;
    }

    /**
     * 快递员的录入
     *
     * @param c 录入的对象
     * @return 录入的结果
     */
    @Override
    public boolean insert(Courier c) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1,c.getCourierName());
            state.setString(2,c.getPhoneNumber());
            state.setString(3,c.getIdNumber());
            state.setString(4,c.getPassword());
            return state.executeUpdate() > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newCourier 新的快递员对象(courierName,phoneNumber,idNumber,password)
     * @return 修改的结果
     */
    @Override
    public boolean update(int id, Courier newCourier) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newCourier.getCourierName());
            state.setString(2,newCourier.getPhoneNumber());
            state.setString(3,newCourier.getIdNumber());
            state.setString(4,newCourier.getPassword());
            state.setInt(5,id);
            return state.executeUpdate() > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id删除快递员
     *
     * @param id 删除的快递id
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
