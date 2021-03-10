package admin.test.dao.imp;

import admin.bean.Express;
import admin.dao.BaseExpressDao;
import admin.dao.imp.ExpressDaoMysql;
import admin.exception.DuplicateCodeException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class ExpressDaoMysqlTest {
    BaseExpressDao dao = new ExpressDaoMysql();

    @Test
    void console() {
        List<Map<String, Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    void findAll() {
        List<Express> all = dao.findAll(false, 2, 2);
        System.out.println(all);
    }

    @Test
    void findByNumber() {
        Express byNumber = dao.findByNumber("123123123");
        System.out.println(byNumber);
    }

    @Test
    void findByCode() {
        Express byCode = dao.findByCode("666666");
        System.out.println(byCode);
    }

    @Test
    void findByUserPhone() {
        List<Express> byUserPhone = dao.findByUserPhone("17624181054");
        System.out.println(byUserPhone);
    }

    @Test
    void findBySysPhone() {
        List<Express> bySysPhone = dao.findBySysPhone("18888888888");
        System.out.println(bySysPhone);
    }

    @Test
    void insert() {
        boolean insert = false;
        try {
            for (int i = 0;i<100;++i){
                Express e = new Express("1231231"+i,"李四","13843838488","顺丰速运","18888888888",666566+i+"");
                insert = dao.insert(e);
            }
        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复！");
        }
        System.out.println(insert);
    }

    @Test
    void update() {
        Express express = new Express();
        express.setNumber("321");
        express.setCompany("中通快递");
        express.setUsername("YYZY");
        express.setStatus(1);
        boolean update = dao.update(1, express);
        System.out.println(update);
    }

    @Test
    void updateStatus() {
        boolean b = dao.updateStatus("777777");
        System.out.println(b);
    }

    @Test
    void delete() {
        boolean delete = dao.delete(1);
        System.out.println(delete);
    }
}