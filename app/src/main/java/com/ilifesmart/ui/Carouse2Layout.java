package com.ilifesmart.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;


/*
* 功能支持:
* 1. 自动播放
* 2. 滑动刷新
* 3. 点击时的位置更新
*
* */
public class Carouse2Layout extends ViewGroup {

    public static final String TAG = "CarouseLayout";

    private float touchX, lastX; // 初始按下的位置及上次按下的相对父容器的位置.
    private float MIN_TOUCHSLOP_DISTANCE; // 认为是滑动的最小距离.

    private boolean isCanTouchMove = true; // 是否允许滑动
    private boolean isAutoPlay = true; // 是否允许滑动
    private boolean once = true;

    private static final int START_DELAY_TIME = 4000;
    private static final int AUTO_DELAY_TIME = 600;
    private static final int ANIM_END_TIME = 300;

    private ValueAnimator mAutoAlignAnimator; // 滑动松手后自动对齐的动画
    private ValueAnimator mAutoPlayAnimator;  // 自动播放的动画
    private Paint bitmapPaint;

    private Bitmap selected;
    private Bitmap unSelected;

    private int index = 0;

    private OnScrollListener listener;

    public Carouse2Layout(Context context) {
        this(context, null);
    }

    public Carouse2Layout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        MIN_TOUCHSLOP_DISTANCE = ViewConfiguration.get(context).getScaledTouchSlop();
        index = getMiddleIndex();

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selected = BitmapFactory.decodeResource(getResources(), R.drawable.dot_selected);
        unSelected = BitmapFactory.decodeResource(getResources(), R.drawable.dot_unselected);

        Log.d(TAG, "Carouse2Layout: neheng ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0, height = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0); // 测量子View的宽高
            width = child.getMeasuredWidth();
            height = child.getMeasuredHeight();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int middle = getMiddleIndex();
        int left = -getWidth()*middle;

        for (int index=0; index<count; index++) {
            View child = getChildAt(index);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int height = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int width = child.getMeasuredWidth();
            child.layout(left, 0, left + width, height);
            child.setTranslationX(0);
            left += width;
        }

        if (once) {
            index = middle;
            once=false;
        }
        // 每次滑动结束时候通过requestLayout()重置translationX及left的位置
        debugprint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw: ~~~~~~~~~~~~~~~~~");
        // 绘制点
        int dotSize = unSelected.getWidth();
        Log.d(TAG, "onDraw: dotSize " + dotSize);
        int childCount = 4; //getChildCount();
        int gapR = 10;
        int left = getWidth();

        for (int i = 0; i < childCount; i++) {
            Bitmap bitmap = null;
            if (i == index) {
                bitmap = selected;
            } else {
                bitmap = unSelected;
            }

            Log.d(TAG, "onDraw: left " + (left-(childCount-i)*(gapR+dotSize)) + " top " + (getHeight()-dotSize-20));
            canvas.drawBitmap(bitmap, left-(childCount-i)*(gapR+dotSize), getHeight()-dotSize-20, bitmapPaint);
        }
    }

    private int getMiddleIndex() {
        return (int) Math.floor(getChildCount()/2.0+0.05);
    }

    private View getMiddleView() {
        int index = getMiddleIndex();
        return getChildAt(index);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean canTouchMove = (isCanTouchMove && (mAutoAlignAnimator == null || !mAutoAlignAnimator.isRunning())) && (getChildCount() > 1); // 自动对齐动画执行中或当前仅一个元素则禁止滑动

        if (canTouchMove && isAutoPlay()) {
            isAutoPlay = false;
            endAutoPlay();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float currX = event.getX();
                if (canTouchMove) {
                    setChildrenTranslationX(currX-touchX);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(lastX-touchX) < MIN_TOUCHSLOP_DISTANCE) {
                    callOnClickView(getMiddleView());
                } else if (canTouchMove) {
                    animeToEnd(lastX-touchX);
                }
            case MotionEvent.ACTION_CANCEL:
                if (isAutoPlay()) {
                    isAutoPlay = true;
                }
                break;
        }
        lastX = event.getX();
        return true;
    }

    private void exchange(float offDistanceX) {
        int childCount = getChildCount();
        if (offDistanceX > 0) {
            View v = getChildAt(childCount-1);
            removeView(v);
            addView(v, 0);
            index = (index-1+childCount)%childCount;
        } else if (offDistanceX < 0) {
            View v = getChildAt(0);
            removeView(v);
            addView(v);
            index = (index+1+childCount)%childCount;
        }
        onScrollToEnd(index);
        requestLayout();
    }

    private void animeToEnd(float start) {
        if (Math.abs(start) < MIN_TOUCHSLOP_DISTANCE) {
            return;
        }
        float end = (start > 0) ? getWidth() : -getWidth();

        mAutoAlignAnimator = ValueAnimator.ofFloat(start, end);
        mAutoAlignAnimator.setDuration(ANIM_END_TIME);
        mAutoAlignAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationX = (float)animation.getAnimatedValue();
                setChildrenTranslationX(translationX);
            }
        });

        mAutoAlignAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                exchange(end);
                restartAutoPlay();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAutoAlignAnimator.start();
    }

    private void debugprint() {
        for (int i = 0; i < getChildCount(); i++) {
            String content = ((TextView)((FrameLayout)getChildAt(i)).getChildAt(0)).getText().toString();
            Log.d(TAG, "debugprint: content " + content + "Left " + getChildAt(i).getLeft() + " translationX " + getChildAt(i).getTranslationX());
        }
    }

    private void setChildrenTranslationX(float translationx) {
        if (Math.abs(translationx) < MIN_TOUCHSLOP_DISTANCE) {
            return;
        }
        for (int index=0; index < getChildCount(); index++) {
            getChildAt(index).setTranslationX(translationx);
        }

        onScrollTo(translationx, (int)Math.floor(100*translationx/getWidth()));
    }

    private boolean isAutoPlay() {
        return (mAutoPlayAnimator != null) || isAutoPlay ;
    }

    public void autoPlay() {
        if (!isAutoPlay) {
            return;
        }

        mAutoPlayAnimator = ValueAnimator.ofInt(0, -getWidth());
        mAutoPlayAnimator.setStartDelay(START_DELAY_TIME);
        mAutoPlayAnimator.setDuration(AUTO_DELAY_TIME);
        mAutoPlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animated = (int)animation.getAnimatedValue();
                setChildrenTranslationX(animated);
            }
        });
        mAutoPlayAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isAutoPlay) {
                    exchange(-getWidth());
                    animation.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mAutoPlayAnimator.start();
    }

    private void restartAutoPlay() {
        if (mAutoPlayAnimator != null && !mAutoPlayAnimator.isStarted()) {
            mAutoPlayAnimator.start();
        }
    }

    private void endAutoPlay() {
        if (mAutoPlayAnimator != null && mAutoPlayAnimator.isRunning()) {
            mAutoPlayAnimator.end();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    /*
    * distanceX: 水平偏移量
    * percent: 当前的进度百分比
    * index: 当前显示的元素index
    * */
    public void onScrollTo(float distanceX, int percent) {
        if (listener != null) {
            listener.onScrollTo(distanceX, percent, index);
        }
    }

    /*
    * index: 选中的新index
    * */
    private void onScrollToEnd(int index) {
        if(listener != null) {
            listener.onScrollToEnd(index);
        }
    }

    private void callOnClickView(View view) {
        if (listener != null) {
            listener.callOnClick(index, view);
        }
    }

    public interface OnScrollListener {
        void onScrollToEnd(int index);
        void onScrollTo(float distanceX, int percent, int index);
        void callOnClick(int index, View view);
    }

}
