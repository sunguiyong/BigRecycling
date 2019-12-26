package ceshi.handover.scinan.com.huishoubaobigrecycling.mybean;

public class QRCode {

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : https://weapp.iotccn.cn/picture/1571923687791.jpg
     */

    private Object requestId;
    private Object errorLog;
    private int status;
    private String message;
    private String data;

    public Object getRequestId() {
        return requestId;
    }

    public void setRequestId(Object requestId) {
        this.requestId = requestId;
    }

    public Object getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(Object errorLog) {
        this.errorLog = errorLog;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
