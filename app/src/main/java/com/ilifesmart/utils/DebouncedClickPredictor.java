package com.ilifesmart.utils;

import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class DebouncedClickPredictor {
    public static long FROZEN_WINDOW_MILLS = 300;
    public static final String TAG = DebouncedClickPredictor.class.getSimpleName();
    private static final Map<View, FrozenView> weakMap = new WeakHashMap<>();

    public static boolean shouldDoClick(View targetView) {
        FrozenView frozenView = weakMap.get(targetView);
        long now = now();

        if (frozenView == null) {
            frozenView = new FrozenView(targetView);
            frozenView.setFrozenWindTime(now + FROZEN_WINDOW_MILLS);
            weakMap.put(targetView, frozenView);
            return true;
        }

        if (now > frozenView.getFrozenWindTime()) {
            frozenView.setFrozenWindTime(now + FROZEN_WINDOW_MILLS);
            return true;
        }

        return false;
    }

    private static long now() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    public static class FrozenView extends WeakReference<View> {
        private long FrozenWindTime;

        FrozenView(View refrent) {
            super(refrent);
        }

        public long getFrozenWindTime() {
            return FrozenWindTime;
        }

        public void setFrozenWindTime(long frozenWindTime) {
            FrozenWindTime = frozenWindTime;
        }
    }
}
