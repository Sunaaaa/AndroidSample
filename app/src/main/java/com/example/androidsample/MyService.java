package com.example.androidsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 3가지 callBack Method 사용

    @Override
    public void onCreate() {
        super.onCreate();
        // onCreate() 안에 onStartCommand() method를 자동 호출
        // 서비스에서 사용할 리소스 준비 작업 (파일의 reference 얻기 등등 )
        Log.i("ServiceExam", "onCreate() 호출");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 하는 로직처리
        Log.i("ServiceExam", "onStartCommand() 호출");

        // 강제적으로 재시작이 되었다.
        if (intent == null){
            // 안드로이드 시스템이 서비스만 재 시작해서 Intent 가 null 이다.
            // 로직 처리 - ACtivity에게 줘야 하는 결과 데이터
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            resultIntent.putExtra("ServiceToActivityData","새로운 액티비티가 생성이 됬어요.");

            // onCreate() 가 호출되지 않음
            startActivity(resultIntent);

        }else {
            String message = intent.getExtras().getString("ActivityToServiceData");
            Log.i("ServiceExam",message);
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            resultIntent.putExtra("ServiceToActivityData","새로운 액티비티가 생성이 됬어요.");

            // onCreate() 가 호출되지 않음
            startActivity(resultIntent);
        }

//        return super.onStartCommand(intent, flags, startId);
        // 강제 종료 되면 자동으로 재시작
        return Service.START_STICKY;
        // 강제 종료 되도 강제 재시작하지 않음
//        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // 리소스 해제 작업
        super.onDestroy();
        Log.i("ServiceExam", "onDestroy() 호출");
    }
}
