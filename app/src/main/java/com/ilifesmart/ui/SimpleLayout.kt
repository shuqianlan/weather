package com.ilifesmart.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class SimpleLayout @JvmOverloads constructor(
    context: Context,
    atributeSet: AttributeSet?=null,
    defStyleAttr:Int=0
): ViewGroup(context, atributeSet, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        var maxHeight = 0
        var width = 0

        println("childCount: $childCount")
        for(index in 0 until childCount) {
            val v = getChildAt(index)
            measureChild(v, widthMeasureSpec, heightMeasureSpec)
            measureChildWithMargins(v, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val childWidth = v.measuredWidth
            val childHeight= v.measuredHeight

            println("index: $index, childWidth: $childWidth, childHeight: $childHeight")

            maxHeight = max(height, childHeight)
            width += childWidth
        }

        println("setMeasuredDimension Width[EXACTLY] ${measureWidthMode == MeasureSpec.EXACTLY}")
        println("setMeasuredDimension Height[EXACTLY] ${measureHeightMode == MeasureSpec.EXACTLY}")

        println("measureWidth: $measureWidth")
        println("height: $height")

        setMeasuredDimension(
            if (measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if (measureHeightMode == MeasureSpec.EXACTLY) measureHeight else maxHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        println("onLayout: $childCount")

        for (index in 0 until childCount) {
            val view = getChildAt(index)
            val childWidth = view.measuredWidth
            val childHeight = view.measuredHeight

            println("onLayout index: $index, childWidth: $childWidth, childHeight: $childHeight")
            view.layout(left, 0, left+childWidth, childHeight)
            left+=childWidth
        }
    }

    // 默认实现仅包含layout_width/height
    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    // 默认实现仅包含layout_width/height
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    // 默认实现仅包含layout_width/height (wrap_content, wrap_content)
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    fun autoPlay() {
//        val animator = ObjectAnimator.ofInt(width).apply {
//            repeatCount = childCount
//            addUpdateListener {
//                it.animatedValue
//                for (index in 0 until childCount) {
//                    getChildAt(index).translationX = if (animatedValue is Float) animatedValue.toInt() else 0f
//                }
//            }
//        }

    }
}