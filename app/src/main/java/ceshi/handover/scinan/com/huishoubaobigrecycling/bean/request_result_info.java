package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class request_result_info implements Serializable {

    /**
     * status : 200
     * message : {"DoorSet":[],"Prices":[{"name":"金属","alias":"METAL","count":5.3,"unit_price":2.3,"total":12.19,"unit":null},{"name":"纸","alias":"PAPER","count":5.3,"unit_price":1.6,"total":8.48,"unit":null}],"Points":206,"Achieve":206,"Page":"RECOVERY"}
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
         * DoorSet : []
         * Prices : [{"name":"金属","alias":"METAL","count":5.3,"unit_price":2.3,"total":12.19,"unit":null},{"name":"纸","alias":"PAPER","count":5.3,"unit_price":1.6,"total":8.48,"unit":null}]
         * Points : 206
         * Achieve : 206
         * Page : RECOVERY
         */

        private int Points;
        private int Achieve;
        private String Page;
        private List<String> DoorSet;
        private List<PricesBean> Prices;

        public int getPoints() {
            return Points;
        }

        public void setPoints(int Points) {
            this.Points = Points;
        }

        public int getAchieve() {
            return Achieve;
        }

        public void setAchieve(int Achieve) {
            this.Achieve = Achieve;
        }

        public String getPage() {
            return Page;
        }

        public void setPage(String Page) {
            this.Page = Page;
        }

        public List<String> getDoorSet() {
            return DoorSet;
        }

        public void setDoorSet(List<String> DoorSet) {
            this.DoorSet = DoorSet;
        }

        public List<PricesBean> getPrices() {
            return Prices;
        }

        public void setPrices(List<PricesBean> Prices) {
            this.Prices = Prices;
        }

        public static class PricesBean {
            /**
             * name : 金属
             * alias : METAL
             * count : 5.3
             * unit_price : 2.3
             * total : 12.19
             * unit : null
             */

            private String name;
            private String alias;
            private double count;
            private double unit_price;
            private double total;
            private Object unit;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public double getCount() {
                return count;
            }

            public void setCount(double count) {
                this.count = count;
            }

            public double getUnit_price() {
                return unit_price;
            }

            public void setUnit_price(double unit_price) {
                this.unit_price = unit_price;
            }

            public double getTotal() {
                return total;
            }

            public void setTotal(double total) {
                this.total = total;
            }

            public Object getUnit() {
                return unit;
            }

            public void setUnit(Object unit) {
                this.unit = unit;
            }
        }
    }
}
