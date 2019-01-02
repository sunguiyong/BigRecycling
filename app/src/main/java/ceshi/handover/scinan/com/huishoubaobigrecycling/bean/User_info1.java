package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/18 0018.
 */

public class User_info1 implements Serializable{


    /**
     * status : 200
     * message : {"account":"18035265458","mobile":"18035265458","mobile_confirmed":1,"face_token":null,"nick":"180****5458","avatar":null,"points":0,"achieve":0,"enbale_push":true,"province":null,"city":null,"area":null,"village":null,"address":null,"wx_account":"","created_time":"2018-10-17T09:42:30.673"}
     */

        /**
         * account : 18035265458
         * mobile : 18035265458
         * mobile_confirmed : 1
         * face_token : null
         * nick : 180****5458
         * avatar : null
         * points : 0
         * achieve : 0
         * enbale_push : true
         * province : null
         * city : null
         * area : null
         * village : null
         * address : null
         * wx_account :
         * created_time : 2018-10-17T09:42:30.673
         */

        private String account;
        private String mobile;
        private int mobile_confirmed;
        private String face_token;
        private String nick;
        private String avatar;
        private int points;
        private int achieve;
        private boolean enbale_push;
        private String province;
        private String city;
        private String area;
        private String village;
        private String address;
        private String wx_account;
        private String created_time;
        private boolean authenticated;
        String wx_nick;

    public String getWx_nick() {
        return wx_nick;
    }

    public void setWx_nick(String wx_nick) {
        this.wx_nick = wx_nick;
    }

    public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getMobile_confirmed() {
            return mobile_confirmed;
        }

        public void setMobile_confirmed(int mobile_confirmed) {
            this.mobile_confirmed = mobile_confirmed;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getAchieve() {
            return achieve;
        }

        public void setAchieve(int achieve) {
            this.achieve = achieve;
        }

        public boolean isEnbale_push() {
            return enbale_push;
        }

        public void setEnbale_push(boolean enbale_push) {
            this.enbale_push = enbale_push;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWx_account() {
            return wx_account;
        }

        public void setWx_account(String wx_account) {
            this.wx_account = wx_account;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

}
