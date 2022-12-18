package ru.ssau.population.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import ru.ssau.population.R
import ru.ssau.population.databinding.FragmentPrepareBinding
import ru.ssau.population.model.*
import java.lang.Exception

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
        binding.producer.alpha
        binding.buttonNext.setOnClickListener {
            val populations: List<PopulationState> = listOf(binding.producer, binding.predator, binding.apexPredator).map {

                val count: Long
                try {
                    count = it.count.text.toString().toLong()
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
                PopulationStateImpl(parameters, count)
            }
            viewModel.setProcessorInit(populations)
        }
    }

    companion object {
        fun newInstance() = PrepareFragment()
    }
}
