package dao;

import bean.Express;
import exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {

    /**
     * 用于查询数据库中全部快递(总数+新增),待取件快递(总数+新增)
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增 }]
     */
    List<Map<String, Integer>> console();

    /**
     * 用于查询所有快递
     * @param limit 是否分页 默认分页(true)
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * 根据单号查询信息
     * @param number 单号
     * @return 快递信息 单号不存在返回null
     * */
    Express findByNumber(String number);

    /**
     * 根据取件码查询信息
     * @param Code 取件码
     * @return 快递信息 取件码不存在返回null
     * */
    Express findByCode(String Code);

    /**
     * 根据用户手机号查询信息
     * @param userPhone 手机号
     * @return 快递信息列表
     * */
    List<Express> findByUserPhone(String userPhone);

    /**
     * 根据录入人手机号查询信息
     * @param sysPhone 手机号
     * @return 快递信息列表
     * */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 快递的录入
     * @param e 录入的对象
     * @return 录入的结果
     * */
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * 快递的修改
     * @param id 要修改的快递id
     * @param newExpress 新的快递对象(number,company,username,userPhone)
     * @return 修改的结果
     * */
    boolean update(int id , Express newExpress);

    /**
     * 更改快递状态为1，表示已取件
     * @param code 要修改的快递取件码
     * @return 修改的结果
     * */
    boolean updateStatus(String code);

    /**
     * 根据id删除快递
     * @param id 删除的快递id
     * @return 删除结果
     * */
    boolean delete(int id);
}
