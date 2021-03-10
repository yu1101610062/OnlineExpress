package admin.dao;

import admin.bean.User;

import java.util.List;
import java.util.Map;

public interface BaseUserDao {
    /**
     * 用于查询数据库中全部用户(总数+今日新增)
     *
     * @return [{size:总数,day:新增}]
     */
    Map<String, Integer> console();

    /**
     * 用于查询所有用户
     * @param limit 是否分页 默认分页(true)
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 用户的集合
     */
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据用户手机号查询信息
     * @param phoneNumber 手机号
     * @return 用户信息
     * */
    User findByPhone(String phoneNumber);

    /**
     * 根据用户手机号设置登录时间
     * @param phoneNumber 手机号
     * */
    void loginTime(String phoneNumber);

    /**
     * 用户的录入
     * @param u 录入的对象
     * @return 录入的结果
     * */
    boolean insert(User u);

    /**
     * 用户的修改
     * @param id 要修改的id
     * @param newUser 新的用户对象(userName,phoneNumber,password)
     * @return 修改的结果
     * */
    boolean update(int id , User newUser);


    /**
     * 根据id删除用户
     * @param id 删除的用户id
     * @return 删除结果
     * */
    boolean delete(int id);
}
