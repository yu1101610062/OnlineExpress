package wx.controller;

import admin.MVC.ResponseBody;
import admin.MVC.ResponseView;
import admin.bean.BootstrapTableExpress;
import admin.bean.Express;
import admin.bean.Message;
import admin.service.CourierService;
import admin.service.ExpressService;
import admin.util.DateFormatUtil;
import admin.util.JSONUtil;
import admin.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QRCodeController {

    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest req, HttpServletResponse resp){
        String code = req.getParameter("code");
        //express | user
        String type = req.getParameter("type");
        String userPhone = null;
        String qRCodeContent = null;
        if ("express".equals(type)){
            //快递二维码，展示单个快递
            //code
            qRCodeContent = "express_"+code;
        }else {
            //用户二维码，展示用户所有快递
            //userPhone
            userPhone = (String) req.getSession().getAttribute("loginPhoneNumber");
            qRCodeContent = "userPhone_"+userPhone;
        }
        req.getSession().setAttribute("qrcode",qRCodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest req,HttpServletResponse resp){
        Message msg = new Message();
        String qrcode = (String) req.getSession().getAttribute("qrcode");
        if (qrcode == null){
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请重新操作");
        }else {
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        String phone = UserUtil.getUserPhone(request.getSession());
        CourierService.sendTimes(UserUtil.getUserPhone(request.getSession()));
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("取件成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("取件码不存在,请用户更新二维码");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootstrapTableExpress e2 =null;
        if(e!=null){
            e2 = new BootstrapTableExpress(e.getId(), e.getNumber(), e.getUsername()
                    , e.getUserPhone(), e.getCompany(), e.getCode(),
                    DateFormatUtil.format(e.getInTime()), e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime()),e.getStatus()==0?"待取件":"已取件", e.getSysPhone());
        }
        Message msg = new Message();
        if(e == null){
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }
}
