package ru.ssau.population.domain

import kotlinx.coroutines.flow.Flow
import ru.ssau.population.model.ChartState
import ru.ssau.population.model.LifecycleInit

interface PopulationsLifecycleProcessor {
    val chartStateFlow: Flow<ChartState>
    fun start(init: LifecycleInit? = null)
    fun pause()
}
