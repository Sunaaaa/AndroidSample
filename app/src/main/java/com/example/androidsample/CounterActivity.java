package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

class MyCounter2 implements Runnable{

    private Handler handler;

    public MyCounter2(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        // 1초마다 카운트를 증가시켜서 TextView에 출력
        for (int i = 0; i<10; i++){
            try{
                Thread.sleep(1000);
                // 메시지 보내기
                // Bundle (데이터 묶음, 데이터를 묶을 수 있는 단위)
                // Key- Value의 쌍으로 데이터 저장
                Bundle bundle = new Bundle();
                // i+"" : 문자열로
                bundle.putString("COUNT", i  + "");
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg); // -> UI Thread의 handler에게 msg와 함께 전달



                // UI Thread 에 handler를 이용해서 메시지를 전달
            }catch (Exception e){

            }
        }

    }
}
public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        final TextView tv = (TextView)findViewById(R.id.counterTv2);
        Button start_btn2 = (Button)findViewById(R.id.start_btn2);

        // MyCounter2의 메시지를 받음
        final Handler handler = new Handler(){
            // handler를 통해 message를 받는 순간 handleMessage() 메소드가 호출
            // Activity와 MyCounter2 사이의 message를 주고 받을 수 있도록 한다.
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                tv.setText(b.getString("COUNT"));
            }
        };

        start_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCounter2 mc = new MyCounter2(handler);
                Thread t = new Thread(mc);
                t.start();
         }
        });
    }
}
