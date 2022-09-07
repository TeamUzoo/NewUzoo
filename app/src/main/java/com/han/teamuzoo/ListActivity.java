package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    FloatingActionButton fb_add_btn;
    FloatingActionButton fb_edit_btn;
    FloatingActionButton fb_image_btn;

    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        // 추가 버튼
        fb_add_btn = findViewById(R.id.fb_add_btn);
        fb_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        // 쓰기 버튼
        fb_edit_btn = findViewById(R.id.fb_edit_btn);
        fb_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"edit btn clicked",Toast.LENGTH_SHORT).show();
            }
        });

        // 이미지 버튼
        fb_image_btn = findViewById(R.id.fb_image_btn);
        fb_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"image btn clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 클릭 이벤트
    private void onAddButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    // 보여짐 사라짐
    // @param clicked 클릭여부
    private void setVisibility(boolean clicked){

        if(!clicked){
            // 보여진다
            fb_edit_btn.setVisibility(fb_add_btn.VISIBLE);
            fb_image_btn.setVisibility(fb_image_btn.VISIBLE);
        } else {
            // 숨긴다
            fb_edit_btn.setVisibility(fb_add_btn.INVISIBLE);
            fb_image_btn.setVisibility(fb_image_btn.INVISIBLE);
        }
    }

    // 애니메이션 효과
    // @param clicked 클릭여부
    private void setAnimation(boolean clicked){

        if(!clicked){
            fb_edit_btn.startAnimation(fromBottom);
            fb_image_btn.startAnimation(fromBottom);
            fb_add_btn.startAnimation(rotateOpen);
        } else {
            fb_edit_btn.startAnimation(toBottom);
            fb_image_btn.startAnimation(toBottom);
            fb_add_btn.startAnimation(rotateClose);
        }

    }


}