package controller;

import MVC.ResponseBody;
import bean.BootstrapTableUser;
import bean.Message;
import bean.ResultData;
import bean.User;
import service.UserService;
import util.DateFormatUtil;
import util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest req, HttpServletResponse resp){
        Map<String,Integer> data = UserService.console();
        Message msg = new Message();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp){
        int offset = Integer.parseInt(req.getParameter("offset"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootstrapTableUser> list2 = new ArrayList<>();
        for(User u:list){
            String joinTime = DateFormatUtil.format(u.getJoinTime());
            String loginTime = u.getLoginTime()==null?"未曾登录":DateFormatUtil.format(u.getLoginTime());
            BootstrapTableUser u2 = new BootstrapTableUser(u.getId(),u.getUserName(),u.getPhoneNumber(),u.getPassword(),joinTime,loginTime);
            list2.add(u2);
        }
        Map<String, Integer> console = UserService.console();
        int total = console.get("data1_size");
        //将集合封装为bootstrap识别的格式
        ResultData<BootstrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return JSONUtil.toJSON(data);
    }
    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("userName");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        User u = new User(userName,phoneNumber,password);
        boolean flag = UserService.insert(u);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("用户录入成功！");
        }else {
            msg.setStatus(-1);
            msg.setResult("用户录入失败！");
        }
        return JSONUtil.toJSON(msg);
    }
    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String phoneNumber = request.getParameter("phoneNumber");
        User u = UserService.findByPhone(phoneNumber);
        Message msg = new Message();
        if(u == null){
            msg.setStatus(-1);
            msg.setResult("手机号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        User newUser = new User();
        int id = Integer.parseInt(request.getParameter("id"));
        newUser.setUserName(request.getParameter("userName"));
        newUser.setPhoneNumber(request.getParameter("phoneNumber"));
        newUser.setPassword(request.getParameter("password"));

        boolean flag = UserService.update(id,newUser);
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

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = UserService.delete(id);
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
