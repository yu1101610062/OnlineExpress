package bean;

public class Message {
    //{status:0,result:"",data:{}}

    //状态码 0成功 -1失败
    private int status;
    //消息内容
    private String result;
    //消息携带的数据
    private Object data;

    //全参构造函数
    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }

    //零参构造函数
    public Message() {
    }

    //只包含状态码
    public Message(int status) {
        this.status = status;
    }

    //只包含结果
    public Message(String result) {
        this.result = result;
    }

    //包含结果和数据
    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
