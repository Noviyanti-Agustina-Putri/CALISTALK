package com.example.calistalk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TracingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val drawPath = Path()

    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 20f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(drawPath, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath.moveTo(x, y)
            }

            MotionEvent.ACTION_MOVE -> {
                drawPath.lineTo(x, y)
            }
        }

        invalidate()
        return true
    }

    fun clearCanvas() {
        drawPath.reset()
        invalidate()
    }
}