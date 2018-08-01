package com.kingyee.common.model;

/**
 * 交易记录Model
 * @author hanyajiao
 */
public class BillRecordList {

    private String  title;
    private Double money;
    private String timeStr;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
