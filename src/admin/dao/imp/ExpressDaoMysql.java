package admin.dao.imp;

import admin.bean.Express;
import admin.dao.BaseExpressDao;
import admin.exception.DuplicateCodeException;
import admin.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {
    //用于查询数据库中全部快递(总数+新增),待取件快递(总数+新增)
    public static final String SQL_CONSOLE =
            "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day,COUNT(STATUS=0 OR NULL) data2_size,COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day FROM ExpressOnline.Express";
    //用于查询所有快递
    public static final String SQL_FIND_ALL =
            "SELECT * FROM ExpressOnline.EXPRESS";
    //用于分页查询所有快递
    public static final String SQL_FIND_LIMIT =
            "SELECT * FROM ExpressOnline.EXPRESS LIMIT ?,?";
    //通过快递单号查询快递
    public static final String SQL_FIND_BY_NUMBER =
            "SELECT * FROM ExpressOnline.EXPRESS WHERE NUMBER=?";
    //根据取件码查询快递
    public static final String SQL_FIND_BY_CODE =
            "SELECT * FROM ExpressOnline.EXPRESS WHERE CODE=?";
    //根据用户手机号查询快递
    public static final String SQL_FIND_BY_USERPHONE =
            "SELECT * FROM ExpressOnline.EXPRESS WHERE USERPHONE=?";
    //根据用户手机号查询未取件快递
    private static final String SQL_FIND_BY_USERPHONE_STATUS =
            "SELECT * FROM ExpressOnline.EXPRESS WHERE USERPHONE=? AND STATUS=?";
    //根据录入人手机号查询快递
    public static final String SQL_FIND_BY_SYSPHONE =
            "SELECT * FROM ExpressOnline.EXPRESS WHERE SYSPHONE=?";
    //快递录入
    public static final String SQL_INSERT =
            "INSERT INTO ExpressOnline.EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    //快递修改
    public static final String SQL_UPDATE =
            "UPDATE ExpressOnline.EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    //状态码改变(取件)
    public static final String SQL_UPDATE_STATUS =
            "UPDATE ExpressOnline.EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE=?";
    //快递删除
    public static final String SQL_DELETE =
            "DELETE FROM ExpressOnline.EXPRESS WHERE ID=?";


    /**
     * 用于查询数据库中全部快递(总数+新增),待取件快递(总数+新增)
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增 }]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            rs = state.executeQuery();
            if (rs.next()) {
                int data1_size = rs.getInt("data1_size");
                int data1_day = rs.getInt("data1_day");
                int data2_size = rs.getInt("data2_size");
                int data2_day = rs.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size", data1_size);
                data1.put("data1_day", data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size", data2_size);
                data2.put("data2_day", data2_day);
                data.add(data1);
                data.add(data2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }

        return data;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页 默认分页(true)
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
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
                String number = rs.getString("number");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
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
     * 根据单号查询信息
     *
     * @param number 单号
     * @return 快递信息 单号不存在返回null
     */
    @Override
    public Express findByNumber(String number) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            state.setString(1, number);
            rs = state.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return null;
    }

    /**
     * 根据取件码查询信息
     *
     * @param code 取件码
     * @return 快递信息 取件码不存在返回null
     */
    @Override
    public Express findByCode(String code) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            state.setString(1, code);
            rs = state.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, rs);
        }
        return null;
    }

    /**
     * 根据用户手机号查询信息
     *
     * @param userPhone 手机号
     * @return 快递信息列表
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            state.setString(1, userPhone);
            rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
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
     * 根据用户手机号查询未取件快递
     *
     * @param userPhone 手机号
     * @param status
     * @return 未取件快递信息列表
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE_STATUS);
            state.setString(1, userPhone);
            state.setInt(2, status);
            rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
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
     * 根据录入人手机号查询信息
     *
     * @param sysPhone 手机号
     * @return 快递信息列表
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            state.setString(1, sysPhone);
            rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
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
     * 快递的录入
     *
     * @param e 录入的对象
     * @return 录入的结果
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            state.setString(1, e.getNumber());
            state.setString(2, e.getUsername());
            state.setString(3, e.getUserPhone());
            state.setString(4, e.getCompany());
            state.setString(5, e.getCode());
            state.setString(6, e.getSysPhone());
            return state.executeUpdate() > 0;
        } catch (Exception e1) {
            if (e1.getMessage().endsWith("for key 'code'")) {
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            } else {
                System.out.println("发生错误");
            }
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象(number,company,username,userPhone)
     * @return 修改的结果
     */
    @Override
    public boolean update(int id, Express newExpress) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1, newExpress.getNumber());
            state.setString(2, newExpress.getUsername());
            state.setString(3, newExpress.getCompany());
            state.setInt(4, newExpress.getStatus());
            state.setInt(5, id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 更改快递状态为1，表示已取件
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果
     */
    @Override
    public boolean updateStatus(String code) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            state.setString(1, code);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 根据id删除快递
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
            state.setInt(1, id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}
