package ru.ssau.population.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ru.ssau.population.domain.PopulationLifecycleProcessorImpl
import ru.ssau.population.domain.PopulationsLifecycleProcessor
import ru.ssau.population.model.ChartState
import ru.ssau.population.model.LifecycleInit
import ru.ssau.population.model.PopulationState

class MainViewModel : ViewModel() {
    private val processor: PopulationsLifecycleProcessor = PopulationLifecycleProcessorImpl()

    private var processorInit: LifecycleInit? = null

    val populationsStates: Flow<ChartState>
        get() = processor.chartStateFlow

    fun setProcessorInit(
        populationsStates: List<PopulationState>,
    ) {
        processorInit = LifecycleInit(
            populationsStates = populationsStates,
        )
    }

    fun start() {
        processor.start(processorInit)
        processorInit = null
    }

    fun pause() {
        processor.pause()
    }
}