package admin.util;

import admin.bean.Courier;
import admin.bean.User;

import javax.servlet.http.HttpSession;

//TODO : 用户还没登录，还没编写柜子端，未存储任何录入人信息
public class UserUtil {
    public static String getUserName(HttpSession session) {
        return (String) session.getAttribute("adminUserName");
    }

    public static String getUserPhone(HttpSession session) {
        return (String) session.getAttribute("loginPhoneNumber");
    }

    public static void setUserPhone(HttpSession session,String phone){
        session.setAttribute("loginPhoneNumber",phone);
    }

    public static String getLoginSms(HttpSession session, String userPhone) {
        return (String) session.getAttribute(userPhone);
    }

    public static void setLoginSms(HttpSession session, String userPhone, String code) {
        session.setAttribute(userPhone, code);
    }

    public static void setWxUser(HttpSession session, User user) {
        session.setAttribute("wxUser", user);
    }

    public static User getWxUser(HttpSession session) {
        return (User) session.getAttribute("wxUser");
    }

    public static void setWxCourier(HttpSession session, Courier courier) {
        session.setAttribute("wxCourier", courier);
    }

    public static Courier getWxCourier(HttpSession session) {
        return (Courier) session.getAttribute("wxCourier");
    }
}
