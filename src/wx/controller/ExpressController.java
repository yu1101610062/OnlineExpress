package wx.controller;

import admin.MVC.ResponseBody;
import admin.bean.BootstrapTableExpress;
import admin.bean.Express;
import admin.bean.Message;
import admin.service.ExpressService;
import admin.util.DateFormatUtil;
import admin.util.JSONUtil;
import admin.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest req, HttpServletResponse resp) {
        String userPhone = UserUtil.getUserPhone(req.getSession());
        List<Express> expressList = ExpressService.findByUserPhone(userPhone);
        List<BootstrapTableExpress> list2 = new ArrayList<>();
        for (Express e : expressList) {
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            String code = e.getCode() == null ? "已取件" : e.getCode();
            BootstrapTableExpress e2 = new BootstrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list2.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
            Stream<BootstrapTableExpress> status0Express = list2.stream().filter(express -> express.getStatus().equals("待取件")).sorted((o1, o2) -> {
                long o1time = DateFormatUtil.toTime(o1.getInTime());
                long o2time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1time - o2time);
            });
            Stream<BootstrapTableExpress> status1Express = list2.stream().filter(express -> express.getStatus().equals("已取件")).sorted((o1, o2) -> {
                long o1time = DateFormatUtil.toTime(o1.getInTime());
                long o2time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1time - o2time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap<>();
            data.put("status0", s0);
            data.put("status1", s1);
            msg.setData(data);
        }
        return JSONUtil.toJSON(msg);
    }


    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootstrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootstrapTableExpress e2 = new BootstrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if(list.size() == 0){
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/insertExpress.do")
    public String insertExpress(HttpServletRequest req,HttpServletResponse resp){
        Message msg = new Message();
        String number = req.getParameter("number");
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String company = req.getParameter("company");
        Express express = new Express(number,username,userPhone,company,UserUtil.getUserPhone(req.getSession()));
        if (ExpressService.insert(null,express)){
            msg.setStatus(0);
        }else {
            msg.setStatus(-1);
        }
        return JSONUtil.toJSON(msg);
    }
}
