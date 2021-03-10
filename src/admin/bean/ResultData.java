package admin.bean;

import java.util.ArrayList;
import java.util.List;

public class ResultData<T> {
    //结果集合
    private List<T> rows = new ArrayList<>();
    //总数
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
