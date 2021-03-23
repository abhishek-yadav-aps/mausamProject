package com.abhitom.mausamproject.ui.weather.future.list

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abhitom.mausamproject.R
import com.abhitom.mausamproject.data.database.entity.DailyItem

private const val STROKE_WIDTH = 10f
private const val PATH_CORNER_RADIUS_IN_DP = 30
private const val CHILD_HEADER_OR_FOOTER_HEIGHT_IN_DP = 220

class FutureWeatherListCurlyItemDecorator(futureWeatherList: List<DailyItem>, context: Context) :
    RecyclerView.ItemDecoration() {

    private val appContext=context.applicationContext

    private val drawPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = STROKE_WIDTH
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(appContext, R.color.black)
        pathEffect = CornerPathEffect(PATH_CORNER_RADIUS_IN_DP.dpToPx)
    }

    private val normalizedFutureWeatherList = normalizeFutureWeatherValues(futureWeatherList)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val path = Path()
        var newPath = true

        for (childIndex in 0 until parent.childCount) {
            val childView = parent.getChildAt(childIndex)
            val dataIndex = parent.getChildAdapterPosition(childView)
            val childViewHeight = childView.height
            val halfChildViewWidth = (childView.right.toFloat() - childView.left.toFloat()) / 2

            if (newPath) {
                val previousDataIndex = if (dataIndex > 0) (dataIndex - 1) else 0
                val moveToYPosition = calculateYValue(previousDataIndex, childViewHeight)
                path.moveTo(childView.left.toFloat() - halfChildViewWidth, moveToYPosition)
                newPath = false
            }

            path.lineTo(childView.right.toFloat() - halfChildViewWidth, calculateYValue(dataIndex, childViewHeight))

            if (childIndex == parent.childCount - 1) {
                drawPathForNextChildView(
                    dataIndex + 1,
                    childView.right.toFloat(),
                    path,
                    halfChildViewWidth,
                    childViewHeight
                )
            }
        }

        canvas.drawPath(path, drawPaint)
    }

    private fun drawPathForNextChildView(
        nextChildViewDataIndex: Int,
        nextChildViewMiddleXValue: Float,
        path: Path,
        halfChildViewWidth: Float,
        childViewHeight: Int
    ) {
        if (nextChildViewDataIndex >= normalizedFutureWeatherList.size) {
            handleNextAfterLastChildView(nextChildViewMiddleXValue, path, childViewHeight)
        } else {
            val nextChildViewEndXValue = nextChildViewMiddleXValue + halfChildViewWidth
            path.lineTo(nextChildViewEndXValue, calculateYValue(nextChildViewDataIndex, childViewHeight))
        }
    }

    private fun handleNextAfterLastChildView(
        lastXValue: Float,
        path: Path,
        childViewHeight: Int
    ) {
        path.lineTo(lastXValue, calculateYValue(normalizedFutureWeatherList.size - 1, childViewHeight))
    }

    private fun calculateYValue(dataIndex: Int, childViewHeight: Int): Float {
        val graphHeight = childViewHeight - (CHILD_HEADER_OR_FOOTER_HEIGHT_IN_DP * 2).dpToPx
        val graphStartHeightDelta = (CHILD_HEADER_OR_FOOTER_HEIGHT_IN_DP+20).dpToPx
        return ((1 - normalizedFutureWeatherList[dataIndex]) * graphHeight + graphStartHeightDelta).toFloat()
    }

    private fun normalizeFutureWeatherValues(futureWeatherList: List<DailyItem>): List<Double> {
        val minDayTemp = futureWeatherList.minByOrNull { it.temp?.max!! }
        val maxDayTemp = futureWeatherList.maxByOrNull { it.temp?.max!! }

        if (minDayTemp == null || maxDayTemp == null) {
            return emptyList()
        }

        if (minDayTemp.temp?.max!! >= maxDayTemp.temp?.max!!) {
            return futureWeatherList.map { 0.5 }
        }

        val range = maxDayTemp.temp.max - minDayTemp.temp.max
        return futureWeatherList.map {
            val relativeValue = it.temp?.max!! - minDayTemp.temp.max
            return@map (relativeValue / range)
        }
    }
}

private val Int.dpToPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)