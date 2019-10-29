package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Carouse3Layout extends FrameLayout {

    private Carouse2Layout viewContainer = null;
    private LinearLayout dotsContainer = null;

    public Carouse3Layout(@NonNull Context context) {
        this(context, null);
    }

    public Carouse3Layout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Carouse3Layout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewContainer = new Carouse2Layout(context);
        addView(viewContainer);
    }

    public void addScrollView(View view) {
        viewContainer.addView(view);
        viewContainer.requestLayout();
    }

}
