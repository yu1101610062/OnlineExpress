package dao;

import bean.Courier;

import java.util.List;
import java.util.Map;

public interface BaseCourierDao {
    /**
     * 用于查询数据库中全部快递员(总数+今日新增)
     *
     * @return [{size:总数,day:新增}]
     */
    Map<String, Integer> console();

    /**
     * 用于查询所有快递员
     * @param limit 是否分页 默认分页(true)
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据用户手机号查询信息
     * @param phoneNumber 手机号
     * @return 快递员信息
     * */
    Courier findByPhone(String phoneNumber);

    /**
     * 快递员的录入
     * @param c 录入的对象
     * @return 录入的结果
     * */
    boolean insert(Courier c);

    /**
     * 快递的修改
     * @param id 要修改的快递id
     * @param newCourier 新的快递员对象(courierName,phoneNumber,idNumber,password)
     * @return 修改的结果
     * */
    boolean update(int id , Courier newCourier);


    /**
     * 根据id删除快递员
     * @param id 删除的快递id
     * @return 删除结果
     * */
    boolean delete(int id);
}
