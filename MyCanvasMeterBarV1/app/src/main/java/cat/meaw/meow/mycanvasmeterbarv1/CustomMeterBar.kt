package cat.meaw.meow.mycanvasmeterbarv1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat



class CustomMeterBar @JvmOverloads constructor(context: Context, attrs: AttributeSet):
    View(context, attrs) {

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val progressInsidePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val pathPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val testPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
    }
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 30f
        isAntiAlias = true
    }


    private val rect = RectF()
    private val rectInline = RectF()
    private val startAngle = 180f
    private val maxAngle = 360f
    private val maxProgress = 100

    private var diameter = 0f
    private var angle = 0f

    private val linePath = Path()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var childWidth = 0
        if(widthSpecMode == MeasureSpec.AT_MOST){
            childWidth = MeasureSpec.getSize(widthMeasureSpec);
            Log.d("DSS", "AT_MOST = $childWidth")
        }
        else if(widthSpecMode == MeasureSpec.EXACTLY){
            childWidth = MeasureSpec.getSize(widthMeasureSpec);
            Log.d("DSS", "EXACTLY = $childWidth")
        }
        else {

        }
        val size = Math.max(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)

    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        diameter = Math.min(width, height).toFloat()
        Log.d("DSSL", "diameter = $diameter")
        updateRect()

    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)



        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        drawCircle(180f, canvas, backgroundPaint)
        drawInsideCircle(180f, canvas, backgroundPaint)
        drawInsideCircle(angle, canvas, progressInsidePaint)
        drawCircle(angle, canvas, progressPaint)

       // pathPaint.setShadowLayer(2.0f, 2.0f, -2.0f, Color.WHITE)
//        canvas.save()
//        canvas.rotate(angle - 5, diameter / 2, diameter / 2)
//        canvas.drawPath(linePath, pathPaint)
//        canvas.restore()

        canvas.save()
        canvas.rotate(angle - 5, diameter / 2, diameter / 2)
        drawResourceIcon(canvas)
        drawResourceIconCore(canvas)
        canvas.restore()


        canvas.save()
        canvas.rotate(15f, (diameter / 2), (diameter / 2))
        for (i in 1..6) {
            drawResourceLine(canvas)
            canvas.drawText("--", 100f+(backgroundPaint.strokeWidth/2), (canvas.getHeight() / 2).toFloat()+(i*2), textPaint)
            canvas.rotate(30f, (diameter / 2), (diameter / 2))
        }
        canvas.restore()
        //canvas.drawText("Hello", 100f, (canvas.getHeight() / 2).toFloat(), textPaint)

//        val xPos: Float = (canvas.width - textPaint.measureText("Hello")) / 2
//        val yPos: Float = ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2))
//        canvas.drawText("Hello", xPos, yPos, textPaint)

    }
    private fun drawResourceIcon(canvas: Canvas) {
        var startX = (diameter) /2
        var startY = (diameter) /2
        var stopX = (diameter - 15f) /2 - ((diameter/2)-15f)+20
        var stopY = (diameter - 15f) /2

        VectorDrawableCompat.create(
            context.resources,
            R.drawable.line_bg,
            null
        )?.apply {
            setBounds(
                stopX.toInt(),
                startX.toInt() - 25,
                startX.toInt(),
                startX.toInt()
            )
            //setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
            draw(canvas)
        }
    }
    private fun drawResourceIconCore(canvas: Canvas) {
        var startX = (diameter) /2
        var startY = (diameter) /2
        var stopX = (diameter - 15f) /2 - ((diameter/2)-15f)+20
        var stopY = (diameter - 15f) /2
        Log.d("DSSL", "startX:$startX startY:$startY stopX:$stopX stopY:$stopY")
        VectorDrawableCompat.create(
            context.resources,
            R.drawable.line_bg,
            null
        )?.apply {
            setBounds(
                stopX.toInt(),
                startX.toInt() - 25,
                stopX.toInt() * 2,
                startX.toInt()
            )
            setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN)
            draw(canvas)
        }
    }
    private fun drawResourceLine(canvas: Canvas) {
        var startX = (diameter) /2
        var startY = (diameter) /2
        var stopX = (diameter - 15f) /2 - ((diameter/2)-15f)+20
        var stopY = (diameter - 15f) /2
        VectorDrawableCompat.create(
            context.resources,
            R.drawable.line_bg,
            null
        )?.apply {
            setBounds(
                stopX.toInt() - 20,
                startX.toInt() - 35,
                stopX.toInt() + 35,
                startX.toInt()
            )
            //setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN)
            draw(canvas)
        }
    }

    private fun drawCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }
    private fun drawInsideCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rectInline, startAngle, angle, false, paint)
    }
    private fun updateRect() {
        val strokeWidth = backgroundPaint.strokeWidth
        rect.set(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth)
        rectInline.set(
            strokeWidth * 3,
            strokeWidth * 3,
            (diameter - strokeWidth) - (strokeWidth * 2),
            (diameter - strokeWidth) - (strokeWidth * 2)
        )
        //rect.set(10f, 10f, 500f, 600f)

        var startX = (diameter) /2
        var startY = (diameter) /2
        var stopX = (diameter - strokeWidth) /2 - ((diameter/2)-strokeWidth)+20
        var stopY = (diameter - strokeWidth) /2
        linePath.moveTo(startX, startY)
        linePath.lineTo(stopX, stopY)

    }
    private fun calculateAngle(progress: Float) = maxAngle / maxProgress * progress

    fun setProgress(@FloatRange(from = 0.0, to = 100.0) progress: Float) {
        angle = calculateAngle(progress)
        invalidate()
    }


    fun setProgressColor(color: Int = Color.YELLOW) {
        progressPaint.color = color
        invalidate()
    }
    fun setProgressInsideColor(color: Int = Color.YELLOW) {
        progressInsidePaint.color =color
        invalidate()
    }

    fun setProgressBackgroundColor(color: Int) {
        backgroundPaint.color = color
        //pathPaint.color = Color.RED
        invalidate()
    }

    fun setProgressWidth(width: Float) {
        progressPaint.strokeWidth = width
        progressInsidePaint.strokeWidth = width
        backgroundPaint.strokeWidth = width
        //pathPaint.strokeWidth = width / 2
        updateRect()
        invalidate()
    }
}