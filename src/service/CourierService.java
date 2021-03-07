package service;

import bean.Courier;
import dao.BaseCourierDao;
import dao.imp.CourierDaoMysql;

import java.util.List;
import java.util.Map;

public class CourierService {
    private static BaseCourierDao dao = new CourierDaoMysql();


    public static Map<String, Integer> console() {
        return dao.console();
    }

    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    public static Courier findByPhone(String phoneNumber) {
        return dao.findByPhone(phoneNumber);
    }


    public static boolean insert(Courier c) {
        return dao.insert(c);
    }


    public static boolean update(int id, Courier newCourier) {
        return dao.update(id, newCourier);
    }


    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
