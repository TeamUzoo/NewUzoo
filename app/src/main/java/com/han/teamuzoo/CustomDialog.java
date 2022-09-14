package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomDialog extends AppCompatActivity {

    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callDialog(){
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_custom_dialog);
        dialog.show();

        final ImageView btnNo = (ImageView) dialog.findViewById(R.id.btnNo);
        final ImageView btnYes = (ImageView) dialog.findViewById(R.id.btnYes);

        // 아니오 버튼
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "아니오", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
//                ((MainActivity) context).finish();
            }
        });

        // 포기하기 버튼
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomDialog.this, CustomDialog2.class);
                startActivity(intent);
                finish();
            }

        });

        dialog.show();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);
    }
}