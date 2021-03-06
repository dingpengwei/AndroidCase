package com.example.androidcase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

  DownloadUtil bar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      this.setContentView(R.layout.activity_main);
      bar = (DownloadUtil) this.findViewById(R.id.xxx);
      bar.setText("点击下载");
      bar.setTextSize(30);
      bar.setTextColor("#FF00FF");

      bar.setClickable(true);
      bar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              bar.startDownload(new DownloadButtonProgressBar.OnButtonProgressListener() {
                  @Override
                  public void finish(String message) {
                      try{
                          bar.setText("下载完成");
                          bar.setProgress(0);
                          Toast.makeText(MainActivity.this,"OK",1).show();
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }

                  @Override
                  public void error(String message) {

                  }
              },"http://192.168.11.244/down/FileDownService?filename=keruixing.db","/mnt/sdcard/xxx1.jpg",
                      "http://192.168.11.244/down/FileDownService?filename=keruixing.db","/mnt/sdcard/xxx2.jpg",
                      "http://192.168.11.244/down/FileDownService?filename=keruixing.db","/mnt/sdcard/xxx3.jpg");
          }
      });
  }
}