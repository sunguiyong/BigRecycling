package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public class LunBo_Info implements Serializable {

    /**
     * status : 200
     * message : [{"id":1,"title":"xxxxx","url":"/upload/sowing/334234234234.jpg","link":"http://www.baidu.com","sort":20},{"id":2,"title":"xxxxx","url":"/upload/sowing/334234234234.jpg","link":"http://www.baidu.com","sort":20}]
     */

    private int status;
    private List<MessageBean> message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 1
         * title : xxxxx
         * url : /upload/sowing/334234234234.jpg
         * link : http://www.baidu.com
         * sort : 20
         */

        private int id;
        private String title;
        private String url;
        private String link;
        private int sort;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
