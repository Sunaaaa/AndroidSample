package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

class MovieDetailRunnable implements Runnable {

    private String keyword;
    private Handler handler;

    public MovieDetailRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
//        int key = Integer.parseInt(keyword);
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=fab6fd6434401fd4302d3ebdab8fef88&movieCd=" + keyword;
//        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=fab6fd6434401fd4302d3ebdab8fef88&movieCd=" + key;
//        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=fab6fd6434401fd4302d3ebdab8fef88&targetDt=" + keyword;

        try {
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();

            Log.i("RESULT","url : "+url);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = "";
            StringBuffer sb = new StringBuffer();
            String result="";

            while ((input = br.readLine()) != null) {
                result += sb.append(input);
                Log.i("RESULT", "sb : "+ sb.toString());
            }

            Log.i("RESULT", "result : "+ result);
            ObjectMapper mapper = new ObjectMapper();

            String[] mDetail = new String[20];

            try {
                JsonNode jnode2 = mapper.readTree(result);

                if (jnode2.isObject()) {
                    ObjectNode obj2 = (ObjectNode) jnode2;
                    if (obj2.has("movieInfoResult")) {
                        JsonNode mn = obj2.get("movieInfoResult").get("movieInfo").get("movieNm");
                        Log.i("RESULT", String.valueOf(mn));
                        JsonNode mEn = obj2.get("movieInfoResult").get("movieInfo").get("movieNmEn");
                        Log.i("RESULT", String.valueOf(mEn));
                        JsonNode sTm = obj2.get("movieInfoResult").get("movieInfo").get("showTm");
                        Log.i("RESULT", String.valueOf(sTm));
                        JsonNode od = obj2.get("movieInfoResult").get("movieInfo").get("openDt");
                        Log.i("RESULT", String.valueOf(od));
                        mDetail[0] = String.valueOf(mn);
                        mDetail[1] = String.valueOf(mEn);
                        mDetail[2] = String.valueOf(sTm);
                        mDetail[3] = String.valueOf(od);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putStringArray("MOVIE_DETAIL", mDetail);
            Log.i("DATA", "에러 나지 말아용 ");
            Message msg = new Message();
            msg.setData(bundle);

            handler.sendMessage(msg);
        } catch (Exception e) {
            Log.i("DATA_ERROR", e.toString());
        }
    }
}

public class MovieDetailActivity extends AppCompatActivity {

    private JsonNode jnode;
    private String mCd;
    private String movieCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        mCd = intent.getExtras().getString("movieCd");

        Log.i("MCD", mCd);
        Log.i("MCD", String.valueOf(mCd.length()));
        movieCd = mCd.substring(1,9);
        Log.i("MCD", movieCd);
        Log.i("MCD", String.valueOf(movieCd.length()));

        Toast.makeText(this,movieCd,Toast.LENGTH_SHORT).show();

        final TextView name = (TextView) findViewById(R.id.name);
        final TextView enName = (TextView) findViewById(R.id.enName);
        final TextView oDate = (TextView) findViewById(R.id.oDate);
        final TextView showTm = (TextView) findViewById(R.id.showTm);

        final Handler handler = new Handler() {

            // handler에게 message가 전달되면 아래의 method가 callback된다.

            @Override
            public void handleMessage(@NonNull final Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String[] result = bundle.getStringArray("MOVIE_DETAIL");

                name.setText(result[0]);
                enName.setText(result[1]);
                oDate.setText(result[3]);
                showTm.setText(result[2]);
            }
        };

        MovieDetailRunnable runnable = new MovieDetailRunnable(handler, movieCd);
        Thread t = new Thread(runnable);
        t.start();
    }


}
