package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DatabaseTestActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        Button createDB_btn = (Button)findViewById(R.id.createDB_btn);
        createDB_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 클릭하면 데이터 베이스를 생성하고 테이블을 만들어요
                // SQLite 데이터 베이스를 사용하기 쉽도록 도와주는 Helper Class를 제공
                // Helper Class를 이용해서 데이터 베이스를 사용
                // => Helper Class를 직접 이용하는 것이 아닌 상속받는 클래스를 작성한 후 사용자 정의 클래스의 객체를 이용한다.
                // Helper Class를 작성하러 갑니다.~~~~~~~~~~~~~~

                // context로 지정된 Activity에서 helper를 사용한다.
                MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
                db = helper.getWritableDatabase();

                // helper를 통해서 Database에 대한 handle을 얻어올 수 있어요.
                // 쓰기 혹은 읽기 용도의 데이터 베이스 handle을 갖는다.
            }
        });

        Button selectDB_btn = (Button)findViewById(R.id.selectDB_btn);
        selectDB_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Database Handle을 이용해서 데이터 베이스 처리를 할 수 있어요.

                // rawQuery() : select 계열 (결과 데이터 집합이 있는 쿼리문) 의 SQL문을 실행할 때 사용되요!

                // Cursor: JDBC의 ResultSet과 동일하게 생각하면 된다.
                // : 위-> 아래의 방향으로 데이터를 추출하면 된다.
                Cursor c = db.rawQuery("SELECT * FROM Member", null);
                String result="";

                // == rs.next()
                while (c.moveToNext()){
                    result += c.getString(0);
                    result += ",";
                    result += c.getInt(1);
                    result += "\n";
                }

                // 이렇게 데이터를 다 얻어오면, 해당 결과를 TextView에 출력
                TextView textView = (TextView)findViewById(R.id.selectTv);
                textView.setText(result);
            }
        });
    }
}
