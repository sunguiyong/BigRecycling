package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

public class LoginQRCode {

    /**
     * data : {"user":{"avatar":"https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg","category":[{"createTime":1566445509000,"groupId":3,"id":1,"name":"塑料瓶","number":"1234343434","point":10,"unit":2},{"createTime":1570615915000,"groupId":3,"id":6,"name":"塑料","number":"3333333333","point":30,"unit":1},{"createTime":1570615945000,"groupId":3,"id":7,"name":"纸类","number":"44444444444","point":40,"unit":1},{"createTime":1570615977000,"groupId":3,"id":8,"name":"纺织物","number":"5555555555","point":50,"unit":1},{"createTime":1570707219000,"groupId":3,"id":9,"name":"玻璃","number":"6666666666","point":60,"unit":1}],"groupId":3,"icNumber":"123","id":11,"isUsed":0,"phone":"17701540105","point":2580,"regTime":1569221787000,"token":"985e4cddb839457f96b1ca7fcebead07","type":0,"updateTime":1572242649000,"userName":"小小","wxId":"oVxoO5BQSbIgW9-ExbxMI9m-MjhE"}}
     * message : 成功
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * user : {"avatar":"https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg","category":[{"createTime":1566445509000,"groupId":3,"id":1,"name":"塑料瓶","number":"1234343434","point":10,"unit":2},{"createTime":1570615915000,"groupId":3,"id":6,"name":"塑料","number":"3333333333","point":30,"unit":1},{"createTime":1570615945000,"groupId":3,"id":7,"name":"纸类","number":"44444444444","point":40,"unit":1},{"createTime":1570615977000,"groupId":3,"id":8,"name":"纺织物","number":"5555555555","point":50,"unit":1},{"createTime":1570707219000,"groupId":3,"id":9,"name":"玻璃","number":"6666666666","point":60,"unit":1}],"groupId":3,"icNumber":"123","id":11,"isUsed":0,"phone":"17701540105","point":2580,"regTime":1569221787000,"token":"985e4cddb839457f96b1ca7fcebead07","type":0,"updateTime":1572242649000,"userName":"小小","wxId":"oVxoO5BQSbIgW9-ExbxMI9m-MjhE"}
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
             * avatar : https://weapp.iotccn.cn/picture/9da77e582ce8c5c095fa30dd03e74dca.jpg
             * category : [{"createTime":1566445509000,"groupId":3,"id":1,"name":"塑料瓶","number":"1234343434","point":10,"unit":2},{"createTime":1570615915000,"groupId":3,"id":6,"name":"塑料","number":"3333333333","point":30,"unit":1},{"createTime":1570615945000,"groupId":3,"id":7,"name":"纸类","number":"44444444444","point":40,"unit":1},{"createTime":1570615977000,"groupId":3,"id":8,"name":"纺织物","number":"5555555555","point":50,"unit":1},{"createTime":1570707219000,"groupId":3,"id":9,"name":"玻璃","number":"6666666666","point":60,"unit":1}]
             * groupId : 3
             * icNumber : 123
             * id : 11
             * isUsed : 0
             * phone : 17701540105
             * point : 2580
             * regTime : 1569221787000
             * token : 985e4cddb839457f96b1ca7fcebead07
             * type : 0
             * updateTime : 1572242649000
             * userName : 小小
             * wxId : oVxoO5BQSbIgW9-ExbxMI9m-MjhE
             */

            private String avatar;
            private int groupId;
            private String icNumber;
            private int id;
            private int isUsed;
            private String phone;
            private int point;
            private long regTime;
            private String token;
            private int type;
            private long updateTime;
            private String userName;
            private String wxId;
            private List<CategoryBean> category;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getIcNumber() {
                return icNumber;
            }

            public void setIcNumber(String icNumber) {
                this.icNumber = icNumber;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsUsed() {
                return isUsed;
            }

            public void setIsUsed(int isUsed) {
                this.isUsed = isUsed;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public long getRegTime() {
                return regTime;
            }

            public void setRegTime(long regTime) {
                this.regTime = regTime;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getWxId() {
                return wxId;
            }

            public void setWxId(String wxId) {
                this.wxId = wxId;
            }

            public List<CategoryBean> getCategory() {
                return category;
            }

            public void setCategory(List<CategoryBean> category) {
                this.category = category;
            }

            public static class CategoryBean {
                /**
                 * createTime : 1566445509000
                 * groupId : 3
                 * id : 1
                 * name : 塑料瓶
                 * number : 1234343434
                 * point : 10
                 * unit : 2
                 */

                private long createTime;
                private int groupId;
                private int id;
                private String name;
                private String number;
                private int point;
                private int unit;

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

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public int getUnit() {
                    return unit;
                }

                public void setUnit(int unit) {
                    this.unit = unit;
                }
            }
        }
    }
}
