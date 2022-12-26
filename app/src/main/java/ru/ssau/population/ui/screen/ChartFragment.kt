package ru.ssau.population.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
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
                    binding.chart.setState(chartState)
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
