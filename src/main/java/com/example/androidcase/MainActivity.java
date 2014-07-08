package com.example.androidcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.goodow.realtime.json.Json;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      try {
          Intent intent = new Intent(this, PDFActivity.class);
          intent.putExtra("msg", Json.createObject().set("path", "/mnt/sdcard/git.pdf"));
          intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
          this.startActivity(intent);
          this.finish();
      } catch (Exception e){
          e.printStackTrace();
      }
  }
}