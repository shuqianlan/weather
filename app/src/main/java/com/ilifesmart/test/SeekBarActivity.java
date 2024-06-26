package com.ilifesmart.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ilifesmart.ui.VerticalSeekBar;
import com.ilifesmart.weather.R;

public class SeekBarActivity extends AppCompatActivity {

    public static final String TAG = "SeekBarActivity";

    VerticalSeekBar mSeekBar;
    TextView mProgress;
    private int availHeight;
    private boolean once = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        mSeekBar = findViewById(R.id.seekBar);
        mProgress = findViewById(R.id.progress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setProgressValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!once) {
                    setProgressValue(mSeekBar.getProgress());
                    once = true;
                }
            }
        });
    }

    private void setProgressValue(int progress) {
        int availHeight = mSeekBar.getWidth() - mSeekBar.getPaddingLeft() - mSeekBar.getPaddingRight(); // 活动区的高度.
        int offsetY = (int)((float)progress/100*availHeight+mSeekBar.getPaddingLeft());
        int translationY = mProgress.getTop()+(mProgress.getBottom()-mProgress.getTop())/2;

        mProgress.setTranslationY(translationY-offsetY);

        String text = progress+"%";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new TextAppearanceSpan(null, 0, 40, null, null), text.length()-1, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mProgress.setText(builder);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SeekBarActivity.class);
    }
}
