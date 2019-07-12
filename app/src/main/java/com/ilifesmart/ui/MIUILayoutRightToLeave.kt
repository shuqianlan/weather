package com.ilifesmart.ui

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import com.ilifesmart.utils.DensityUtils

class MIUILayoutRightToLeave @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CustomSurfaceView(context, attrs, defStyleAttr){

    private lateinit var pathPaint: Paint
    private lateinit var textPaint: Paint
    private var touchX = 0f
    private var moveX  = 0f
    private var path:Path = Path()
    private val MAX_RIGHT_DISTANCE_WITHMARGIN = 150.1F // 最大拖拽距离
    private val MAX_RIGHT_DISTANCE = 150F // 最大拖拽距离(判断用,避免150.1 == 150.99999999999的情况)

    init {
        initialize(context)
    }

    private fun initialize(context: Context) {
        pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = DensityUtils.sp2px(context, 24f).toFloat()
            textAlign = Paint.Align.CENTER
        }
   }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        moveX = event.x
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchX = event.x
        }

        val distanceX = moveX - touchX

        if (event.action == MotionEvent.ACTION_MOVE) {
            val centerY = height/2.toFloat()
            val tmpStep = height/4.toFloat() // 拖拽显示区域高度
            with(path) {
                reset()
                if(distanceX >= 0) {
                    moveTo(0f, centerY-tmpStep)
                    quadTo(Math.min(distanceX, MAX_RIGHT_DISTANCE_WITHMARGIN), centerY, 0f, centerY+tmpStep) // 二阶贝塞尔
                } else {
                    moveTo(width.toFloat(), centerY-tmpStep)
                    quadTo(width+Math.max(distanceX, -MAX_RIGHT_DISTANCE_WITHMARGIN), centerY, width.toFloat(), centerY+tmpStep) // 二阶贝塞尔
                }
                close()
            }
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (distanceX >= MAX_RIGHT_DISTANCE) {
                (context as? Activity)?.finish()
            } else if(distanceX <= -MAX_RIGHT_DISTANCE) {
              // println("HAHA ============= TODO ")
            } else {
                path.reset()
            }
        }

        return true
    }

    override fun doDraw(canvas: Canvas, paint: Paint?) {
        canvas.drawColor(Color.WHITE)
        canvas.drawPath(path, pathPaint)
        val arrow = if ((moveX - touchX) > 0) " > " else " < "
        val centerX = if ((moveX - touchX) > 0) MAX_RIGHT_DISTANCE/2 - textPaint.measureText(arrow)/2 else width - (MAX_RIGHT_DISTANCE/2 - textPaint.measureText(arrow)/2)
        canvas.drawText(arrow, centerX, height/2.toFloat(), textPaint)
    }
}
