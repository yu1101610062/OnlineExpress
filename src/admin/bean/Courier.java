package admin.bean;

import java.sql.Timestamp;

public class Courier {
    private int id;
    private String courierName;
    private String phoneNumber;
    private String idNumber;
    private String password;
    private int sendExpress;
    private Timestamp joinTime;
    private Timestamp loginTime;

    public Courier() {
    }

    public Courier(int id, String courierName, String phoneNumber, String idNumber, String password, int sendExpress, Timestamp joinTime, Timestamp loginTime) {
        this.id = id;
        this.courierName = courierName;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.password = password;
        this.sendExpress = sendExpress;
        this.joinTime = joinTime;
        this.loginTime = loginTime;
    }

    public Courier(String courierName, String phoneNumber, String idNumber, String password) {
        this.courierName = courierName;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.password = password;
        this.joinTime = joinTime;
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

    public int getSendExpress() {
        return sendExpress;
    }

    public void setSendExpress(int sendExpress) {
        this.sendExpress = sendExpress;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
