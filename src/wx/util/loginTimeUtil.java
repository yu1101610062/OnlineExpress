package wx.util;

import admin.service.CourierService;
import admin.service.UserService;

public class loginTimeUtil {
    public static void loginTime(String phoneNumber){
        CourierService.loginTime(phoneNumber);
        UserService.loginTime(phoneNumber);
    }
}
