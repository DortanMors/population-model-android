package ru.ssau.population.ui.screen

import androidx.lifecycle.ViewModel
import ru.ssau.population.Defaults
import ru.ssau.population.domain.PopulationsLifecycleProcessor
import ru.ssau.population.model.LifecycleInit
import ru.ssau.population.model.PopulationState

class MainViewModel : ViewModel() {
    lateinit var processor: PopulationsLifecycleProcessor // todo init this shit

    private var processorInit = LifecycleInit(
        populationsStates = listOf(
            Defaults.predatorPopulationState,
            Defaults.producerPopulationState,
            Defaults.apexPredatorPopulationState,
        ),
    )

    fun setProcessorInit(
        a: Double,
    ) {
        val populationsStates: List<PopulationState> = listOf() // начальные состояния для
        processorInit = LifecycleInit(
            populationsStates = populationsStates,
        )
    }

    fun getProcessorInit(): LifecycleInit = processorInit // todo not pretty, use flow

    fun start() {
        processor.start(processorInit)
    }

    fun resume() {
        processor.start()
    }

    fun pause() {
        processor.pause()
    }
}