package com.example.yasksrivastava.popularmovies;


public class PopularMovie {
    String title;
    String imgurl;
    String releaseDate;
    double vote_avg;
    String overview;

    public PopularMovie(String title,String imgurl, String releaseDate,double vote_avg,String overview){
        this.title = title;
        this.imgurl = imgurl;
        this.releaseDate = releaseDate;
        this.vote_avg = vote_avg;
        this.overview = overview;
    }
}