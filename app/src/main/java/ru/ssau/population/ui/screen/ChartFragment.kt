package ru.ssau.population.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import ru.ssau.population.databinding.FragmentChartBinding

class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            lifecycleScope.launch {
                viewModel.populationsStates.collect { chartState ->
                    val curves = chartState.y.map { curveData ->
                        LineDataSet(
                            chartState.t.zip(curveData).map { (t, y) -> Entry(t.toFloat(), y.toFloat()) },
                            "Population", // todo use real name
                        )
                    }
                    binding.chart.data = LineData(curves)
                }
            }
        }
        binding.startPause.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.start()
            } else {
                viewModel.pause()
            }
        }
    }

    companion object {
        fun newInstance() = ChartFragment()
    }
}
