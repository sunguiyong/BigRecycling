package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public class Erweima implements Serializable {

    /**
     * status : 200
     * message : {"id":"KEY_45GIK","val":"76d8acb4-573b-41a6-a7bd-61af67d454e1","expire":"2018/10/27 14:17:05"}
     */

    private int status;
    private MessageBean message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : KEY_45GIK
         * val : 76d8acb4-573b-41a6-a7bd-61af67d454e1
         * expire : 2018/10/27 14:17:05
         */

        private String id;
        private String val;
        private String expire;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }
    }
}
