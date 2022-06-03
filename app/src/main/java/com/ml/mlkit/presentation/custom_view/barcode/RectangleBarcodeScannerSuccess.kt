package com.ml.mlkit.presentation.custom_view.barcode

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class RectangleBarcodeScannerSuccess @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val Int.toDp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    private val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private val qrScannerWidth = 250.toPx
    private val qrScannerHeight = 250.toPx


    private val verticalOffset = 0.4f
    private val horizontalOffset = 0.5f

    // Edges of QR scanner
    private var xAxisLeftEdge = 0f
    private var xAxisRightEdge = 0f
    private var yAxisTopEdge = 0f
    private var yAxisBottomEdge = 0f

    private val frameStrokeWidth = 3.toPx.toFloat()

    private val backgroundPaint = Paint().apply {
        setARGB(80, 0, 0, 0)
    }

    private val framePaint = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
        strokeWidth = frameStrokeWidth

        style = Paint.Style.STROKE
    }

    private lateinit var backgroundShape: Path
    private lateinit var qrScannerShape: Path

    private fun createBackgroundPath() = Path().apply {
        lineTo(right.toFloat(), 0f)
        lineTo(right.toFloat(), bottom.toFloat())
        lineTo(0f, bottom.toFloat())
        lineTo(0f, 0f)
        fillType = Path.FillType.EVEN_ODD
    }

    private fun createQrPath() = Path().apply {
        moveTo(xAxisLeftEdge, yAxisTopEdge)
        lineTo(xAxisRightEdge, yAxisTopEdge)
        lineTo(xAxisRightEdge, yAxisBottomEdge)
        lineTo(xAxisLeftEdge, yAxisBottomEdge)
        lineTo(xAxisLeftEdge, yAxisTopEdge)
        fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            xAxisLeftEdge = width * horizontalOffset - qrScannerWidth / 2f
            xAxisRightEdge = width * horizontalOffset + qrScannerWidth / 2f
            yAxisTopEdge = height * verticalOffset - qrScannerHeight / 2f
            yAxisBottomEdge = height * verticalOffset + qrScannerHeight / 2f

            backgroundShape = createBackgroundPath()
            qrScannerShape = createQrPath()
            backgroundShape.addPath(qrScannerShape)


            drawPath(backgroundShape, backgroundPaint)
            drawPath(qrScannerShape, framePaint)
        }
    }

}