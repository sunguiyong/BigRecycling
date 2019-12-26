package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

public class LoginResult {

    /**
     * requestId : null
     * errorLog : null
     * status : 200
     * message : 成功
     * data : {"user":{"id":11,"userName":"小小","password":null,"phone":"17701540105","avatar":"https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg","isUsed":0,"regTime":1569221787000,"updateTime":1572242649000,"lastLoginTime":null,"groupId":3,"point":2320,"level":null,"icNumber":"123","type":0,"userAddress":null,"wxId":"oVxoO5BQSbIgW9-ExbxMI9m-MjhE","category":[{"id":1,"number":"1234343434","name":"塑料瓶","groupId":3,"unit":2,"point":10,"createTime":1566445509000,"total":null},{"id":6,"number":"3333333333","name":"塑料","groupId":3,"unit":1,"point":30,"createTime":1570615915000,"total":null},{"id":7,"number":"44444444444","name":"纸类","groupId":3,"unit":1,"point":40,"createTime":1570615945000,"total":null},{"id":8,"number":"5555555555","name":"纺织物","groupId":3,"unit":1,"point":50,"createTime":1570615977000,"total":null},{"id":9,"number":"6666666666","name":"玻璃","groupId":3,"unit":1,"point":60,"createTime":1570707219000,"total":null}],"token":"985e4cddb839457f96b1ca7fcebead07"}}
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
         * user : {"id":11,"userName":"小小","password":null,"phone":"17701540105","avatar":"https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg","isUsed":0,"regTime":1569221787000,"updateTime":1572242649000,"lastLoginTime":null,"groupId":3,"point":2320,"level":null,"icNumber":"123","type":0,"userAddress":null,"wxId":"oVxoO5BQSbIgW9-ExbxMI9m-MjhE","category":[{"id":1,"number":"1234343434","name":"塑料瓶","groupId":3,"unit":2,"point":10,"createTime":1566445509000,"total":null},{"id":6,"number":"3333333333","name":"塑料","groupId":3,"unit":1,"point":30,"createTime":1570615915000,"total":null},{"id":7,"number":"44444444444","name":"纸类","groupId":3,"unit":1,"point":40,"createTime":1570615945000,"total":null},{"id":8,"number":"5555555555","name":"纺织物","groupId":3,"unit":1,"point":50,"createTime":1570615977000,"total":null},{"id":9,"number":"6666666666","name":"玻璃","groupId":3,"unit":1,"point":60,"createTime":1570707219000,"total":null}],"token":"985e4cddb839457f96b1ca7fcebead07"}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 11
             * userName : 小小
             * password : null
             * phone : 17701540105
             * avatar : https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg
             * isUsed : 0
             * regTime : 1569221787000
             * updateTime : 1572242649000
             * lastLoginTime : null
             * groupId : 3
             * point : 2320
             * level : null
             * icNumber : 123
             * type : 0
             * userAddress : null
             * wxId : oVxoO5BQSbIgW9-ExbxMI9m-MjhE
             * category : [{"id":1,"number":"1234343434","name":"塑料瓶","groupId":3,"unit":2,"point":10,"createTime":1566445509000,"total":null},{"id":6,"number":"3333333333","name":"塑料","groupId":3,"unit":1,"point":30,"createTime":1570615915000,"total":null},{"id":7,"number":"44444444444","name":"纸类","groupId":3,"unit":1,"point":40,"createTime":1570615945000,"total":null},{"id":8,"number":"5555555555","name":"纺织物","groupId":3,"unit":1,"point":50,"createTime":1570615977000,"total":null},{"id":9,"number":"6666666666","name":"玻璃","groupId":3,"unit":1,"point":60,"createTime":1570707219000,"total":null}]
             * token : 985e4cddb839457f96b1ca7fcebead07
             */

            private int id;
            private String userName;
            private Object password;
            private String phone;
            private String avatar;
            private int isUsed;
            private long regTime;
            private long updateTime;
            private Object lastLoginTime;
            private int groupId;
            private int point;
            private Object level;
            private String icNumber;
            private int type;
            private Object userAddress;
            private String wxId;
            private String token;
            private List<CategoryBean> category;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getIsUsed() {
                return isUsed;
            }

            public void setIsUsed(int isUsed) {
                this.isUsed = isUsed;
            }

            public long getRegTime() {
                return regTime;
            }

            public void setRegTime(long regTime) {
                this.regTime = regTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public Object getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(Object lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public Object getLevel() {
                return level;
            }

            public void setLevel(Object level) {
                this.level = level;
            }

            public String getIcNumber() {
                return icNumber;
            }

            public void setIcNumber(String icNumber) {
                this.icNumber = icNumber;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUserAddress() {
                return userAddress;
            }

            public void setUserAddress(Object userAddress) {
                this.userAddress = userAddress;
            }

            public String getWxId() {
                return wxId;
            }

            public void setWxId(String wxId) {
                this.wxId = wxId;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public List<CategoryBean> getCategory() {
                return category;
            }

            public void setCategory(List<CategoryBean> category) {
                this.category = category;
            }

            public static class CategoryBean {
                /**
                 * id : 1
                 * number : 1234343434
                 * name : 塑料瓶
                 * groupId : 3
                 * unit : 2
                 * point : 10
                 * createTime : 1566445509000
                 * total : null
                 */

                private int id;
                private String number;
                private String name;
                private int groupId;
                private int unit;
                private int point;
                private long createTime;
                private Object total;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
                }

                public int getUnit() {
                    return unit;
                }

                public void setUnit(int unit) {
                    this.unit = unit;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public Object getTotal() {
                    return total;
                }

                public void setTotal(Object total) {
                    this.total = total;
                }
            }
        }
    }
}
