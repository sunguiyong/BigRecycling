package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

public class DeviceInfo {

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : B100014
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
