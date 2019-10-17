package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchEventAndToastActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Activity 객체, 문자열, 얼마나 Toast 문자열이 떠있다가 사라질 것인지
        // makeText() : 문자열을 만들기
        // 반드시 show()를 해야 한다.
        Toast.makeText(getApplicationContext(), R.string.toastMsg, Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}

