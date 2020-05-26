package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

public class VersionApk {

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : {"id":19,"version":"啊啊啊啊","content":"阿诗丹顿师德师风","url":"C:/energySystem/tomcat-8084/webapps/garbageClassifyManageSystem/resources/testupload/huishou.apk","md5":"斯蒂芬","fileSize":null,"utype":0,"state":1,"sysUserId":null,"createTime":1578369657000,"groupId":3}
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
         * id : 19
         * version : 啊啊啊啊
         * content : 阿诗丹顿师德师风
         * url : C:/energySystem/tomcat-8084/webapps/garbageClassifyManageSystem/resources/testupload/huishou.apk
         * md5 : 斯蒂芬
         * fileSize : null
         * utype : 0
         * state : 1
         * sysUserId : null
         * createTime : 1578369657000
         * groupId : 3
         */

        private int id;
        private String version;
        private String content;
        private String url;
        private String md5;
        private Object fileSize;
        private int utype;
        private int state;
        private Object sysUserId;
        private long createTime;
        private int groupId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public Object getFileSize() {
            return fileSize;
        }

        public void setFileSize(Object fileSize) {
            this.fileSize = fileSize;
        }

        public int getUtype() {
            return utype;
        }

        public void setUtype(int utype) {
            this.utype = utype;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getSysUserId() {
            return sysUserId;
        }

        public void setSysUserId(Object sysUserId) {
            this.sysUserId = sysUserId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
    }
}
