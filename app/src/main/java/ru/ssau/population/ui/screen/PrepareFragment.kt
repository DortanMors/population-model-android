package ru.ssau.population.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.ssau.population.R
import ru.ssau.population.databinding.FragmentPrepareBinding
import ru.ssau.population.model.PopulationParameters
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
            val populations: List<PopulationParameters> = listOf(binding.producer, binding.predator, binding.apexPredator).map {
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
                PopulationParameters(alpha, beta, t, omega, i)
            }

        }
        // get/set parameters view
    }

    companion object {
        fun newInstance() = PrepareFragment()
    }
}
