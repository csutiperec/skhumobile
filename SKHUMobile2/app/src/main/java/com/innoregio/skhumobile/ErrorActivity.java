package com.innoregio.skhumobile;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Error Activity", "Error Activity Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
    }
}
