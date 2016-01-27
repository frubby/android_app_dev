package com.frw.douban.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by fruwe on 2016/1/25.
 */
public class ActorBean {
    public JSONObject getWorks() {
        return works;
    }

    public void setWorks(JSONObject works) {
        this.works = works;
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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImgL() {
        return imgL;
    }

    public void setImgL(String imgL) {
        this.imgL = imgL;
    }

    public String getImgS() {
        return imgS;
    }

    public void setImgS(String imgS) {
        this.imgS = imgS;
    }

    public String getImgM() {
        return imgM;
    }

    public void setImgM(String imgM) {
        this.imgM = imgM;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBornPlace() {
        return bornPlace;
    }

    public void setBornPlace(String bornPlace) {
        this.bornPlace = bornPlace;
    }

    int id;
    String name;
    String name_en;
    String alt;
    String imgL;
    String imgS;
    String imgM;
    boolean sex;
    String bornPlace;
    JSONObject works;

}
