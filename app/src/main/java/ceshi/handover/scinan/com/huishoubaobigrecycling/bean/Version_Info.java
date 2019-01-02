package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/23 0023.
 */

public class Version_Info implements Serializable {

    /**
     * status : 200
     * message : {"id":3233,"version":"v4.025","download":"http:///wwwwxxxx.com/ssxxxx.apk","type":1}
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
         * id : 3233
         * version : v4.025
         * download : http:///wwwwxxxx.com/ssxxxx.apk
         * type : 1
         */

        private int id;
        private String version;
        private String download;
        private int type;

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

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
