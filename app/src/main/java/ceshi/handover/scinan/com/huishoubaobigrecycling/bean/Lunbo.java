package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

public class Lunbo {
    @Override
    public String toString() {
        return "Lunbo{" +
                "requestId=" + requestId +
                ", errorLog=" + errorLog +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : [{"id":1,"name":"xxx","state":null,"position":"ad2","groupId":3,"type":2,"fileName":null,"fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0},{"id":2,"name":null,"state":null,"position":"ad1","groupId":3,"type":2,"fileName":"ad1","fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0},{"id":3,"name":null,"state":null,"position":"ad6","groupId":3,"type":2,"fileName":null,"fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0},{"id":4,"name":null,"state":null,"position":"ad3","groupId":3,"type":2,"fileName":null,"fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0},{"id":5,"name":null,"state":null,"position":"ad4","groupId":3,"type":2,"fileName":null,"fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0},{"id":6,"name":null,"state":null,"position":"ad5","groupId":3,"type":2,"fileName":null,"fileUrl":"https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg","startDate":null,"endDate":null,"playStartTime":null,"playEndTime":null,"playType":null,"createTime":null,"isDelete":0}]
     */

    private Object requestId;
    private Object errorLog;
    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : xxx
         * state : null
         * position : ad2
         * groupId : 3
         * type : 2
         * fileName : null
         * fileUrl : https://weapp.iotccn.cn/picture/dd261db21ca84ee4474bcc20df439789.jpg
         * startDate : null
         * endDate : null
         * playStartTime : null
         * playEndTime : null
         * playType : null
         * createTime : null
         * isDelete : 0
         */

        private int id;
        private String name;
        private Object state;
        private String position;
        private int groupId;
        private int type;
        private Object fileName;
        private String fileUrl;
        private Object startDate;
        private Object endDate;
        private Object playStartTime;
        private Object playEndTime;
        private Object playType;
        private Object createTime;
        private int isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getFileName() {
            return fileName;
        }

        public void setFileName(Object fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getPlayStartTime() {
            return playStartTime;
        }

        public void setPlayStartTime(Object playStartTime) {
            this.playStartTime = playStartTime;
        }

        public Object getPlayEndTime() {
            return playEndTime;
        }

        public void setPlayEndTime(Object playEndTime) {
            this.playEndTime = playEndTime;
        }

        public Object getPlayType() {
            return playType;
        }

        public void setPlayType(Object playType) {
            this.playType = playType;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }
    }
}
