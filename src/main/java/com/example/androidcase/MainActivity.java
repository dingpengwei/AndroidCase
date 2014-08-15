package com.example.androidcase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

  DownloadSingle bar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      this.setContentView(R.layout.activity_main);
      bar = (DownloadSingle) this.findViewById(R.id.xxx);
      bar.setText("点击下载");
      bar.setTextSize(30);
      bar.setTextColor("#FF00FF");

      bar.setClickable(true);
      bar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              bar.startDownload("http://192.168.11.244/down/FileDownService?filename=abc.zip","/mnt/sdcard/jpg.jpg",new DownloadButtonProgressBar.OnButtonProgressListener() {
                  @Override
                  public void finish() {
                      try{
//                          bar.setText("下载完成");
//                          bar.setProgress(bar.getMax());
                          Toast.makeText(MainActivity.this,"OK",1).show();
                      } catch (Exception e){
                          e.printStackTrace();
                      }
                  }
              });
          }
      });
  }
}