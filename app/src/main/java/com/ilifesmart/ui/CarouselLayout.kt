package com.ilifesmart.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.util.jar.Attributes

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

    private lateinit var gestureDetector: GestureDetector
    init {
        gestureDetector = GestureDetector(context, DefaultGestureDetector())
    }

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

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        println("onTouchEvent ... ")
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            return true
//        }
//        return gestureDetector.onTouchEvent(event)
//    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        println("dispatchTouchEvent .. ")
        return gestureDetector.onTouchEvent(ev)
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
                for (index in 0 until childCount) {
                    getChildAt(index).translationX = -value.toFloat()
                }
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    inner class DefaultGestureDetector: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val moveX = e2?.getX() ?: 0f
            val beginX = e1?.getX() ?: 0f

            val offsetDistanceX = moveX-beginX
            println("velocityX:$velocityX, velocityY:$velocityY, offsetDistanceX:$offsetDistanceX")
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            println("onDown: true")
            return true
        }

    }

}
