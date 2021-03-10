package admin.controller;

import admin.MVC.ResponseBody;
import admin.bean.Message;
import admin.service.AdminService;
import admin.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        //接参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用Service传参数，获取结果
        boolean result = AdminService.login(username, password);
        //根据结果,准备不同的返回数据
        Message msg = null;
        if (result) {
            msg = new Message(0, "登陆成功");//{status:0,result:"登陆成功"}
            Date date = new Date();
            String ip = request.getRemoteAddr();
            AdminService.updateLoginTimeAndIP(username,date,ip);
            request.getSession().setAttribute("adminUserName",username);
        } else {
            msg = new Message(-1, "登陆失败");//{status:-1,result:"登陆失败"}
        }
        //转化为json并返回ajax
        return JSONUtil.toJSON(msg);
    }
}
