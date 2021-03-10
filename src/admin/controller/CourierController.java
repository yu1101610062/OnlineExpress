package admin.controller;

import admin.MVC.ResponseBody;
import admin.bean.BootstrapTableCourier;
import admin.bean.Courier;
import admin.bean.Message;
import admin.bean.ResultData;
import admin.service.CourierService;
import admin.util.DateFormatUtil;
import admin.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourierController {
    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        Map<String,Integer> data = CourierService.console();
        Message msg = new Message();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        int offset = Integer.parseInt(req.getParameter("offset"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootstrapTableCourier> list2 = new ArrayList<>();
        for(Courier c:list){
            String joinTime = DateFormatUtil.format(c.getJoinTime());
            String loginTime = c.getLoginTime()==null?"未曾登录":DateFormatUtil.format(c.getLoginTime());
            String sendExpress = String.valueOf(c.getSendExpress());
            BootstrapTableCourier c2 = new BootstrapTableCourier(c.getId(),c.getCourierName(),c.getPhoneNumber(),c.getIdNumber(),c.getPassword(),sendExpress,joinTime,loginTime);
            list2.add(c2);
        }
        Map<String, Integer> console = CourierService.console();
        int total = console.get("data1_size");
        //将集合封装为bootstrap识别的格式
        ResultData<BootstrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return JSONUtil.toJSON(data);
    }
    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String courierName = req.getParameter("courierName");
        String phoneNumber = req.getParameter("phoneNumber");
        String idNumber = req.getParameter("idNumber");
        String password = req.getParameter("password");
        Courier c = new Courier(courierName,phoneNumber,idNumber,password);
        boolean flag = CourierService.insert(c);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("快递员录入成功！");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递员录入失败！");
        }
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String phoneNumber = request.getParameter("phoneNumber");
        Courier c = CourierService.findByPhone(phoneNumber);
        Message msg = new Message();
        if(c == null){
            msg.setStatus(-1);
            msg.setResult("手机号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(c);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        Courier newCourier = new Courier();
        int id = Integer.parseInt(request.getParameter("id"));
        newCourier.setCourierName(request.getParameter("courierName"));
        newCourier.setPhoneNumber(request.getParameter("phoneNumber"));
        newCourier.setIdNumber(request.getParameter("idNumber"));
        newCourier.setPassword(request.getParameter("password"));

        boolean flag = CourierService.update(id,newCourier);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = CourierService.delete(id);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        return JSONUtil.toJSON(msg);
    }
}
