package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MyContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);

        Button contact_btn = (Button) findViewById(R.id.contact_btn);

        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보안 관련 코드가 나와야함
                // 사용자에게 허가를 얻어야 접근 및 사용이 가능함
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 마시멜로우 버전 (6) 보다 높은 경우

                    // 사용자에게 허가를 얻은 적이 있는지
                    if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        // 이전에 허용을 한 적이 없는 경우
                        // 사용자의 허가를 얻어야 한다.
                        // 여러 개의 권한을 한번에 처리할 수 있기 때문에 String[] 의 형태
                        // 주소록의 데이터를 가져오는 permission을 요청하는 알림창이 빵
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1111);

                    } else {
                        // 이미 이전에 허용을 한 적이 있는 경우
                        ContentResolver cr = getContentResolver();

                        // 주소록 접근 URI
                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        Cursor c = cr.query(uri, null, null, null, null);

                        String result = "";

                        // 사람 이름 컬럼
                        String name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
                        // 전화번호 컬럼
                        String pnum = ContactsContract.CommonDataKinds.Phone.NUMBER;

                        // == rs.next()
                        while (c.moveToNext()) {
                            // c.getColumnIndex() : 컬럼 이름을 통해 컬럼 번호를 알아온다.
                            result += c.getString(c.getColumnIndex(name));
                            result += ",";
                            result += c.getString(c.getColumnIndex(pnum));
                            result += "\n";
                        }

                        // 이렇게 데이터를 다 얻어오면, 해당 결과를 TextView에 출력
                        final TextView tv = (TextView) findViewById(R.id.contactTv);
                        tv.setText(result);
                        Log.i("LOG_PHONE", "성공");


                    }

                } else {
                    // 마시멜로우 버전 (6) 보다 낮은 경우
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1111) {

            try {

                // grantResults :  권한 허용 여부가 들어있음
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 만약, 사용자가 주소록 접근에 대한 허용을 누르면
                    // Content Resolver를 이용해서 주소록에 접근
                    ContentResolver cr = getContentResolver();

                    // 주소록 접근 URI
                    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    Cursor c = cr.query(uri, null, null, null, null);

                    String result = "";

                    // 사람 이름 컬럼
                    String name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
                    // 전화번호 컬럼
                    String pnum = ContactsContract.CommonDataKinds.Phone.NUMBER;

                    // == rs.next()
                    while (c.moveToNext()) {
                        // c.getColumnIndex() : 컬럼 이름을 통해 컬럼 번호를 알아온다.
                        result += c.getString(c.getColumnIndex(name));
                        result += ",";
                        result += c.getString(c.getColumnIndex(pnum));;
                        result += "\n";
                    }

                    // 이렇게 데이터를 다 얻어오면, 해당 결과를 TextView에 출력
                    TextView tv = (TextView) findViewById(R.id.contactTv);
                    tv.setText(result);
                    Log.i("LOG_PHONE", "성공");

                }
            } catch (Exception e) {
                Log.i("LOG_PHONE", e.toString());

            }

        }else {
            Toast.makeText(this, "여기로 쨘", Toast.LENGTH_SHORT).show();
        }
    }
}
