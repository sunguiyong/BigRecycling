package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

public class LunboV {

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : [{"id":27,"name":"测试","state":1,"groupId":3,"startDate":1577808000000,"endDate":1580400000000,"playStartTime":"23:59:00","playEndTime":"02:59:00","playType":1,"createTime":1578034719000,"isDelete":0,"list":[{"id":37,"name":"测试视屏1","path":"http://114.116.37.87:8084/garbageClassifyManageSystem/resources/images/5bfaee44d70eae6b8f918bb4f626e010.mp4","type":1,"createTime":1578650095000,"groupId":3,"playTime":4},{"id":24,"name":"资源01","path":"http://114.116.37.87:8084/resources/images/20191231155953p2ok7.jpg","type":0,"createTime":1577779197000,"groupId":3,"playTime":3},{"id":38,"name":"测试视屏2","path":"http://114.116.37.87:8084/garbageClassifyManageSystem/resources/images/b4129677b157797146e2e8903b153689.mp4","type":1,"createTime":1578650127000,"groupId":3,"playTime":8},{"id":26,"name":"资源02","path":"http://114.116.37.87:8084/resources/images/20200108110829gdugo.jpg","type":0,"createTime":1578452910000,"groupId":3,"playTime":3}]}]
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
         * id : 27
         * name : 测试
         * state : 1
         * groupId : 3
         * startDate : 1577808000000
         * endDate : 1580400000000
         * playStartTime : 23:59:00
         * playEndTime : 02:59:00
         * playType : 1
         * createTime : 1578034719000
         * isDelete : 0
         * list : [{"id":37,"name":"测试视屏1","path":"http://114.116.37.87:8084/garbageClassifyManageSystem/resources/images/5bfaee44d70eae6b8f918bb4f626e010.mp4","type":1,"createTime":1578650095000,"groupId":3,"playTime":4},{"id":24,"name":"资源01","path":"http://114.116.37.87:8084/resources/images/20191231155953p2ok7.jpg","type":0,"createTime":1577779197000,"groupId":3,"playTime":3},{"id":38,"name":"测试视屏2","path":"http://114.116.37.87:8084/garbageClassifyManageSystem/resources/images/b4129677b157797146e2e8903b153689.mp4","type":1,"createTime":1578650127000,"groupId":3,"playTime":8},{"id":26,"name":"资源02","path":"http://114.116.37.87:8084/resources/images/20200108110829gdugo.jpg","type":0,"createTime":1578452910000,"groupId":3,"playTime":3}]
         */

        private int id;
        private String name;
        private int state;
        private int groupId;
        private long startDate;
        private long endDate;
        private String playStartTime;
        private String playEndTime;
        private int playType;
        private long createTime;
        private int isDelete;
        private List<ListBean> list;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public String getPlayStartTime() {
            return playStartTime;
        }

        public void setPlayStartTime(String playStartTime) {
            this.playStartTime = playStartTime;
        }

        public String getPlayEndTime() {
            return playEndTime;
        }

        public void setPlayEndTime(String playEndTime) {
            this.playEndTime = playEndTime;
        }

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 37
             * name : 测试视屏1
             * path : http://114.116.37.87:8084/garbageClassifyManageSystem/resources/images/5bfaee44d70eae6b8f918bb4f626e010.mp4
             * type : 1
             * createTime : 1578650095000
             * groupId : 3
             * playTime : 4
             */

            private int id;
            private String name;
            private String path;
            private int type;
            private long createTime;
            private int groupId;
            private int playTime;

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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public int getPlayTime() {
                return playTime;
            }

            public void setPlayTime(int playTime) {
                this.playTime = playTime;
            }
        }
    }

    @Override
    public String toString() {
        return "LunboV{" +
                "requestId=" + requestId +
                ", errorLog=" + errorLog +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
