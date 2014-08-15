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
public class DownloadUtil extends DownloadButtonProgressBar {

    public DownloadUtil(Context context) {
        super(context);
    }

    public DownloadUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DownloadUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startDownload(OnButtonProgressListener onButtonProgress,String... params){
        new DownloadTask(this,onButtonProgress,params);
    }

    class DownloadTask extends AsyncTask<String,Integer,OnButtonProgressListener> {
        private DownloadUtil singleDownload;
        private OnButtonProgressListener onButtonProgressListener;

        public DownloadTask(DownloadUtil singleDownload,OnButtonProgressListener buttonProgressListener,String... params) {
            super();
            this.singleDownload = singleDownload;
            this.onButtonProgressListener = buttonProgressListener;
            this.execute(params);
        }

        @Override
        protected OnButtonProgressListener doInBackground(String... params) {
            int length = params.length;
            for(int i=0;i<=length;i= i + 2){
                try {
                    URL url = new URL(params[i]);
                    URLConnection con = url.openConnection();
                    int contentLength = con.getContentLength();
                    this.singleDownload.setProgress(0);
                    this.singleDownload.setMax(contentLength);
                    InputStream is = con.getInputStream();
                    byte[] bs = new byte[1024];
                    int len;
                    OutputStream os = new FileOutputStream(new File(params[i + 1]));
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                        this.publishProgress(i/2+1,length/2,len,contentLength);
                    }
                    os.close();
                    is.close();
                } catch (Exception e){
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
            return this.onButtonProgressListener;
        }

        @Override
        protected void onPostExecute(OnButtonProgressListener onButtonProgressListener) {
            super.onPostExecute(onButtonProgressListener);
            if(this.onButtonProgressListener != null){
                this.onButtonProgressListener.finish(null);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            float percent = ((float)(this.singleDownload.getProgress() + values[2]) / (float) values[3]);
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);

            this.singleDownload.setText("下载个数:" + String.valueOf(values[0]) + "/" + String.valueOf(values[1]) + " 下载进度：" + nt.format(percent));
            this.singleDownload.setProgress(this.singleDownload.getProgress() + values[2]);
        }
    }
}
