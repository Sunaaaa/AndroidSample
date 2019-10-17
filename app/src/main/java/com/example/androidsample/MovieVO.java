package com.example.androidsample;

public class MovieVO {
    private String rank;
    private String movieNm;
    private String movieCd;
    private String openDt;
    private String audiCnt;
    private String showCnt;
    private String audiAcc;

    public MovieVO() {
    }

    public MovieVO(String rank, String movieNm, String movieCd, String openDt, String audiCnt, String showCnt, String audiAcc) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.movieCd = movieCd;
        this.openDt = openDt;
        this.audiCnt = audiCnt;
        this.showCnt = showCnt;
        this.audiAcc = audiAcc;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getMovieCd() {
        return movieCd;
    }

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getAudiCnt() {
        return audiCnt;
    }

    public void setAudiCnt(String audiCnt) {
        this.audiCnt = audiCnt;
    }

    public String getShowCnt() {
        return showCnt;
    }

    public void setShowCnt(String showCnt) {
        this.showCnt = showCnt;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }
}
