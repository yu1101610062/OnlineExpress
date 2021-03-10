package admin.test.dao.imp;

import admin.bean.Courier;
import admin.dao.imp.CourierDaoMysql;
import org.junit.jupiter.api.Test;

class CourierDaoMysqlTest {

    @Test
    void console() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByPhone() {
    }

    @Test
    void insert() {
        Courier c = new Courier("于朝阳","17624181054","130821199812221817","159357");
        CourierDaoMysql daoMysql = new CourierDaoMysql();
        daoMysql.insert(c);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        CourierDaoMysql daoMysql = new CourierDaoMysql();
        daoMysql.delete(1);
    }
}