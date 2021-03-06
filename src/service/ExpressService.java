package service;

import bean.Express;
import dao.BaseExpressDao;
import dao.imp.ExpressDaoMysql;
import exception.DuplicateCodeException;
import util.RandomUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();

    /**
     * 用于查询数据库中全部快递(总数+新增),待取件快递(总数+新增)
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增 }]
     */

    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页 默认分页(true)
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据单号查询信息
     *
     * @param number 单号
     * @return 快递信息 单号不存在返回null
     */

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据取件码查询信息
     *
     * @param Code 取件码
     * @return 快递信息 取件码不存在返回null
     */

    public static Express findByCode(String Code) {
        return dao.findByCode(Code);
    }

    /**
     * 根据用户手机号查询信息
     *
     * @param userPhone 手机号
     * @return 快递信息列表
     */

    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人手机号查询信息
     *
     * @param sysPhone 手机号
     * @return 快递信息列表
     */

    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param e 录入的对象
     * @return 录入的结果
     */

    public static boolean insert(Express e) {
        e.setCode(RandomUtil.getCode()+"");
        try {
            return dao.insert(e);
        }catch (DuplicateCodeException duplicateCodeException){
            return insert(e);
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象(number,company,username,userPhone)
     * @return 修改的结果
     */

    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserPhone()!=null){
            if (dao.delete(id)){
                return insert(newExpress);
            }
            return false;
        }else {
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if (newExpress.getStatus()==1){
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    /**
     * 更改快递状态为1，表示已取件
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果
     */

    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id删除快递
     *
     * @param id 删除的快递id
     * @return 删除结果
     */

    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
