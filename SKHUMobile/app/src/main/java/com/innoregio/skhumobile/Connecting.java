package com.innoregio.skhumobile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connecting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Connecting Activity", "Connecting Activity Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);
        Log.w("Connecting Activity", "Layout set");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                loadApp();
            }
        });

        t.start();
    }

    public void loadApp(){
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (isOnline()) {
            if (isAvailable("http://tokajgis.uni-eszterhazy.hu:8080/new_skhu/index.html#"))
            {



                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            } else {
                startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
            }
        } else {
            startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting() ;
    }

    boolean isAvailable(String urll){
        try {
            Log.w("isAvailable", "Still working 1");
            URL url = new URL(urll);
            Log.w("isAvailable", "Still working 2");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.w("isAvailable", "Still working 3");
            urlConnection.setRequestMethod("HEAD");
            Log.w("isAvailable", "Still working 4");
            urlConnection.connect();
            Log.w("isAvailable", "Still working 5");
            return (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }
}
