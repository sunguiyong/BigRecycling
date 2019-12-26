package ceshi.handover.scinan.com.huishoubaobigrecycling.entity;

public class UnitEntity {

    /**
     * 配件编号
     */
    private int unit_no;

    /**
     * 配件类型
     */
    private int unit_type;

    /**
     * 配件名称
     */
    private String unit_name;

    public UnitEntity(int unit_no, int unit_type, String unit_name) {
        this.unit_no = unit_no;
        this.unit_type = unit_type;
        this.unit_name = unit_name;
    }

    public int getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(int unit_no) {
        this.unit_no = unit_no;
    }

    public int getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(int unit_type) {
        this.unit_type = unit_type;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
