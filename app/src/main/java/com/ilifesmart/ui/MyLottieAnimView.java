package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

public class MyLottieAnimView extends LottieAnimationView {

    public MyLottieAnimView(Context context) {
        super(context);
    }

    public MyLottieAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLottieAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isInEditMode() {
        super.isInEditMode();
        return true;
    }
}
