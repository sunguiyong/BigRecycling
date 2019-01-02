package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4 0004.
 */

public class request_result {

    public request_result(String identity, List<ItemsBean> items) {
        this.identity = identity;
        this.items = items;
    }

    /**
     * identity : 123456789
     * items : [{"alias":"METAL","count":5.3},{"alias":"PAPER","count":5.3}]
     */

    private String identity;
    private List<ItemsBean> items;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        public ItemsBean(String alias, String count) {
            this.alias = alias;
            this.count = count;
        }

        /**
         * alias : METAL
         * count : 5.3
         */

        private String alias;
        private String count;

        public String getAlias() {
            return alias;
        }

        public String setAlias(String alias) {
            this.alias = alias;
            return alias;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
