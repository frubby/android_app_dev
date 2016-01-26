package com.frw.douban.bean;

import java.io.Serializable;

/**
 * Created by fruwe on 2016/1/25.
 */
public class MovieBean  implements Serializable{
    int id;
    String title;
    String original_title;
    byte rating_max;
    byte rating_min;
    byte rating_stars;
    float rating_average;
    String[] genres;
    ActorBean[] directors;
    int year;
    String images_small;
    String images_large;
    String images_medium;
    String alt;
    int reviews_count;
    int wish_count;
    int collect_count;
    int seasons_count;
    int do_count;
    int current_season;
    String[] countries;

    ActorBean[] casts;
    String summary;
    String subtype;

    int comments_count;
    int ratings_count;
    String[] aka;
    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public int getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(int seasons_count) {
        this.seasons_count = seasons_count;
    }

    public int getDo_count() {
        return do_count;
    }

    public void setDo_count(int do_count) {
        this.do_count = do_count;
    }

    public int getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(int current_season) {
        this.current_season = current_season;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public ActorBean[] getCasts() {
        return casts;
    }

    public void setCasts(ActorBean[] casts) {
        this.casts = casts;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public String[] getAka() {
        return aka;
    }

    public void setAka(String[] aka) {
        this.aka = aka;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public byte getRating_max() {
        return rating_max;
    }

    public void setRating_max(byte rating_max) {
        this.rating_max = rating_max;
    }

    public byte getRating_min() {
        return rating_min;
    }

    public void setRating_min(byte rating_min) {
        this.rating_min = rating_min;
    }

    public byte getRating_stars() {
        return rating_stars;
    }

    public void setRating_stars(byte rating_stars) {
        this.rating_stars = rating_stars;
    }

    public float getRating_average() {
        return rating_average;
    }

    public void setRating_average(float rating_average) {
        this.rating_average = rating_average;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public ActorBean[] getDirectors() {
        return directors;
    }

    public void setDirectors(ActorBean[] directors) {
        this.directors = directors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    public String getImages_small() {
        return images_small;
    }

    public void setImages_small(String images_small) {
        this.images_small = images_small;
    }

    public String getImages_large() {
        return images_large;
    }

    public void setImages_large(String images_large) {
        this.images_large = images_large;
    }

    public String getImages_medium() {
        return images_medium;
    }

    public void setImages_medium(String images_medium) {
        this.images_medium = images_medium;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }


}
