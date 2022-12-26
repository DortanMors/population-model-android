package ru.ssau.population.ui.screen

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.farshidroohi.ChartEntity
import ru.ssau.population.databinding.ViewMicroPlotBinding
import ru.ssau.population.model.ChartState


class AbstractPlotView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
): FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: ViewMicroPlotBinding = ViewMicroPlotBinding.inflate(LayoutInflater.from(context), this, true)
    private val colors = arrayOf(
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
    )

    fun setState(state: ChartState) {
        if (state.t.isEmpty()) {
            return
        }
        binding.lineChart.setList(
            state.y.mapIndexed { index, populationCurveValues ->
                ChartEntity(colors[index % colors.size], populationCurveValues.toFloatArray())
            }
        )
    }

}
