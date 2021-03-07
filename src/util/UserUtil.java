package util;

import javax.servlet.http.HttpSession;
//TODO : 用户还没登录，还没编写柜子端，未存储任何录入人信息
public class UserUtil {
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static String getUserPhone(HttpSession session){
        return "18888888888";
    }
}
