package wx.controller;

import admin.MVC.ResponseBody;
import admin.bean.Courier;
import admin.bean.Message;
import admin.bean.User;
import admin.service.CourierService;
import admin.service.UserService;
import admin.util.JSONUtil;
import admin.util.MailUtil;
import admin.util.RandomUtil;
import admin.util.UserUtil;
import wx.util.loginTimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {
    @ResponseBody("/wx/mail.do")
    public String sendMail(HttpServletRequest req, HttpServletResponse resp) {
        String mail = req.getParameter("mail");
        String phone = req.getParameter("phone");
        if (phone == null){
            phone = UserUtil.getUserPhone(req.getSession());
        }
        String code = RandomUtil.getCode() + "";
        MailUtil.SendMail(3, code, mail);
        Message msg = new Message();
        msg.setStatus(0);
        msg.setResult("如果邮件地址正确，您会收到一封包含验证码的邮件");
        UserUtil.setLoginSms(req.getSession(), phone, code);
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String userPhone = req.getParameter("userPhone");
        String uCode = req.getParameter("code");
        String code = UserUtil.getLoginSms(req.getSession(), userPhone);
        Message msg = new Message();
        req.getSession().setAttribute("newUser", 0);
        if (code == null) {
            msg.setStatus(-1);
            msg.setResult("该手机号未获取验证码");
        } else if (code.equals(uCode)) {
            req.getSession().setAttribute(userPhone,null);
            Courier sysCourier = CourierService.findByPhone(userPhone);
            User sysUser = UserService.findByPhone(userPhone);
            if (sysCourier != null) {
                msg.setStatus(1);
                UserUtil.setWxCourier(req.getSession(), sysCourier);
                loginTimeUtil.loginTime(userPhone);
            } else if (sysUser != null) {
                msg.setStatus(2);
                UserUtil.setWxUser(req.getSession(), sysUser);
                loginTimeUtil.loginTime(userPhone);
            } else {
                msg.setStatus(0);
                msg.setResult("欢迎您，新用户！");
                User user = new User();
                user.setPhoneNumber(userPhone);
                UserUtil.setWxUser(req.getSession(), user);
            }
            UserUtil.setUserPhone(req.getSession(), userPhone);
        } else {
            msg.setStatus(-2);
            msg.setResult("验证码错误");
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/reg.do")
    public String reg(HttpServletRequest req,HttpServletResponse resp){
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        String sysCode = UserUtil.getLoginSms(req.getSession(),UserUtil.getUserPhone(req.getSession()));
        Message msg = new Message();
        if (code.equals(sysCode)){
            msg.setStatus(0);
            User user = new User(name,UserUtil.getUserPhone(req.getSession()),password);
            UserService.insert(user);
        }else {
            msg.setStatus(-1);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest req, HttpServletResponse resp) {
        User wxUser = UserUtil.getWxUser(req.getSession());
        Courier wxCourier = UserUtil.getWxCourier(req.getSession());
        Message message = new Message();
        if (wxUser != null) {
            message.setStatus(0);
            message.setResult(wxUser.getPhoneNumber());
        } else {
            message.setStatus(1);
            message.setResult(wxCourier.getPhoneNumber());
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
