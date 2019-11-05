package com.ilifesmart.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

import com.ilifesmart.weather.R

/*
* 功能支持:
* 1. 自动播放
* 2. 滑动刷新
* 3. 点击时的位置更新
* TODO:现在是居中位置作为当前视图, 根据目前的实现逻辑其实已经没必要如此处理了。主要还是懒得改,0.0
* */
class CarouseLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewGroup(context, attrs, 0) {

    private var touchX: Float = 0.toFloat()
    private var lastX: Float = 0.toFloat() // 初始按下的位置及上次按下的相对父容器的位置.
    private val MIN_TOUCHSLOP_DISTANCE: Float // 认为是滑动的最小距离.
    private var START_DELAY_TIME = 4000
    private var AUTO_DELAY_TIME = 600

    private var isCanTouchMove = true // 是否允许滑动
    private var isAutoPlay = true // 是否允许滑动
    private var once = true

    private var mAutoAlignAnimator: ValueAnimator? = null // 滑动松手后自动对齐的动画
    private var mAutoPlayAnimator: ValueAnimator? = null  // 自动播放的动画

    private val bitmapPaint: Paint
    private val selected: Bitmap
    private val unSelected: Bitmap

    private var index = 0

    private var listener: OnScrollListener? = null

    private val middleIndex: Int
        get() = Math.floor(childCount / 2.0 + 0.05).toInt()

    private val middleView: View
        get() {
            val index = middleIndex
            return getChildAt(index)
        }

    init {
        MIN_TOUCHSLOP_DISTANCE = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
        index = middleIndex

        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        selected = BitmapFactory.decodeResource(resources, R.drawable.dot_selected)
        unSelected = BitmapFactory.decodeResource(resources, R.drawable.dot_unselected)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CarouselLayout)
            isAutoPlay = typedArray.getBoolean(R.styleable.CarouselLayout_auto_play, isAutoPlay)
            isCanTouchMove = typedArray.getBoolean(R.styleable.CarouselLayout_enable_touch, isCanTouchMove)
            START_DELAY_TIME = typedArray.getInt(R.styleable.CarouselLayout_start_delay, START_DELAY_TIME)
            AUTO_DELAY_TIME = typedArray.getInt(R.styleable.CarouselLayout_duration, AUTO_DELAY_TIME)

            typedArray.recycle()
        }

        val runnable = Runnable {
            if (isAutoPlay) {
                autoPlay()
            }
        }

        post(runnable)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = 0
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0) // 测量子View的宽高
            width = child.measuredWidth
            height = child.measuredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val middle = middleIndex
        var left = -width * middle

        for (index in 0 until count) {
            val child = getChildAt(index)
            val lp = child.layoutParams as MarginLayoutParams

            val height = child.measuredHeight + lp.topMargin + lp.bottomMargin
            val width = child.measuredWidth
            child.layout(left, 0, left + width, height)
            child.translationX = 0f
            left += width
        }

        if (once) {
            index = middle
            once = false
        }
    }

    // 绘制完子View后 绘制点数
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        // 绘制点
        val dotSize = unSelected.width

        val gapR = 10
        val left = width

        for (i in childCount-1 downTo 0 step 1) {
            canvas.drawBitmap(
                if (i == index) selected else unSelected,
                (left - (childCount - i) * (gapR + dotSize)).toFloat(),
                (height - dotSize - 20).toFloat(),
                bitmapPaint
            )
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val canTouchMove =
            isCanTouchMove && (mAutoAlignAnimator == null || !mAutoAlignAnimator!!.isRunning) && childCount > 1 // 自动对齐动画执行中或当前仅一个元素则禁止滑动

        if (canTouchMove && isAutoPlay()) {
            isAutoPlay = false
            endAutoPlay()
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchX = event.x
            MotionEvent.ACTION_MOVE -> {
                val currX = event.x
                if (canTouchMove) {
                    setChildrenTranslationX(currX - touchX)
                }
            }
            MotionEvent.ACTION_UP -> {
                if (Math.abs(lastX - touchX) < MIN_TOUCHSLOP_DISTANCE) {
                    callOnClickView(middleView)
                } else if (canTouchMove) {
                    animeToEnd(lastX - touchX)
                }
                if (isAutoPlay()) {
                    isAutoPlay = true
                }
            }
            MotionEvent.ACTION_CANCEL -> if (isAutoPlay()) {
                isAutoPlay = true
            }
        }
        lastX = event.x
        return true
    }

    private fun exchange(offDistanceX: Float) {
        val childCount = childCount
        if (offDistanceX > 0) {
            val v = getChildAt(childCount - 1)
            removeView(v)
            addView(v, 0)
            index = (index - 1 + childCount) % childCount
        } else if (offDistanceX < 0) {
            val v = getChildAt(0)
            removeView(v)
            addView(v)
            index = (index + 1 + childCount) % childCount
        }
        onScrollToEnd(index)
        requestLayout()
    }

    private fun animeToEnd(start: Float) {
        if (Math.abs(start) < MIN_TOUCHSLOP_DISTANCE) {
            return
        }
        val end = (if (start > 0) width else -width).toFloat()

        mAutoAlignAnimator = ValueAnimator.ofFloat(start, end)
        mAutoAlignAnimator!!.duration = ANIM_END_TIME.toLong()
        mAutoAlignAnimator!!.addUpdateListener { animation ->
            val translationX = animation.animatedValue as Float
            setChildrenTranslationX(translationX)
        }

        mAutoAlignAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                exchange(end)
                restartAutoPlay()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        mAutoAlignAnimator!!.start()
    }

    private fun setChildrenTranslationX(translationx: Float) {
        if (Math.abs(translationx) < MIN_TOUCHSLOP_DISTANCE) {
            return
        }
        for (index in 0 until childCount) {
            getChildAt(index).translationX = translationx
        }

        onScrollTo(translationx, Math.floor((100 * translationx / width).toDouble()).toInt())
    }

    private fun isAutoPlay(): Boolean {
        return mAutoPlayAnimator != null || isAutoPlay
    }

    fun autoPlay() {
        if (!isAutoPlay || childCount <= 1) {
            return
        }

        mAutoPlayAnimator = ValueAnimator.ofInt(0, -width)
        mAutoPlayAnimator!!.startDelay = START_DELAY_TIME.toLong()
        mAutoPlayAnimator!!.duration = AUTO_DELAY_TIME.toLong()
        mAutoPlayAnimator!!.addUpdateListener { animation ->
            val animated = animation.animatedValue as Int
            setChildrenTranslationX(animated.toFloat())
        }
        mAutoPlayAnimator!!.addListener(object : Animator.AnimatorListener {
            var start = 0L
            var end = 0L
            override fun onAnimationStart(animation: Animator) {
                start = System.currentTimeMillis()
            }

            override fun onAnimationEnd(animation: Animator) {
                end=System.currentTimeMillis()
                if (isAutoPlay) {
                    exchange((-width).toFloat())
                    restartAutoPlay()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        mAutoPlayAnimator!!.start()
    }

    private fun restartAutoPlay() {
        endAutoPlay()
        if (mAutoPlayAnimator != null && !mAutoPlayAnimator!!.isStarted) {
            mAutoPlayAnimator!!.start()
        }
    }

    private fun endAutoPlay() {
        if (mAutoPlayAnimator != null && mAutoPlayAnimator!!.isRunning) {
            mAutoPlayAnimator!!.end()
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    fun setOnScrollListener(listener: OnScrollListener?) {
        if (listener != null) {
            this.listener = listener
        }
    }

    /*
    * distanceX: 水平偏移量
    * percent: 当前的进度百分比
    * index: 当前显示的元素index
    * */
    fun onScrollTo(distanceX: Float, percent: Int) {
        if (listener != null) {
            listener!!.onScrollTo(distanceX, percent, index)
        }
    }

    /*
    * index: 选中的新index
    * */
    private fun onScrollToEnd(index: Int) {
        if (listener != null) {
            listener!!.onScrollToEnd(index)
        }
    }

    private fun callOnClickView(view: View) {
        if (listener != null) {
            listener!!.callOnClick(index, view)
        }
    }

    interface OnScrollListener {
        fun onScrollToEnd(index: Int)
        fun onScrollTo(distanceX: Float, percent: Int, index: Int)
        fun callOnClick(index: Int, view: View)
    }

    companion object {

        private val ANIM_END_TIME = 300
    }

}
