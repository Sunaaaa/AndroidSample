package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class FruitSelectActivity extends AppCompatActivity {

    private String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_select);

        // Spinner에 채워질 데이터 Set (ArrayList) 이 필요
        final ArrayList<String> list = new ArrayList<String>();
        list.add("수박");
        list.add("복숭아");
        list.add("토마토");
        list.add("메론");

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        // adapter
        // ApplicationContext, spinner에 데이터를 표현할 떄 어떤 형태로 표현할지, adapter를 만들기 위한 데이터
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // Item을 선택했을 때 호출
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (String)list.get(i);
                Log.i("selectedText", "선택된 과일 : " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // item : spinner 안의 각각의 객체
        // item을 선택하는 이벤트 객체가 필요
        //spinner.setOnItemClickListener();

        Button sendDataBtn = (Button)findViewById(R.id.fruit_btn);
        sendDataBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("fruit", selectedItem);
                setResult(5000, resultIntent);
                FruitSelectActivity.this.finish();
            }
        });


    }
}
