package com.example.androidsample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase db;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        // Content Provider가 만들어지면 helper를 생성함

        // 데이터 베이스를 사용하기 위해 helper를 사용
        // 데이터 베이스를 조작할 수 있는 helper
        MySQLiteHelper helper = new MySQLiteHelper(getContext());
        db = helper.getWritableDatabase();
        Log.i("LOG_DATABASE", "Content Provider의 onCreate() 호출");
        return true;
    }

    @Override
    // 어떤 컬럼을 들고 갈 것인지 , where 절, where 절의 인자 , order by
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        // select 계열의 SQL문이 실행되고 그 결과 Cursor를 다른 Application에 제공
        Log.i("LOG_DATABASE","query()가 호출되었어요~");

        // 인자로 들어오는 값들을 이용해서 사용할 SQL문을 구성해야 한다.
        String sql = "SELECT * FROM Member";
        // 데이터 베이스를 뒤져서 나온 결과 데이터를 핸들인 Cursor 객체를 return 한다.
        return db.rawQuery(sql, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
