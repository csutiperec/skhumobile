package com.innoregio.skhumobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Main Activity", "Main Activity Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Container.webView = findViewById(R.id.webView);;
        WebSettings webSettings = Container.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        Container.webView.addJavascriptInterface(new JavaScriptInterface(getApplicationContext()), "Android");
        webSettings.setPluginState(WebSettings.PluginState.ON);

        Container.webView.loadUrl("https://gis.uni-eszterhazy.hu");

        Container.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            }
        });
        Container.webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                /*
                try{
                    url.replace("blob:", "");
                    String decodedurl = URLDecoder.decode(url, "UTF-8");
                    String filename = URLUtil.guessFileName(decodedurl, contentDisposition, mimetype);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(decodedurl));
                    request.addRequestHeader("User-Agent", userAgent);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                    downloadManager.enqueue(request);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                */
                Container.webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(Container.webView.canGoBack()){
            Container.webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
