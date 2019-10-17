package com.example.androidsample;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class BookDTO {

    // 데이터 복원하는 용도
    private String bimgurl; // 도서 이미지
    private String btitle; // 도서 제목
    private String bauthor; // 도서 저자
    private String bprice; // 도서 가격

    // 안드로이드가 제공하는 Drawble (그림표현)
    // bimgurl를 가지고 도서 이미지에 대한 drawable 객체
    private Drawable drawable;

    public BookDTO() {
    }

    public BookDTO(String bimgurl, String btitle, String bauthor, String bprice) {
        this.bimgurl = bimgurl;
        this.btitle = btitle;
        this.bauthor = bauthor;
        this.bprice = bprice;
    }

    public String getBimgurl() {
        return bimgurl;
    }

    public void setBimgurl(String bimgurl) {
        this.bimgurl = bimgurl;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bimgurl='" + bimgurl + '\'' +
                ", btitle='" + btitle + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", bprice='" + bprice + '\'' +
                '}';
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }

    public void drawableFormURL(){
        Drawable d = null;

        try {
            InputStream is = (InputStream)new URL(bimgurl).getContent();

            // 그림 URL을 통해 drawble 객체를 만들 수 있다.
            d = Drawable.createFromStream(is, "NAME");
            this.drawable = d;

        }catch (Exception e){
            Log.i("DTO_ERROR", e.toString());
        }
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
