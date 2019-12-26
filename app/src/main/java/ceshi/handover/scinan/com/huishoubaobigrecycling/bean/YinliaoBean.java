package ceshi.handover.scinan.com.huishoubaobigrecycling.bean;

public class YinliaoBean {
    private String name;
    private int score;
    private int num;
    private String unit;
    private int scoreSum;

    public YinliaoBean() {
    }

    public YinliaoBean(String name, int score, int num, String unit, int scoreSum) {
        this.name = name;
        this.score = score;
        this.num = num;
        this.unit = unit;
        this.scoreSum = scoreSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(int scoreSum) {
        this.scoreSum = scoreSum;
    }
}
