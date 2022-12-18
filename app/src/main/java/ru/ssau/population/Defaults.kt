package ru.ssau.population

import ru.ssau.population.model.PopulationParameters
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl

object Defaults {
    const val timeStep: Long = 1 // время между шагами расчёта в днях
    const val delay: Long = 100  // задержка отрисовки в миллисекундах
    const val maxPointsAtAxis: Long = 100 // максимальное количество точек на оси в одно время

    private val producerPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = 0.5,
            attackFactor = 0.0,
            defenseFactor = 10.0,
            nutrition = 1.0,
            hungerFactor = 0.0,
        ),
        count = 100,
    )

    private val predatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.1,
            attackFactor = 0.1,
            defenseFactor = 0.0001,
            nutrition = 3.0,
            hungerFactor = 3.0,
        ),
        count = 10,
    )

    private val apexPredatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.1,
            attackFactor = 0.1,
            defenseFactor = 10000.0,
            nutrition = 1.0,
            hungerFactor = 5.0,
        ),
        count = 10,
    )

    val populations = listOf(
        producerPopulationState,
        predatorPopulationState,
        apexPredatorPopulationState,
    )
}
