package com.example.androidcase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by dpw on 8/12/14.
 */
public class ButtonProgressBar extends ProgressBar {

    private String text;
    private int textSize = 18;
    private String textColor = "#FF00FF";
    private Paint mPaint;
    private Canvas canvas;

    public ButtonProgressBar(Context context) {
        super(context);
        initText();
    }

    public ButtonProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initText();
    }

    public ButtonProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    @Override
    public void setProgress(int progress) {
//        setText(progress);
        super.setProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.setColor(Color.parseColor(this.textColor));
        this.mPaint.setTextSize(this.textSize);
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    // 初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
    }

    // 设置文字内容
    private void setText(int progress) {
        int i = (int) ((progress * 1.0f / this.getMax()) * 100);
        this.text = String.valueOf(i) + "%";
    }

    // 设置文字内容
    public void setText(String text) {
        this.text = text;
    }

    // 设置文字内容
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    // 设置文字内容
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    public interface OnButtonProgressListener {
        void finish();
    };


}
