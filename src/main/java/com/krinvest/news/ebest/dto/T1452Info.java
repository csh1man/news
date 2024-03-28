package com.krinvest.news.ebest.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class T1452Info {
    @SerializedName("hname")
    private String name;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("change")
    private BigDecimal change;

    @SerializedName("diff")
    private BigDecimal diff;

    @SerializedName("volume")
    private BigDecimal volume;

    @SerializedName("jnilvolume")
    private BigDecimal  beforeVolume;

    public T1452Info(JsonObject json){
        this.name = json.get("hname").getAsString();
        this.price = json.get("price").getAsBigDecimal();
        this.change = json.get("change").getAsBigDecimal();
        this.diff = json.get("diff").getAsBigDecimal();
        this.volume = json.get("volume").getAsBigDecimal();
        this.beforeVolume = json.get("jnilvolume").getAsBigDecimal();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getBeforeVolume() {
        return beforeVolume;
    }

    public void setBeforeVolume(BigDecimal beforeVolume) {
        this.beforeVolume = beforeVolume;
    }
}
