package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class KAKAOMAPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaomap);

        MapView map = new MapView(this);

        // Linearlayout // 눈에 보이는 Component를 담는 객체
        ViewGroup group = (ViewGroup)findViewById(R.id.map_LiLayout);

        // 지도가 어느 위치/지점을 가리키고 있을 것인지        (위도, 경도)
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.502584,127.041230);
        map.setMapCenterPoint(mapPoint, true);
        group.addView(map);

    }
}
