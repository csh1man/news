package com.krinvest.news.ebest.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class T1441Info {
    @SerializedName("hname")
    private String name;
    @SerializedName("price")
    private BigDecimal closePrice;
    @SerializedName("change")
    private BigDecimal change;
    @SerializedName("diff")
    private BigDecimal diff;
    @SerializedName("volume")
    private BigDecimal volume;
    @SerializedName("open")
    private BigDecimal openPrice;
    @SerializedName("high")
    private BigDecimal highPrice;
    @SerializedName("low")
    private BigDecimal lowPrice;

    public T1441Info(JsonObject json){
        this.name = json.get("hname").getAsString();
        this.closePrice = json.get("price").getAsBigDecimal();
        this.change = json.get("change").getAsBigDecimal();
        this.diff = json.get("diff").getAsBigDecimal();
        this.volume = json.get("volume").getAsBigDecimal();
        this.openPrice = json.get("open").getAsBigDecimal();
        this.highPrice = json.get("high").getAsBigDecimal();
        this.lowPrice = json.get("low").getAsBigDecimal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getDiff() {
        return diff;
    }

    public void setDiff(BigDecimal diff) {
        this.diff = diff;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String toString(){
        return "회사명 : [" + this.name + "] 등락률 : [" + this.diff + "%] 거래량 : [" + this.volume + "]";
    }
}
