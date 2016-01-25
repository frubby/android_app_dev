package com.frw.douban.bean;

/**
 * Created by fruwe on 2016/1/25.
 */
public class MovieBean {
    int id;
    String title;
    String original_title;
    byte rating_max;
    byte rating_min;
    byte rating_stars;
    float rating_average;
    String[] genres;
    int collect_count;
    ActorBean[] directors;
    int year;


}
