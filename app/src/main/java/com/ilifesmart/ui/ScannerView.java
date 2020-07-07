package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

public class ScannerView extends CustomSurfaceView {

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Paint mPaint;
    private LinearGradient mLinearGradient;

    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mLinearGradient = new LinearGradient(w/2, 0, w/2, h/3, Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
    }

    private int offsetY = 0;
    @Override
    public void doDraw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        canvas.drawRect(0, offsetY, getWidth(), getHeight()/3+offsetY, mPaint);

        offsetY++;

        if (offsetY >= (1.3 * getHeight())) {
            offsetY = 0;
        }

        Log.d("ScannerView", "doDraw: ~~~~~~~~~~");

        try {
        	Thread.sleep(16);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        super.onSurfaceCreated(holder);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.onSurfaceChanged(holder, format, width, height);
    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
        super.onSurfaceDestroyed(holder);
    }
}
