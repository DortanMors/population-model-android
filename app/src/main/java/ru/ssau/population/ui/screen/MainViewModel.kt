package ru.ssau.population.ui.screen

import androidx.lifecycle.ViewModel
import ru.ssau.population.domain.PopulationsLifecycleProcessor
import ru.ssau.population.model.LifecycleInit

class MainViewModel : ViewModel() {
    lateinit var processor: PopulationsLifecycleProcessor // todo init this shit

    private var processorInit = LifecycleInit(
        0.0,
        // todo etc.
    ) // todo default value

    fun setProcessorInit(
        a: Double,
    ) {
        processorInit = LifecycleInit(
            a = a,
            // todo etc.
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