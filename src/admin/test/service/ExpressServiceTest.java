package admin.test.service;

import admin.bean.Express;
import admin.service.ExpressService;
import org.junit.jupiter.api.Test;

class ExpressServiceTest {

    @Test
    void console() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByNumber() {
    }

    @Test
    void findByCode() {
    }

    @Test
    void findByUserPhone() {
    }

    @Test
    void findBySysPhone() {
    }

    @Test
    void insert() {
        Express e = new Express("1231231","李四","13843838488","顺丰速运","18888888888","666666");
        System.out.println(ExpressService.insert("1101610062@qq.com",e));
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void delete() {
    }
}