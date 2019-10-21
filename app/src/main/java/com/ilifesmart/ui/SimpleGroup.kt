package com.ilifesmart.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

import com.ilifesmart.utils.DensityUtils
import com.ilifesmart.utils.Utils

class SimpleGroup : ViewGroup {
    private var animator: ValueAnimator? = null

    private var once = true

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)

        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        var maxHeight = 0
        var width = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams

            maxHeight = Math.max(child.measuredHeight + lp.bottomMargin + lp.topMargin, maxHeight)
            width += child.measuredWidth + lp.leftMargin + lp.rightMargin
        }

        val nWidth = if (widthMode == View.MeasureSpec.EXACTLY) widthSize else width
        val nHeight = if (heightMode == View.MeasureSpec.EXACTLY) heightSize else maxHeight
        setMeasuredDimension(nWidth, nHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        val count = childCount

        Log.d(TAG, "onLayout: count $count")
        for (index in 0 until count) {
            val child = getChildAt(index)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams

            val height = child.measuredHeight + lp.topMargin + lp.bottomMargin
            val width = child.measuredWidth
            child.layout(left, 0, left + width, height) // 先onSizeChanged后onLayout

            left += width
        }
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        animator!!.cancel()
        animator = null
    }

    fun autoPlay() {
        if (childCount == 0 || animator != null && animator!!.isRunning) {
            return
        }

        val arr = IntArray(2)
        DensityUtils.getWindowSize(context, arr)
        val windowWidth = arr[0]
        animator = ValueAnimator.ofInt(0, windowWidth)

        animator!!.duration = 4000
        animator!!.startDelay = 4000
        animator!!.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            for (i in 0 until childCount) {
                getChildAt(i).translationX = (-value).toFloat()
            }
        }

        animator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                if (!once) {
                    val lastIndex = 0 //(loopCount++) % getChildCount();
                    val v = getChildAt(lastIndex)
                    removeViewAt(lastIndex)
                    addView(v)
                    requestLayout()
                }
                once = false
            }

            override fun onAnimationEnd(animation: Animator) {
                animation.start()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {
                Log.d(TAG, "onAnimationRepeat: ")
            }
        })
        animator!!.start()
    }

    companion object {

        val TAG = "SimpleGroup"
    }

}
