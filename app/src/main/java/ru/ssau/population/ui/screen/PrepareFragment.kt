package ru.ssau.population.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.ssau.population.R
import ru.ssau.population.databinding.FragmentPrepareBinding
import ru.ssau.population.model.*
import java.lang.Exception
import ru.ssau.population.Defaults

class PrepareFragment : Fragment() {

    private lateinit var binding: FragmentPrepareBinding

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrepareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.maxPoints.setText(Defaults.maxPointsAtAxis.toString())

        binding.producer.count.setText(Defaults.populations[0].count.toString())
        binding.producer.alpha.setText(Defaults.populations[0].population.selfReproductionFactor.toString())
        binding.producer.beta.setText(Defaults.populations[0].population.attackFactor.toString())
        binding.producer.t.setText(Defaults.populations[0].population.defenseFactor.toString())
        binding.producer.omega.setText(Defaults.populations[0].population.nutrition.toString())
        binding.producer.i.setText(Defaults.populations[0].population.hungerFactor.toString())

        binding.predator.count.setText(Defaults.populations[1].count.toString())
        binding.predator.alpha.setText(Defaults.populations[1].population.selfReproductionFactor.toString())
        binding.predator.beta.setText(Defaults.populations[1].population.attackFactor.toString())
        binding.predator.t.setText(Defaults.populations[1].population.defenseFactor.toString())
        binding.predator.omega.setText(Defaults.populations[1].population.nutrition.toString())
        binding.predator.i.setText(Defaults.populations[1].population.hungerFactor.toString())

        binding.buttonNext.setOnClickListener {
            val maxPoints: Int =
                try {
                    binding.maxPoints.text.toString().toInt()
                } catch (exception: Exception) {
                    binding.maxPointsLayout.error = getString(R.string.error_value_int)
                    Toast.makeText(requireContext(), R.string.error_toast, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            Defaults.maxPointsAtAxis = maxPoints

            val populations: List<PopulationState> = listOf(binding.producer, binding.predator).map {

                val count: Double
                try {
                    count = it.count.text.toString().toDouble()
                } catch (exception: Exception) {
                    it.countLayout.error = getString(R.string.error_value)
                    Toast.makeText(requireContext(), R.string.error_toast, Toast.LENGTH_LONG).show()

                    return@setOnClickListener
                }

                val (alpha, beta, t, omega, i) = listOf(
                    it.alphaLayout to it.alpha,
                    it.betaLayout to it.beta,
                    it.tLayout to it.t,
                    it.omegaLayout to it.omega,
                    it.iLayout to it.i,
                ).map { (layout, input) ->
                    val value: Double
                    try {
                        value = input.text.toString().toDouble()
                    } catch (exception: Exception) {
                        layout.error = getString(R.string.error_value)
                        return@setOnClickListener
                    }
                    value
                }

                val parameters = PopulationParameters(alpha, beta, t, omega, i)
                PopulationStateImpl(parameters, count.toFloat())
            }
            viewModel.setProcessorInit(listOf(populations[0], populations[1]))
            navigateToChartFragment()
        }
    }

    private fun navigateToChartFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, ChartFragment.newInstance())
            .addToBackStack(ChartFragment::class.simpleName)
            .commit()
    }

    companion object {
        fun newInstance() = PrepareFragment()
    }
}
