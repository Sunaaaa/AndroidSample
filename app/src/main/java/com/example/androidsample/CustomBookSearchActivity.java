package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class BookSearchRunnable implements Runnable{

    private String keyword;
    private Handler handler;

    public BookSearchRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://70.12.115.72:80/bookSearch/BookSearchServlet?SERACH_KEYWORD=" + keyword;

        try{
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObject.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = "";
            StringBuffer sb = new StringBuffer();

            while ((input=br.readLine())!= null){
                sb.append(input);
            }
            Log.i("C_BOOK_1", sb.toString());

            ObjectMapper mapper = new ObjectMapper();

            BookDTO[] resultArr = mapper.readValue(sb.toString(), BookDTO[].class);

            for (BookDTO dto : resultArr){
                Log.i("C_BOOK_2", dto.getBtitle());
            }

            // UI Thread 에게 데이터를 전달하기 전에 화면에 표현할 데이터가 완전히 준비되어야 해요!

            for (BookDTO dto : resultArr){
                dto.drawableFormURL();
            }

            // 최종적으로 얻어낸 객체를 UI Thread(Main Thread/ Activity Thread) 에게 전달해야 한다.
            Bundle bundle = new Bundle();
            bundle.putSerializable("BOOKS", resultArr);

            Message message = new Message();
            message.setData(bundle);

            handler.sendMessage(message);

        } catch (Exception e){
            Log.i("C_BOOK_ERROR", e.toString());
        }

    }
}
public class CustomBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_book_search);

        final Button search_btn = (Button)findViewById(R.id.custom_btn);
        final EditText search_et = (EditText)findViewById(R.id.custom_edittext);
        final ListView listView = (ListView)findViewById(R.id.custom_listview);

        // 오버라이딩 하면서 handler 생성
        final Handler handler = new Handler(){

            // handler에게 message가 전달되면 아래의 method가 callback된다.

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                BookDTO[] list = (BookDTO[])bundle.getSerializable("BOOKS");

                // ListView에 Adapter를 적용해서 ListView에 뿌려요
                // ListView : 화면에 List 형태를 보여주는 위젯
                // ListView를 가져다가 실제로 그려주는 작업은 Adapter가 합니다.

                CustomListViewAdapter adapter = new CustomListViewAdapter();
                // Adapter에 그려야 하는 데이터를 Setting
                for (BookDTO dto : list){
                    adapter.addItem(dto);
                }

                listView.setAdapter(adapter);
            }
        };

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookSearchRunnable runnable = new BookSearchRunnable(handler, search_et.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });


    }
}
