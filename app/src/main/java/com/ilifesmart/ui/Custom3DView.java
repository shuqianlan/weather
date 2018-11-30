package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public class Custom3DView extends ViewGroup {

    private Camera mCamera;
    private Matrix mMatrix;
    private Scroller mScroller;
    private VelocityTracker mTracker;

    private float mDownY = 0;
    private static final int standerSpeed = 2000;

    private int mStartScreen = 0;
    private int mCurrScreen = 0;
    private int mHeight;
    private int angle = 90;

    private static final int STATE_PRE = 0;
    private static final int STATE_NEXT = 1;
    private static final int STATE_NORMAL = 2;
    private int STATE = -1;
    private float resistance = 1.0f;

    public Custom3DView(Context context) {
        this(context, null);
    }

    public Custom3DView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Custom3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCamera = new Camera();
        mMatrix = new Matrix();

        mScroller = new Scroller(getContext(), new LinearInterpolator());
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeight);
        MarginLayoutParams params = (MarginLayoutParams)getLayoutParams();
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth-(params.leftMargin+params.rightMargin), MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight-(params.topMargin+params.bottomMargin), MeasureSpec.EXACTLY);
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        mHeight = getMeasuredHeight();
        scrollTo(0, (mStartScreen+1)*mHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        int childTop = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                if (i == 0) {
                    childTop += params.topMargin;
                }
                child.layout(params.leftMargin, childTop, child.getMeasuredWidth() + params.leftMargin, childTop+child.getMeasuredHeight());
                childTop += child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            drawScreen(canvas, i, getDrawingTime());
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }

        mTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0, getScrollY());
                }
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int disY = (int) (mDownY - y);
                mDownY = y;
                if (mScroller.isFinished()) {
                    recycleMove(disY);
                }
                break;
            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(1000);
                float velocitY = mTracker.getXVelocity();

                //滑动的速度大于规定的速度，或者向上滑动时，上一页页面展现出的高度超过1/2。则设定状态为STATE_PRE
                if (velocitY >= standerSpeed || (getScrollY() + mHeight/2)/mHeight < mStartScreen+1) {
                    STATE = STATE_PRE;
                    //滑动的速度大于规定的速度，或者向下滑动时，下一页页面展现出的高度超过1/2。则设定状态为STATE_NEXT
                } else if (velocitY < -standerSpeed || (getScrollY() + mHeight/2)/mHeight > mStartScreen+1) {
                    STATE = STATE_NEXT;
                } else {
                    STATE = STATE_NORMAL;
                }
                changeByState();
                if (mTracker != null) {
                    mTracker.recycle();
                    mTracker = null;
                }
              break;

        }

        return true;
    }

    private void changeByState() {
        switch (STATE) {
            case STATE_NORMAL:
                toNormalAction();
                break;
            case STATE_PRE:
                toPrePager();
                break;
            case STATE_NEXT:
                toNextPager();
                break;
        }
    }

    private void toNormalAction() {
        int startY, delta, duration;
        STATE = STATE_NORMAL;
        startY = getScrollY();
        delta = mHeight * (mStartScreen+1) - getScrollY();
        duration = Math.abs(delta) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void toNextPager() {
        STATE = STATE_NEXT;
        setNext();
        int startY = getScrollY() - mHeight;
        int delta = mHeight * (mStartScreen+1) - startY;
        int duration = (Math.abs(delta))*2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void toPrePager() {
        STATE = STATE_PRE;
        setPre();
        int startY = getScrollY() + mHeight;
        setScrollY(startY);
        int delta = mHeight * (mStartScreen+1) - startY;
        int duration = (Math.abs(delta))*2;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    private void setNext() {
        mCurrScreen = (mCurrScreen + 1) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);
    }

    private void setPre() {
        mCurrScreen = ((mCurrScreen - 1) + getChildCount()) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
    }

    private void recycleMove(int delta) {
        delta = delta % mHeight;
        delta = (int) (delta / resistance);
        if (Math.abs(delta) > mHeight / 4) {
            return;
        }
        if (getScrollY() <= 0 && mCurrScreen <= 0 && delta <= 0) {
            return;
        }
        if (mHeight * mCurrScreen <= getScrollY() && mCurrScreen == getChildCount() - 1 && delta >= 0) {
            return;
        }
        scrollBy(0, delta);
        if (getScrollY() < 8 && mCurrScreen != 0) {
            setPre();
            scrollBy(0, mHeight);
        } else if (getScrollY() > (getChildCount() - 1) * mHeight - 8) {
            setNext();
            scrollBy(0, -mHeight);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 画单个页面
     *
     * @param canvas
     * @param screen
     * @param drawingTime
     */

    private void drawScreen(Canvas canvas, int screen, long drawingTime) {
        // 得到当前子View的高度
        final int height = getHeight();
        final int scrollHeight = screen * height;
        final int scrollY = this.getScrollY();
        // 偏移量不足的时
        if (scrollHeight > scrollY + height || scrollHeight + height < scrollY) {
            return;
        }
        final View child = getChildAt(screen);
        final int faceIndex = screen;
        final float currentDegree = getScrollY() * (angle / getMeasuredHeight());
        final float faceDegree = currentDegree - faceIndex * angle;
        if (faceDegree > 90 || faceDegree < -90) {
            return;
        }
        final float centerY = (scrollHeight < scrollY) ? scrollHeight + height : scrollHeight;
        final float centerX = getWidth() / 2;
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;
        canvas.save();
        camera.save();
        camera.rotateX(faceDegree);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.concat(matrix);
        drawChild(canvas, child, drawingTime);
        canvas.restore();
    }
}
