package com.bskoskusk.imagetotex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {
    ImageView iV;
    TextView tV;
    public void create(View kuchbhi) {
        Intent signInActivity = new Intent(homeActivity.this, com.bskoskusk.imagetotex.signInActivity.class);
        Pair[] pairs = new Pair[3];
        pairs [0] = new Pair(iV,"logo_image");
        pairs [1] = new Pair(tV,"logo_text");
        pairs [2] = new Pair(tV,"continue_trans");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(homeActivity.this,pairs);
        startActivity(signInActivity,options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        iV = findViewById(R.id.imageView4);
        tV = findViewById(R.id.textViewDescrip);
    }
}