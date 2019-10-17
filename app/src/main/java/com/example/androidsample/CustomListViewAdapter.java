package com.example.androidsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// ListView 안의 내용을 그리는 역할을 하는 adapter
public class CustomListViewAdapter extends BaseAdapter {

    private List<BookDTO> list = new ArrayList<BookDTO>();

    public void addItem(BookDTO dto) {

        list.add(dto);
    }

    @Override
    public int getCount() {
        // 총 몇개의 Component를 그려야 하는지를 return
        // list의 개수만큼
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        // 몇번 째 데이터를 화면에 출력할지 결정
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    // i : 각각의 List 들
    public View getView(int i, View view, ViewGroup viewGroup) {

        Bitmap bitmap;
        final Context context = viewGroup.getContext();

        // 출력할 View 객체를 생성
        if (view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 제작한 Custom 형태의 xml의 형태로 view 객체를 구성하게 된다.
            // 생성한 View 객체에 XML Layout을 설정
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        // 출력한 View Component Reference 획득
        ImageView imageView = (ImageView)view.findViewById(R.id.custom_img);
        TextView tv1 = (TextView)view.findViewById(R.id.custom_tv1);
        TextView tv2 = (TextView)view.findViewById(R.id.custom_tv2);

        // 화면에 출력할 데이터를 가져와요
        BookDTO dto = list.get(i);
        Log.i("DTO", dto.toString());

        imageView.setImageDrawable(dto.getDrawable());
        tv1.setText(dto.getBtitle());
        tv2.setText(dto.getBauthor());

        return view;
    }


}
