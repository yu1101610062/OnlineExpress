package bean;

public class BootstrapTableCourier {
    private int id;
    private String courierName;
    private String phoneNumber;
    private String idNumber;
    private String password;
    private String sendExpress;
    private String joinTime;
    private String loginTime;

    public BootstrapTableCourier(int id, String courierName, String phoneNumber, String idNumber, String password, String sendExpress, String joinTime, String loginTime) {
        this.id = id;
        this.courierName = courierName;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.password = password;
        this.sendExpress = sendExpress;
        this.joinTime = joinTime;
        this.loginTime = loginTime;
    }

    public BootstrapTableCourier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSendExpress() {
        return sendExpress;
    }

    public void setSendExpress(String sendExpress) {
        this.sendExpress = sendExpress;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
