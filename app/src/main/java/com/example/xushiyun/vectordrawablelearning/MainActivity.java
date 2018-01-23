package com.example.xushiyun.vectordrawablelearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secondBezierTest(View view) {
        startActivity(new Intent(this, SecondBezierActivity.class));
    }

    public void thirdBezierTest(View view) {
        startActivity(new Intent(this, ThirdBezierActivity.class));
    }

    public void drawPadBezierTest(View view) {
        startActivity(new Intent(this, DrawPadActivity.class));
    }
}
