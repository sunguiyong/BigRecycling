package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/1 0001.
 */

public class pingzi_info implements Serializable
{


    /**
     * type : bottle
     * last : 0
     * this : 0
     */

    private String type;
    private String last;
    @SerializedName("this")
    private String thisX;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getThisX() {
        return thisX;
    }

    public void setThisX(String thisX) {
        this.thisX = thisX;
    }
}
