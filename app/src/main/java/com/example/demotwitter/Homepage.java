package com.example.demotwitter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Homepage extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        String username = getIntent().getStringExtra("username");
        TextView tv = findViewById(R.id.tv_username);
        tv.setText(username);
    }
}
