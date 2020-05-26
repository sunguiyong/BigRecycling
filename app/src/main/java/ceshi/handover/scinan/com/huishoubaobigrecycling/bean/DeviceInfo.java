package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

public class DeviceInfo {


    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : {"deviceNumber":"A100023","deviceCheckCode":"8e0f6de142854997ad86c9c4e5b6d315"}
     */

    private Object requestId;
    private Object errorLog;
    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * deviceNumber : A100023
         * deviceCheckCode : 8e0f6de142854997ad86c9c4e5b6d315
         */

        private String deviceNumber;
        private String deviceCheckCode;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public String getDeviceCheckCode() {
            return deviceCheckCode;
        }

        public void setDeviceCheckCode(String deviceCheckCode) {
            this.deviceCheckCode = deviceCheckCode;
        }
    }
}
