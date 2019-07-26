package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyVerticalSeekBar extends AppCompatSeekBar {
    public MyVerticalSeekBar(Context context) {
        this(context, null);
    }

    public MyVerticalSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVerticalSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int percent = getMax() - (int)event.getY()/getHeight()*getMax();
                setProgress(percent);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
