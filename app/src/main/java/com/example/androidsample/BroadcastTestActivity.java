package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// 묵시적으로 호출되기 떄문에 manifest에 action과 category를 등록해야 한다.
public class BroadcastTestActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        Button register_btn = (Button)findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Broadcast Receiver
                // 먼저 Broadcast Receiver가 어떤 Broadcast를 청취할 수 있는지를 나타내는
                // Intent Filter를 하나 만들어 준다. <intent-filter>와 같은 기능
                IntentFilter filter = new IntentFilter();

                // 안드로이드 시스템에서 나오는 여러가지 정해져 있는 Broadcast를 catch 할 수 있어요.
                filter.addAction("MY_BROADCAST_DYNAMIC");


                // 익명 클래스 방식으로 직접 오버라이딩을 하면서 inner Class로 객체를 만들어야 한다.
                // 안드로이드의 3번 쨰 Component인  Broadcast Receiver
                // Broadcast 신호를 잡는다.
                receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        //  Broadcast 를 잡았을 때 처리해야할 코드 작성
                        Toast.makeText(context, "등록된 Broadcast Receiver 사용중~", Toast.LENGTH_SHORT).show();

                        // Notification : Status Bar에 Notofocation을 빵하고 올린다.
                        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                        // channelID와 channelName, notification 중요도 설정
                        String channelID = "MY_CAHNNEL_ID";
                        String channelName = "MY_CAHNNEL_NAME";

                        // 중요도가 낮으면 상태 창에만 빵
                        // 중요도가 높으면 팝업 창에도 빵
                        int important = NotificationManager.IMPORTANCE_HIGH;

                        // Oreo 버전 이상에서는 channel 객체를 생성해서 설정해야 한다.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel = new NotificationChannel(channelID, channelName, important);
                            manager.createNotificationChannel(channel);
                        }

                        // Notification을 생성
                        // builder : Notofocation을 만들기 위한 객체
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelID);

                        // Intent 를 생성
                        // => Notofocation을 클릭했을 때 화면에 Activity를 보여주기 위한 용도
                                                            //  현재 화면을 보이게 함
                                                            // 통지를 누르면 현재 보고 있는 Activity를 보이게 함
                        Intent nIntent = new Intent(context,BroadcastTestActivity.class);
                        // 새로운 Activity를 띄우지 않고 onNewIntent()를 호출하기 위해서
                        // 기존에 떠있는 Activity를 이용
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        int requestID = 10000;
                        // PendingIntent는 Intent를 가지고 있는 Intent
                        // Intent의 수행을 지연시키는 역할을 수행
                        // 새로운 Activity를 띄우기 위한 용도
                        PendingIntent pIntent = PendingIntent.getActivity(context,requestID,nIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                        // +++++++ 여기까지 설정 완료 =======

                        // --- Notification  설정
                        builder.setContentTitle("Notification 제목")
                                .setContentText("여기는 Notification의 실제 내용")
                                .setAutoCancel(true) // Notification을 터치했을 때 사라지도록
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // 기본 알람소리로 Notification이 오면 소리를 발생한다.
                                .setSmallIcon(android.R.drawable.btn_star) // 별 모양의 아이콘 표시
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.now)) // 비트맵 형태의 큰 아이콘
                                .setContentIntent(pIntent);

                        // Notification을 만듬
                        // 위에서 만든 Notification을 Notification Manager을 통해서 실제 Notification 실행
                        manager.notify(0, builder.build());


                    }
                };

                //  Broadcast Receiver객체를 만든 이후, Activity에 등록
                //  Broadcast Receiver가 등록이 되어야지만  Broadcast (신호)를 잡을 수 있다.
                // receiver가 사용할 Intent Filter인 filter
                registerReceiver(receiver, filter);
                Log.i("BROADCAST_DYNAMIC", "BroadCast Resceiver 등록 ");

            }
        });

        Button unRegister_btn = (Button)findViewById(R.id.unRegister_btn);
        unRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되면 receiver의 등록을 해제 해줘요
                // 현재 등록이 되어 있는지를 확인한 후 등록 되어 있는 경우만 해제
                if (receiver != null) {
                    unregisterReceiver(receiver);
                    Log.i("BROADCAST_DYNAMIC", "BroadCast Receiver 해제");
                }
            }
        });

        Button sendSignal_btn = (Button)findViewById(R.id.sendSignal_btn);
        sendSignal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되면 Broadcast (신호)가 발생
                // 신호를 발생시키기 위해서 Intent 가 필요 - Action을 설정한다 .
                Intent i = new Intent();
                // MY_BROADCAST_DYNAMIC 의 액션을 잡기
                i.setAction("MY_BROADCAST_DYNAMIC");
                Log.i("BROADCAST_DYNAMIC", "BroadCast 발생");
                sendBroadcast(i);
            }
        });
    }
}
