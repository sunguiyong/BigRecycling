package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class Face_Info implements Serializable {


    /**
     * message : 登录成功！
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6I....
     * user : {"mobile":"18256285451","nike":"180****8548"}
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
         * mobile : 18256285451
         * nike : 180****8548
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
