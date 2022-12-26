package ru.ssau.population.ui.screen

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ru.ssau.population.databinding.ViewMicroPlotBinding
import ru.ssau.population.model.ChartState


class AbstractPlotView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
): FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: ViewMicroPlotBinding = ViewMicroPlotBinding.inflate(LayoutInflater.from(context), this, true)
    private val populationsColors = arrayOf(
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
    )

    fun setState(state: ChartState) {
        if (state.t.isEmpty()) {
            return
        }
        val curves = state.y.mapIndexed { curveIndex, curveData ->
            LineDataSet(
                state.t.mapIndexed { timeIndex, time ->
                    Entry(time, curveData[timeIndex])
                },
                "Population$curveIndex",
            ).apply {
                color = populationsColors[curveIndex % colors.size]
            }
        }
        binding.lineChart.data = LineData(curves)
        binding.lineChart.invalidate()
    }
}
