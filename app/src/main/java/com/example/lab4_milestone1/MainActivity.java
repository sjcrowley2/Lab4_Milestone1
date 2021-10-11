package com.example.lab4_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private Button startButton;
    private TextView textView;
    private volatile boolean stopThread = false;
    private volatile int downloadProgress;

    class ExampleRunnable implements Runnable{
        @Override
        public void run(){
            mockFileDownloader();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        textView = findViewById(R.id.textView);

    }

    public void mockFileDownloader(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");

            }
        });

        for (downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10){

            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("");
                        startButton.setText("Start");
                    }
                });
                return;
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            textView.setText("Download Progress: " + downloadProgress + "%");
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });

    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread = true;
    }

}


