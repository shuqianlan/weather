package com.ilifesmart.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Carouse2Layout extends ViewGroup {

    public static final String TAG = "CarouseLayout";

    private float touchX, lastX;
    private boolean once = true;
    private float MIN_TOUCHSLOP_DISTANCE;

    private boolean isCanTouchMove = true;
    private ValueAnimator mAutoAlignAnimator;
    private ValueAnimator mAutoPlayAnimator;

    public Carouse2Layout(Context context) {
        this(context, null);
    }

    public Carouse2Layout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        MIN_TOUCHSLOP_DISTANCE = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.d(TAG, "Carouse2Layout: MIN_TOUCHSLOP_DISTANCE " + MIN_TOUCHSLOP_DISTANCE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0, height = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
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

        // 每次滑动结束时候通过requestLayout()重置translationX及left的位置
        debugprint();
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

        Log.d(TAG, "onTouchEvent: canTouchMove " + canTouchMove);
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
                    getMiddleView().callOnClick();
                } else if (canTouchMove) {
                    animeToEnd(lastX-touchX);
                }
                break;
        }

        resetAutoPlay();
        lastX = event.getX();
        return true;
    }

    private void exchange(float offDistanceX) {
        if (offDistanceX > 0) {
            View v = getChildAt(getChildCount()-1);
            removeView(v);
            addView(v, 0);
        } else if (offDistanceX < 0) {
            View v = getChildAt(0);
            removeView(v);
            addView(v);
        }
        requestLayout();
    }

    private void animeToEnd(float start) {
        if (Math.abs(start) < MIN_TOUCHSLOP_DISTANCE) {
            return;
        }
        float end   = (start > 0) ? getWidth() : -getWidth();

        mAutoAlignAnimator = ValueAnimator.ofFloat(start, end);
        mAutoAlignAnimator.setDuration(300);
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
                debugprint();
                autoPlay();
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
        for (int index=0; index<getChildCount(); index++) {
            getChildAt(index).setTranslationX(translationx);
        }
    }

    public void autoPlay() {
        mAutoPlayAnimator = ValueAnimator.ofInt(0, -getWidth());
        mAutoPlayAnimator.setStartDelay(40000);
        mAutoPlayAnimator.setDuration(600);
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
                exchange(-getWidth());
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                animation.start();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mAutoPlayAnimator.start();
    }

    private void resetAutoPlay() {
        if (mAutoPlayAnimator != null) {
            mAutoPlayAnimator.cancel();
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

}
