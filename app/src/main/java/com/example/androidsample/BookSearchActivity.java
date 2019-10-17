package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class BookSearchActivity extends AppCompatActivity {
    class SearchTitleRunnable implements Runnable{

        private String keyword;
        private Handler handler;

        public SearchTitleRunnable(Handler handler, String keyword) {
            this.handler = handler;
            this.keyword = keyword;
        }

        @Override
        public void run() {
            // keyword를 이용해서 web program에 접속한 후 결과를 받아와요!
            // 결과로 받아온 JSON 문자열을 이용해서 Listview에 출력해야 해요!
            // 그런데 여기서는 ListView를 제어할 수 없어요
            // Handler를 이용해서 UI Thread에 ListView에 사용할 데이터를 넘겨줌
            String url = "http://70.12.115.72:9990/bookSearch/searchTitle?USER_KEYWORD=" + keyword;

            // NetWork는 exception이 발생할 여지가 있기 때문에 tyr-catch로 예외처리를 해야 한다. (없으면 컴파일 에러 발생)
            try{
                // url을 URL 객체로 만들어 실제 url의 주소로 접속할 수 있다. (method 사용 가능)
                URL urlObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection)urlObject.openConnection();

                // network 연결이 성공한 후 데이터를 읽어들이기 위한 데이터 연결 통로
                // Stream 을 생성@ ==> BufferedReader
                // con.getInputStream() : Stream을 뽑아냄
                // new InputStreamReader(con.getInputStream()) : 조금 더 나은 Stream
                // new BufferedReader(new InputStreamReader(con.getInputStream())) : 조금 더더 나은 Stream

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input = "";
                StringBuffer sb = new StringBuffer();

                // input=br.readLine() : 서버가 보내주는 데이터를 다 읽음
                while ((input=br.readLine())!= null){
                    sb.append(input);
                }
                Log.i("DATA", sb.toString());
                // 얻어온 결과 JSON 문자열을 Jackson Library를 이용해서 Java 객체 형태 (String[])로 변형

                // Jackson Library를 이용하여 JSON을 원래 형태 (String[])로 변환
                ObjectMapper mapper = new ObjectMapper();

                // sb를 읽어서 Stringp[] 의 형태로 변형되어 resultArr로 떨어짐
                String[] resultArr = mapper.readValue(sb.toString(), String[].class);

                // Handler을 통해 String[] 을 쏴줌
                Bundle bundle = new Bundle();
                // Key - Value 쌍으로 바구니에 담는다
                bundle.putStringArray("BOOK_ARRAY", resultArr);

                Message msg = new Message();
                msg.setData(bundle);

                // Activity의 Handler가 받음
                handler.sendMessage(msg);



            } catch (Exception e){
                Log.i("DATA_ERROR", e.toString());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        final Button search_btn = (Button)findViewById(R.id.bsearch_btn);
        final EditText search_et = (EditText)findViewById(R.id.bsearchET);

        final ListView listView = (ListView)findViewById(R.id.result_list);


        // 오버라이딩 하면서 handler 생성
        final Handler handler = new Handler(){

            // handler에게 message가 전달되면 아래의 method가 callback된다.

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String[] result = bundle.getStringArray("BOOK_ARRAY");

                // adapter 객체는 데이터를 가져다가 실제 View에 그리는 역할을 담당
                // 배열의 데이터를 가지고 spinner, ListView 등에 그리기 떄문에
                // List View, spinner 사용시에는 반드시 필요하다.
                // ArrayAdapter : 데이터를 배열에서 가져온다.
                ArrayAdapter adapter = new ArrayAdapter
                        (BookSearchActivity.this, android.R.layout.simple_list_item_1, result);
                listView.setAdapter(adapter);
            }
        };

        // 웹 서버에 접속해서 데이터를 받아온 후 해당 데이터를 ListView에 세팅
        // UI Thread (Activity Thread, Main Thread)에서는 Network 연결을 할 수 없다.
        // -> 최우선 순위 작업은 사용자와의 Interaction 이기 때문에

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 keyword를 가지고 Thread를 파생
                // search_et.getText() : Editable이라는 Interface로 떨어짐
                SearchTitleRunnable runnable = new SearchTitleRunnable
                        (handler, search_et.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });

    }
}
