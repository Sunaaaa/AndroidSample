package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

class MyCounter implements Runnable{
    private TextView textView;

    public MyCounter(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void run() {
        // 1초마다 카운트를 증가시켜서 TextView에 출력
        for (int i = 0; i<10; i++){
            try{
                Thread.sleep(1000);

                // 메시지를
            }catch (Exception e){

            }
        }

    }
}
public class CounterErrorInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_error_in);

        final TextView tv = (TextView)findViewById(R.id.counterTv);
        Button start_btn = (Button)findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCounter mc = new MyCounter(tv);
                Thread t = new Thread(mc);
                t.start();
                // 스레드를 생성해서 1초마다 TextView에 Count를 출력
                // Runnable 인스턴스를 뽑아서 Thread 생성
 /*               Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 1초마다 카운트를 증가시켜서 TextView에 출력
                        for (int i = 0; i<10; i++){
                            try{
                                Thread.sleep(1000);
                                tv.setText("count : " + i);
                            }catch (Exception e){

                            }
                        }
                    }
                });
                t.start();
   */         }
        });
    }
}
