package com.example.bluetoothcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class popUp extends AppCompatActivity {

    ImageButton terminal;
    ImageButton game;
    TextView tt;
    public static String EXTRA_ADRESS;
    public static String adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        getWindow().setLayout((int)(w*.8),(int)(h*.7));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        game=(ImageButton)findViewById(R.id.game_b);
        tt=(TextView)findViewById(R.id.textView4);
        Intent nInt=getIntent();
        adress=nInt.getStringExtra(ScrollingActivity.EXTRA_ADRESS);

    }

    

    public void game_onClick(View view) {
        Intent I=new Intent(getApplicationContext(),GameMode.class);
        I.putExtra(EXTRA_ADRESS,adress);
        startActivity(I);

    }
    public void voice_onClick(View view){
        Intent I=new Intent(getApplicationContext(),VoiceControl.class);
        I.putExtra(EXTRA_ADRESS,adress);
        startActivity(I);
    }
}
