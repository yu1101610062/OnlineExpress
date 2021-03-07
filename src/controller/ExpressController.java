package controller;

import MVC.ResponseBody;
import bean.BootstrapTableExpress;
import bean.Express;
import bean.Message;
import bean.ResultData;
import service.ExpressService;
import util.DateFormatUtil;
import util.JSONUtil;
import util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressController {

    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg = new Message();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        int offset = Integer.parseInt(req.getParameter("offset"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
        List<BootstrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootstrapTableExpress e2 = new BootstrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        List<Map<String, Integer>> console = ExpressService.console();
        int total = console.get(0).get("data1_size");
        //将集合封装为bootstrap识别的格式
        ResultData<BootstrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return JSONUtil.toJSON(data);
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String number = req.getParameter("number");
        String company = req.getParameter("company");
        String username = req.getParameter("username");
        String userPhone = req.getParameter("userPhone");
        String email = req.getParameter("email");
        Express e = new Express(number,username,userPhone,company,UserUtil.getUserPhone(req.getSession()));
        boolean flag = ExpressService.insert(email,e);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("快递录入成功！");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递录入失败！");
        }
        return JSONUtil.toJSON(msg);
     }

    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String number = request.getParameter("number");
        Express e = ExpressService.findByNumber(number);
        Message msg = new Message();
        if(e == null){
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String email = request.getParameter("email");
        String temp = request.getParameter("status");
        Express newExpress = new Express();
        int status;
        if (temp!=null){
            status = Integer.parseInt(temp);
            newExpress.setStatus(status);
        }
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUsername(username);
        newExpress.setUserPhone(userPhone);
        if (userPhone!=null){
            newExpress.setSysPhone(UserUtil.getUserPhone(request.getSession()));
        }
        boolean flag = ExpressService.update(email,id, newExpress);
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

    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = ExpressService.delete(id);
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
