package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ChangeImageActivity extends AppCompatActivity {

    int p = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);

        Button imgBtn = (Button)findViewById(R.id.img_btn);
        final ImageView iv = (ImageView)findViewById(R.id.image);
        Glide.with(this).load(R.raw.gh).into(iv);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                iv.setImageResource(R.drawable.now);
                if (p==1){
                    Glide.with(ChangeImageActivity.this).load(R.raw.gh2).into(iv);
                    p=0;
                }
                else if (p==0){
                    Glide.with(ChangeImageActivity.this).load(R.raw.gh).into(iv);
                    p=1;
                }
            }
        });
    }
}
