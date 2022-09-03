package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RekogActivity extends AppCompatActivity {

    Button button;
    TextView txtCoin;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekog);

        button = findViewById(R.id.button);
        txtCoin = findViewById(R.id.txtCoin);
        imgBack = findViewById(R.id.imgBack);



    }
}