package fr.epf.min1.flags

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var drawPath: Path = Path()
    private var drawPaint: Paint = Paint()
    private var canvasPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var drawColor = Color.WHITE
    private var canvas: Canvas? = null
    private var canvasBitmap: Bitmap? = null
    private var brushSize: Float = 10f

    init {
        setupDrawing()
    }

    private fun setupDrawing() {
        drawPaint.color = drawColor
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = brushSize
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap!!)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                drawPath.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                canvas?.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun setColor(newColor: String) {
        invalidate()
        drawColor = Color.parseColor(newColor)
        drawPaint.color = drawColor
    }

    fun setBrushSize(newSize: Float) {
        brushSize = newSize
        drawPaint.strokeWidth = brushSize
    }
    fun clearCanvas() {
        canvas?.drawColor(Color.WHITE)
        invalidate()
    }
}
