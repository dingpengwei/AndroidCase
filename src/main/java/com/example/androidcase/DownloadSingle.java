package com.example.androidcase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;

/**
 * Created by dpw on 8/12/14.
 */
public class DownloadSingle extends DownloadButtonProgressBar {

    public DownloadSingle(Context context) {
        super(context);
    }

    public DownloadSingle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DownloadSingle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startDownload(String remoteFilePath,String localFilePath,OnButtonProgressListener onButtonProgress){
        new DownloadTask(remoteFilePath,localFilePath,this,onButtonProgress);
    }

    class DownloadTask extends AsyncTask<String,Integer,OnButtonProgressListener> {
        private String remoteFilePath;
        private String localFilePath;
        private DownloadSingle singleDownload;
        private OnButtonProgressListener onButtonProgressListener;

        public DownloadTask(String remoteFilePath,String localFilePath,DownloadSingle singleDownload,OnButtonProgressListener buttonProgressListener) {
            super();
            this.remoteFilePath = remoteFilePath;
            this.localFilePath = localFilePath;
            this.singleDownload = singleDownload;
            this.onButtonProgressListener = buttonProgressListener;
            this.execute(this.remoteFilePath);
        }

        @Override
        protected OnButtonProgressListener doInBackground(String... params) {
            try {
                URL url = new URL(this.remoteFilePath);
                URLConnection con = url.openConnection();
                int contentLength = con.getContentLength();
                this.singleDownload.setMax(contentLength);
                InputStream is = con.getInputStream();
                byte[] bs = new byte[1024];
                int len;
                OutputStream os = new FileOutputStream(new File(this.localFilePath));
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                    this.publishProgress(len);
                }
                os.close();
                is.close();
            }catch (Exception e){
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
            return this.onButtonProgressListener;
        }

        @Override
        protected void onPostExecute(OnButtonProgressListener onButtonProgressListener) {
            super.onPostExecute(onButtonProgressListener);
            if(this.onButtonProgressListener != null){
                this.onButtonProgressListener.finish();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            float percent = ((float)(this.singleDownload.getProgress() + values[0]) / (float) this.singleDownload.getMax());
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);

            this.singleDownload.setText("正在下载:" + nt.format(percent));
            this.singleDownload.setProgress(this.singleDownload.getProgress() + values[0]);
        }
    }
}
