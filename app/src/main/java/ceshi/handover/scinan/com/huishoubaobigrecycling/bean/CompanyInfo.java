package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import java.util.List;

public class CompanyInfo {

    /**
     * ad_lamp_off_time : -25260000
     * ad_lamp_on_time : 54000000
     * address : 雅风企业
     * cabinList : [{"category_id":1,"create_time":1578626850000,"device_id":27,"device_number":"A100028","group_id":3,"id":88,"name":"塑料瓶","state":0,"weight_quantity":0},{"category_id":6,"create_time":1578626851000,"device_id":27,"device_number":"A100028","group_id":3,"id":89,"name":"塑料","state":0,"weight_quantity":0},{"category_id":7,"create_time":1578626851000,"device_id":27,"device_number":"A100028","group_id":3,"id":90,"name":"纸类","state":1,"weight_quantity":0},{"category_id":8,"create_time":1578626851000,"device_id":27,"device_number":"A100028","group_id":3,"id":91,"name":"纺织物","state":1,"weight_quantity":0},{"category_id":9,"create_time":1578626851000,"device_id":27,"device_number":"A100028","group_id":3,"id":92,"name":"玻璃","state":1,"weight_quantity":0}]
     * check_code : cb818390832542c8991e119aadf7d412
     * create_time : 1578624061000
     * group_id : 3
     * group_name : 灵加科技
     * id : 27
     * imei : 111
     * lat : 31.43357
     * lng : 121.19714
     * number : A100028
     * shut_down_time : -21660000
     * sleep_end_time : -25260000
     * sleep_start_time : 57540000
     * start_up_time : -25260000
     * state : 2
     * temperature_threshold : 50
     * work_user_id : 5
     * zone_id : 54
     */

    private int ad_lamp_off_time;
    private int ad_lamp_on_time;
    private String address;
    private String check_code;
    private long create_time;
    private int group_id;
    private String group_name;
    private int id;
    private String imei;
    private double lat;
    private double lng;
    private String number;
    private int shut_down_time;
    private int sleep_end_time;
    private int sleep_start_time;
    private int start_up_time;
    private int state;
    private int temperature_threshold;
    private int work_user_id;
    private int zone_id;
    private List<CabinListBean> cabinList;

    public int getAd_lamp_off_time() {
        return ad_lamp_off_time;
    }

    public void setAd_lamp_off_time(int ad_lamp_off_time) {
        this.ad_lamp_off_time = ad_lamp_off_time;
    }

    public int getAd_lamp_on_time() {
        return ad_lamp_on_time;
    }

    public void setAd_lamp_on_time(int ad_lamp_on_time) {
        this.ad_lamp_on_time = ad_lamp_on_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheck_code() {
        return check_code;
    }

    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getShut_down_time() {
        return shut_down_time;
    }

    public void setShut_down_time(int shut_down_time) {
        this.shut_down_time = shut_down_time;
    }

    public int getSleep_end_time() {
        return sleep_end_time;
    }

    public void setSleep_end_time(int sleep_end_time) {
        this.sleep_end_time = sleep_end_time;
    }

    public int getSleep_start_time() {
        return sleep_start_time;
    }

    public void setSleep_start_time(int sleep_start_time) {
        this.sleep_start_time = sleep_start_time;
    }

    public int getStart_up_time() {
        return start_up_time;
    }

    public void setStart_up_time(int start_up_time) {
        this.start_up_time = start_up_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTemperature_threshold() {
        return temperature_threshold;
    }

    public void setTemperature_threshold(int temperature_threshold) {
        this.temperature_threshold = temperature_threshold;
    }

    public int getWork_user_id() {
        return work_user_id;
    }

    public void setWork_user_id(int work_user_id) {
        this.work_user_id = work_user_id;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public List<CabinListBean> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinListBean> cabinList) {
        this.cabinList = cabinList;
    }

    public static class CabinListBean {
        /**
         * category_id : 1
         * create_time : 1578626850000
         * device_id : 27
         * device_number : A100028
         * group_id : 3
         * id : 88
         * name : 塑料瓶
         * state : 0
         * weight_quantity : 0
         */

        private int category_id;
        private long create_time;
        private int device_id;
        private String device_number;
        private int group_id;
        private int id;
        private String name;
        private int state;
        private int weight_quantity;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public String getDevice_number() {
            return device_number;
        }

        public void setDevice_number(String device_number) {
            this.device_number = device_number;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getWeight_quantity() {
            return weight_quantity;
        }

        public void setWeight_quantity(int weight_quantity) {
            this.weight_quantity = weight_quantity;
        }
    }
}
