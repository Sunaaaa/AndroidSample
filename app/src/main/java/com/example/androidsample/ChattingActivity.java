package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Button btn = (Button)findViewById(R.id.chat_btn);
        final ScrollView sv = (ScrollView)findViewById(R.id.sv);
        final TextView msgtv = (TextView)findViewById(R.id.chat_tv);
        final TextView uidtv = (TextView)findViewById(R.id.chat_uid);
        final EditText et = (EditText)findViewById(R.id.chat_et);

        msgtv.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgtv.append(uidtv.getText() + " >> " + et.getText()+"\n");
                sv.fullScroll(View.FOCUS_DOWN);
                et.setText("");
//                msgtv.append(et.getText()+"\n");
//                et.setText("");
//                msgtv.append("\n");

                // Scroll 안함!
//                msgtv.scrollTo(0,0);

                // Scroll 안함!
//                msgtv.scrollTo(0,100);


            }
        });
    }

}
