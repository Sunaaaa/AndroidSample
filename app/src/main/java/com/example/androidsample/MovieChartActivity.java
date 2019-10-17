package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
class MakeSendingDate{
    private int year;
    private int month;
    private int day;
    private String strMonth;
    private String strDay;

    private String sendData;

    public MakeSendingDate(){

    }

    public void setDate(int year,int month , int day){
        this.day = day;
        this.month = month;
        this.year = year;

        if((month / 10) < 1){
            strMonth = "0" +String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        if((day / 10) < 1){
            strDay = "0" +String.valueOf(day);
        } else {
            strDay = String.valueOf(day);
        }
    }
    public String getSendData(){
        sendData = String.valueOf(year) +strMonth+strDay;
        return sendData;
    }

}

class SearchMovieRunnable implements Runnable{
    private String keyword;
    private Handler handler;

    public SearchMovieRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=fab6fd6434401fd4302d3ebdab8fef88&targetDt=" + keyword;

        try{
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObject.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = "";
            StringBuffer sb = new StringBuffer();

            String result = "";
            while ((input=br.readLine())!= null){
                result +=sb.append(input);
            }
            Log.i("DATA", result);

            ObjectMapper mapper = new ObjectMapper();
            MovieVO[] resultArr = new MovieVO[10];
//            String[] mName = new String[20];
//            String[] mCode = new String[20];

            try {
                JsonNode jnode = mapper.readTree(result);

                for (int i = 0; i<10; i++) {
                    if (jnode.isObject()) {
                        ObjectNode obj = (ObjectNode) jnode;
                        if (obj.has("boxOfficeResult")) {
                            JsonNode mn = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("movieNm");
                            JsonNode mcd = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("movieCd");
                            JsonNode r = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("rank");
                            JsonNode oDt = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("openDt");
                            JsonNode aCnt = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("audiCnt");
                            JsonNode sCnt = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("showCnt");
                            JsonNode aAcc = obj.get("boxOfficeResult").get("dailyBoxOfficeList").get(i).get("audiAcc");
//                            Log.i("RESULT", String.valueOf(mName[i));
//                            mName[i] = String.valueOf(mn);
//                            Log.i("RESULT", String.valueOf(mCode[i]));
//                            mCode[i] = String.valueOf(mcd);
                            MovieVO m = new MovieVO();

                            m.setMovieNm(String.valueOf(mn));
                            m.setMovieCd(String.valueOf(mcd));
                            m.setRank(String.valueOf(r));
                            m.setOpenDt(String.valueOf(oDt));
                            m.setAudiCnt(String.valueOf(aCnt));
                            m.setShowCnt(String.valueOf(sCnt));
                            m.setAudiAcc(String.valueOf(aAcc));

                            resultArr[i] = m;

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

//            Bundle bundle = new Bundle();
//            bundle.putStringArray("MOVIE_ARRAY", mName);
//            bundle.putStringArray("MOVIE_CODE", mCode);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MOVIE_ARRAY", resultArr);
//            bundle.putStringArray("MOVIE_ARRAY" ,mName);
//            bundle.putStringArray("MOVIE_CODE", mCode);

            Log.i("DATA", "에러 나지 말아용 ");
            Message msg = new Message();
            msg.setData(bundle);

            handler.sendMessage(msg);
        } catch (Exception e){
            Log.i("DATA_ERROR", e.toString());
        }
    }
}

public class MovieChartActivity extends AppCompatActivity {


    private long now = System.currentTimeMillis();
    MakeSendingDate msdate = new MakeSendingDate();

    private String movieCd;


    Date d = new Date(now);
    SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
    SimpleDateFormat msdf = new SimpleDateFormat("mm");
    SimpleDateFormat dsdf = new SimpleDateFormat("dd");

    int yyear = Integer.parseInt(ysdf.format(d));
    int mmonth = Integer.parseInt(msdf.format(d));
    int dday = Integer.parseInt(dsdf.format(d));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_chart);

        final Button mrank_btn = (Button)findViewById(R.id.mrank_btn);
        final ListView listView = (ListView)findViewById(R.id.movielist);

        final TextView tv = (TextView)findViewById(R.id.tv);
        final EditText et = (EditText)findViewById(R.id.et);

        final Handler handler = new Handler(){

            // handler에게 message가 전달되면 아래의 method가 callback된다.

            @Override
            public void handleMessage(@NonNull final Message msg) {
                super.handleMessage(msg);
                final Bundle bundle = msg.getData();
//                MovieVO[] result = (MovieVO[]) bundle.getSerializable("MOVIE_ARRAY");
                final MovieVO[] result = (MovieVO[]) bundle.getSerializable("MOVIE_ARRAY");
                String[] mname = new String[result.length];

                for (int i = 0; i<result.length; i++){
                    mname[i] = result[i].getMovieNm();
                    Log.i("ListView_mname: ", mname[i]);
                }

                ArrayAdapter adapter = new ArrayAdapter(MovieChartActivity.this, android.R.layout.simple_list_item_1, mname);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Log.i("selectedText", "선택된 영화 : " + position);
 //                       String[] code = bundle.getStringArray("MOVIE_CODE");
   //                     Log.i("selectedText", "선택된 영화코드 : " +String.valueOf(code[position]));


                        Intent mIntent = new Intent();
                        mIntent.putExtra("movieCd", String.valueOf(result[position].getMovieCd()));
                        ComponentName cname = new ComponentName("com.example.androidsample","com.example.androidsample.MovieDetailActivity");
                        mIntent.setComponent(cname);

                        startActivity(mIntent);
                    }

                });

            }
        };


        final DatePicker datePicker = (DatePicker)findViewById(R.id.dataPicker);
        datePicker.init(yyear, mmonth, dday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                msdate.setDate(yyear, mmonth, dday);
                Toast.makeText(MovieChartActivity.this, msdate.getSendData(), Toast.LENGTH_SHORT).show();
            }
        });


        mrank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchMovieRunnable runnable = new SearchMovieRunnable(handler, et.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });


    }


}
