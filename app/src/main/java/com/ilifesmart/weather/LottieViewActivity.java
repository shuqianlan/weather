package com.ilifesmart.weather;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.ilifesmart.ui.MyLottieAnimView;

public class LottieViewActivity extends AppCompatActivity {

    MyLottieAnimView lottieView;
    public static final String TAG = "LottieView";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lottie_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lottieView = findViewById(R.id.lottie_view_inner);
        lottieView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                Log.d(TAG, "onCompositionLoaded: " + composition);
            }
        });

        lottieView.setAnimation("launcher.json");

        lottieView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                Log.d(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                Log.d(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                Log.d(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                Log.d(TAG, "onAnimationRepeat: ");
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.start_lottie) {
            lottieView.playAnimation();
        } else if (v.getId() == R.id.stop_lottie) {
            lottieView.pauseAnimation();
        } else if (v.getId() == R.id.start_once) {
            lottieView.setRepeatCount(0);
        } else if (v.getId() == R.id.start_loop) {
            lottieView.setRepeatCount(LottieDrawable.INFINITE);
        }
    }
}