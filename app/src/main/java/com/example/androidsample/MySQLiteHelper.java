package com.example.androidsample;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // 생성자도 다시 작성해야 한다.
    public MySQLiteHelper(Context context) {
        // 상위 클래스의 생성자 호출
        super(context, "Member.db", null, 1);
    }

    // 오버라이드 Method 구현
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 초기 데이터 베이스 설정 코드가 들어가요.
        // 테이블 생성하고 초기 데이터 Insert하는 작업
        // execSQL() : 결과 데이터 집합인 ResultSet을 가져오지 않는 SQL 구문을 실행할 떄
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Member ( userName TEXT, userAge INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO Member VALUES ('홍길동', 30);");
        sqLiteDatabase.execSQL("INSERT INTO Member VALUES ('박길동', 10);");
        sqLiteDatabase.execSQL("INSERT INTO Member VALUES ('공길동', 50);");
        Log.i("LOG_DATABASE", "Helper의 onCreate() 호출!!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
