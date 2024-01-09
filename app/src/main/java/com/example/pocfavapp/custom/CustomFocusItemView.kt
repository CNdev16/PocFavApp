package com.example.pocfavapp.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pocfavapp.R

class CustomFocusItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(Rect(0, 0, width, height), Paint().apply {
            style = Paint.Style.FILL
            color = Color.BLACK
            alpha = 80
        })

        val corner = context.resources.getDimensionPixelOffset(R.dimen.corner).toFloat()

        canvas.drawRoundRect(RectF(22f, 22f, 1058f, 286f), corner, corner, Paint().apply {
            color = Color.TRANSPARENT
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        })

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        val filterBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
        }
        canvas.drawBitmap(filterBitmap, 0f, 0f, paint)
    }
}
