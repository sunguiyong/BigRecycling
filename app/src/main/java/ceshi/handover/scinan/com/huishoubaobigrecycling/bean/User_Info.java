package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/31 0031.
 */

public class User_Info implements Serializable {

    /**
     * message : 登录成功！
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjEzNDAyODU4NDUiLCJuYW1laWQiOiIxIiwibmJmIjoxNTQwOTc4MDM0LCJleHAiOjE1NDYxNjIwMzQsImlhdCI6MTU0MDk3ODAzNH0.lMjMUZN5NPG94It8suRn9d3EouFP-fNOLag0qzHUUn0
     * user : {"mobile":"1340285845","nike":"1340285845"}
     */

    private String message;
    private String token;
    private UserBean user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * mobile : 1340285845
         * nike : 1340285845
         */

        private String mobile;
        private String nike;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNike() {
            return nike;
        }

        public void setNike(String nike) {
            this.nike = nike;
        }
    }
}
