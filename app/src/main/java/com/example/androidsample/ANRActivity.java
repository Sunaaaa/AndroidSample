package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


class MySumThread implements Runnable{
    private TextView tv;

    // Injection : 객체, 클래스 간의 연결 구조와 상관없이 프로그램을 유연하게 끌고 갈 수 있다.
    // Runnable 을 구현한 객체에 TextView를 넣어서, 해당 TextView를 사용할 수 있도록 한다.
    public MySumThread(TextView tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        // Loop를 돌면서 누적합을 TextView (countTv)에  출력함
        long sum = 0;
        for (long i = 0; i<1000000000L; i++){
            sum += i;
        }
        tv.setText("총합은 " + sum);

    }
}

public class ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        final TextView countTv = (TextView)findViewById(R.id.countTv);
        final Button count_btn = (Button)findViewById(R.id.count_btn);
        final Button toast_btn = (Button)findViewById(R.id.toast_btn);

        count_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Thread를 파생시켜야 한다.

                // Runnable 인터페이스를 구현한 객체
                MySumThread mySum = new MySumThread(countTv);

                // Runnable 객체를 이용하여 Thread 생성
                Thread t = new Thread(mySum);

                // non-blocking method : method의 수행과 상관없이 다음으로 진행됨
                // start() 의 수행이 다 끝나지 않아도 다음 코드가 실행된다.
                // 새로운 실행흐름을 만들어 낼 수 있다. --> 마치 동시에 실행되는 것처럼
                t.start();
            }
        });

        toast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ANRActivity.this,"Toast가 실행되요~", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
