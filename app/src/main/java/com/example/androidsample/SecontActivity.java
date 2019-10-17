package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecontActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secont);

        Intent i = getIntent();

        // 전달되는 데이터가 어떤 타입인지 알 수 없기 떄문에 Object Type으로 get 하기 떄문에 원하는 데이터 타입으로 다운 캐스팅 한다.
        String msg = (String)i.getExtras().get("sendMsg");

        TextView tv = (TextView)findViewById(R.id.messageTv);
        tv.setText(msg);

        Button closebtn = (Button)findViewById(R.id.close_btn);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Activity
                SecontActivity.this.finish();
            }
        });
    }
}
