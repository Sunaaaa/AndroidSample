package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        Button imp_btn = (Button) findViewById(R.id.imp_btn);
        imp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Button이 클릭되면, 새로운 Activity를 호출한다.
                // Implicit Intent를 이용해서 Activity를 호출한다.
                Intent i = new Intent();

                // 이미 정해져 있는 Action이 있다. - 웹 브라우저를 띄위기, 전화 걸기 등등
                // 내가 정의한 Action도 사용가능
                // 하나만 가능
                i.setAction("MY_ACTION");

                // 이미 정해져 있다. 내가 정의한 Category도 사용가능
                // 여러개가 달릴 수 있다.
                i.addCategory("MY_CATEGORY");

                // 추가적인 데이터 넣을 때 사용 - (기본 데이터 넣기 : i.setData())
                i.putExtra("DATA", "소리 없는 아우성");
                startActivity(i);

            }
        });

        Button dial_btn = (Button) findViewById(R.id.dial_btn);
        dial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();

                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:01012345678"));
                startActivity(i);

            }
        });

        Button browser_btn = (Button) findViewById(R.id.browser_btn);
        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Browser를 이용해서 특정 URL로 접속
                // 네트워크 접속해야 하기 때문에 보안 해제
                // -> AndroirManifest에 <uses-permission android:name="android.permission.INTERNET" /> 추가
                Uri uri = Uri.parse("http://www.naver.com");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);

            }
        });

        Button map_btn = (Button) findViewById(R.id.map_btn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();

                ComponentName cname = new ComponentName("com.example.androidsample", "com.example.androidsample.KAKAOMAPActivity");
                i.setComponent(cname);
                startActivity(i);

            }
        });


        // 누르면 바로 전화 걸기
        Button call_btn = (Button) findViewById(R.id.call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 사용자의 안드로이드 버전이 6버전 보다 작은가?
                // 마시멜로 버전 (6버전) 이후부터 보안이 강화 됨
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 추가적인 보안 해제가 필요
                    // 현재 앱에 대해 사용자 권한 중 전화걸기 기능이 설정되어 있는가?
                    int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (result == PackageManager.PERMISSION_DENIED){
                        // 전화 걸기 기능에 대한 보안이 설정 안되어 있어요
                        // 한번 이라도 전화걸기에 대한 권한 설정을 거부한 적이 있는가?
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                            // 권한 설정을 거부한 적이 있어요
                            // -> Dialog를 띄어 권한 설정 거부 해제 여부를 다시 묻는다.
                            AlertDialog.Builder builder = new AlertDialog.Builder(IntentTestActivity.this);
                            builder.setTitle("권한 필요!");
                            builder.setMessage("전화 걸기 기능이 필요해요. 수락할까요?");
                            builder.setPositiveButton("수락", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                }
                            });

                            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            // 만든 Dialog를 보이게 한다.
                            builder.show();
                        }else {
                            // 이전에 권한 설정 거부를 한 적이 없다.
                            // -> permission을 요청한다. --- 전화 걸기, 허용할 꺼니? '네' -- 보안 해제
                            // -> permission 요구 Dialog를 띄운다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);


                        }

                    }else {
                        // 전화 걸기 기능에 대한 보안이 설정되어 있어요
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:010-1234-5678"));
                        startActivity(i);
                    }

                } else {
                    // 이전 안드로이드 버전이기 때문에 manifest에 간단한 설정으로 바로 실행
                    //  직접 calling하는 Activity 호출
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:010-1234-5678"));
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //grantResults : 사용자가 해당 권한에 대해 허가를 했는지 아닌지에 대한 정보가 들어가 있다.

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:010-1234-5678"));
                startActivity(i);
            }
        }
    }
}
