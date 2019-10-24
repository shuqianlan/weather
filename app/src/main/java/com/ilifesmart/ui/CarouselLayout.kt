package com.ilifesmart.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import java.util.jar.Attributes
import kotlin.math.abs

class CarouselLayout : ViewGroup {
    constructor(context:Context): this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, defStyleAttr = 0)
    constructor(context: Context, attributeSet: AttributeSet?=null, defStyleAttr: Int=0): this(context, attributeSet, defStyleAttr, 0)
    constructor(context: Context, attributeSet: AttributeSet?=null, defStyleAttr: Int=0, defStyleRes:Int=0): super(context, attributeSet, defStyleAttr, defStyleRes)

    enum class Orientation {
        LEFT, RIGHT
    }

    private var animator:ValueAnimator?=null
    private var once = true
    private var touchX = 0f
    private var lastX = 0f

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        val count = childCount

        for (index in 0 until count) {
            val child = getChildAt(index)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams

            val height = child.measuredHeight + lp.topMargin + lp.bottomMargin
            val width = child.measuredWidth
            child.layout(left, 0, left + width, height)
            left += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        println("onInterceptTouchEvent ")
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("ontouchBegin .............. ")
        if (event?.action == MotionEvent.ACTION_DOWN) {
            touchX = event?.x ?: 0f
            lastX = touchX
        } else if (event?.action == MotionEvent.ACTION_MOVE) {
            var currX = event?.x ?: 0f

            setChildrenTranslationX(currX-touchX)
            lastX = currX
        } else if (event?.action == MotionEvent.ACTION_UP) {
            if (abs(lastX - touchX).toInt() <= ViewConfiguration.get(context).scaledTouchSlop) {
                println("触发OnClick事件")
            } else {
                //
                val lastOffsetDistanceX = width - abs(lastX-touchX)
                animToEnd(if ((lastX-touchX) > 0) lastOffsetDistanceX.toInt() else -lastOffsetDistanceX.toInt())
            }
            touchX = 0f
            lastX = 0f
        }

        resetAutoPlay()
        return true
    }

    fun animToEnd(start:Int) {
        var _start = start
        var _end = if (_start > 0) width else -width
        val animator = ValueAnimator.ofInt(_start, _end)
        animator.duration = 300
        animator.addUpdateListener {
            val distancex = it.animatedValue as Int
            setChildrenTranslationX(distancex.toFloat())
        }
    }

    fun autoPlay() {
        if (childCount == 0) {
            return
        }

        ValueAnimator.ofInt(0, width).apply {
            startDelay = 4000
            duration = 500
            addUpdateListener {
                val value = animatedValue as Int
                setChildrenTranslationX(-value.toFloat())
            }
            addListener(object :Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    animator?.start()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    animator?.start()
                }

                override fun onAnimationStart(animation: Animator?) {
                    if (!once) {
                        val view = getChildAt(0)
                        removeView(view)
                        addView(view)
                        requestLayout()
                    }

                    once = false
                }
            })
        }.also {
            animator = it
        }.start()
    }

    fun setChildrenTranslationX(translationx:Float) {
        for (index in 0 until childCount) {
            getChildAt(index).translationX = translationx
        }
    }

    fun resetAutoPlay() {
        animator?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

}
