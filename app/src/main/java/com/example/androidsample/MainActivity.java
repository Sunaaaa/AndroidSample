package com.example.androidsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btn1 : Event Source
        Button linear_btn = (Button)findViewById(R.id.linear_btn);

        Button chatAc_btn = (Button)findViewById(R.id.chatAc_btn);

        Button changeIm_btn = (Button)findViewById(R.id.changeIm_btn);

        Button toast_btn = (Button)findViewById(R.id.toast_btn);

        Button swipe_btn = (Button)findViewById(R.id.swipe_btn);

        Button fruitAc_btn = (Button)findViewById(R.id.fruitAc_btn);

        Button anr_btn = (Button)findViewById(R.id.anr_btn);

        Button error_btn = (Button)findViewById(R.id.error_btn);

        Button counter_btn = (Button)findViewById(R.id.counter_btn);

        Button book_btn = (Button)findViewById(R.id.book_btn);

        Button movie_btn = (Button)findViewById(R.id.movie_btn);

        Button custom_btn = (Button)findViewById(R.id.custom_btn);

        Button implicit_btn = (Button)findViewById(R.id.implicit_btn);

        Button service_start_btn = (Button)findViewById(R.id.serviceStartBtn);

        Button service_stop_btn = (Button)findViewById(R.id.serviceStopBtn);

        Button broad_btn = (Button)findViewById(R.id.broad_btn);

        Button broadCastbtn = (Button)findViewById(R.id.broadCastbtn);

        Button sqlite_btn = (Button)findViewById(R.id.sqlite_btn);

        Button phone_btn = (Button)findViewById(R.id.phone_btn);
        // Anonymouse Class : 전체 클래스 내부에 하나의 클래스가 익명으로 들어가 있음
        linear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.LinearLayoutExampleActivity");
                i.setComponent(cname);
                startActivity(i);

            }
        });

        chatAc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.ChattingActivity");
                i.setComponent(cname);
                startActivity(i);

            }
        });

        changeIm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.ChangeImageActivity");
                i.setComponent(cname);
                startActivity(i);

            }
        });

        toast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.TouchEventAndToastActivity");
                i.setComponent(cname);
                startActivity(i);

            }
        });

        swipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ACtivity 객체는 context 를 상속받기 떄문에 is-a 관계 --> Activity를 넣어도 됨

                // View를 만들 때는 반드시 Activity 객체가 필요하다.
                // this : 실제 Listener를 지칭하고 있기 떄문에 context를 뜻하지 않는다.
                // --> Anonymouse 익명 클래스에서의 context를 가져오기 위해서는 클래스 이름.this (해당 클래스 객체를 가져온다)로 가져온다.

                // Anonymouse : 클래스 선언하면서 인스턴스를 바로 올림
                final EditText et = new EditText(MainActivity.this);

                // 경고창 용도로 사용되는 AlertDialog 생성
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // Dialog에 title과 message 설정
                dialog.setTitle("Activity에 데이터 전달");
                dialog.setMessage("전달할 내용을 입력하세요.");

                // Dialog 에 입력상자 (EditText)
                dialog.setView(et);

                // Dialog의 취소/확인 버튼에 이벤트 적용
                // positive 버튼 : Dialog 인터페이스가 가지는 onClickListener의 인스턴스를 가짐 -> Dialog의 onClick() 메소드를 오버 라이드
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 다른 Activity를 호출하는 코드
                        Intent intent = new Intent();
                        ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.SecontActivity");
                        intent.setComponent(cname);
                        intent.putExtra("sendMsg",et.getText().toString());
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        fruitAc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.FruitSelectActivity");
                i.setComponent(cname);

                // 결과를 가져옴
                startActivityForResult(i, 3000);
            }
        });

        anr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.ANRActivity");
                i.setComponent(cname);
                startActivity(i);
             }
        });

        error_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.CounterErrorInActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        counter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.CounterActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });


        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.BookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });


        movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.MovieChartActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        custom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.CustomBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        implicit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.IntentTestActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        service_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서비스 객체를 실행
                // 서비스 실행할 때도 Intent 사용
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.MyService");
                i.setComponent(cname);
                i.putExtra("ActivityToServiceData", "소리없는 아우성");
                startService(i);
            }
        });

        service_stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 생성된 서비스는 Activity를 통해 종료해야한다.
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.MyService");
                i.setComponent(cname);
                stopService(i);
            }
        });


        broad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                i.setAction("MY_BROADCAST");
                i.putExtra("BroadCastMsg", "브로트케스트는 메세지가 전달되요~");
                sendBroadcast(i);
            }
        });


        broadCastbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                i.setAction("START_BROADCAST_ACTIVITY");
                startActivity(i);
            }
        });


        sqlite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                i.setAction("START_DATABASE_ACTIVITY");
                startActivity(i);
            }
        });

        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 Activity 호출
                Intent i = new Intent();
                i.setAction("CONTACT_ACTIVITY");
                startActivity(i);
            }
        });
        /*

* 새로운 클래스 만들어서 이벤트 처리
        //Listener 객체 생성
        MyClickListener listener = new MyClickListener();
        // Event Source에 Listener 객체를 부착
        btn1.setOnClickListener(listener);
*/
    }


//    class MyClickListener implements View.OnClickListener {
//
//        // 버튼이 눌렸을 때 호출되요
//        // 새로운 Activity 를 start 시켜요
//        // Activity를 호출하기 위해 2가지 방식
//        // 1. Intent
//        @Override
//        public void onClick(View view) {
//            Intent i = new Intent();
//            ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.LinearLayoutExampleActivity");
//
//            // 어떤 Activity를 사용할지에 대한 정보를 Intent가 가짐
//            i.setComponent(cname);
//
//            // 해당 Intent가 가진 Class 정보를 가지고 Activity가 실행
//            startActivity(i);   // Activity Class가 가지고 있는 method
//
//        }
//    }


    // 서비스 에서 보낸 Intent 를 여기서 받음
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String msg = intent.getExtras().getString("ServiceToActivityData");
        Log.i("ServiceExam", msg);
        Toast.makeText(this, "asjdhfsjkd", Toast.LENGTH_SHORT).show();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // requearCode와 resultCode는 어떤 값을 주어도 무관하다.
    // 그러나 A 액티비티를 5000으로 설정하고, A를 부르고자 할 때는 5000을 명시해야 한다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000 && resultCode == 5000){
            String result = data.getExtras().getString("fruit");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

}
