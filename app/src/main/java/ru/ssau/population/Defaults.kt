package ru.ssau.population

import ru.ssau.population.model.PopulationParameters
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl

object Defaults {
    const val timeStep: Long = 1 // время между шагами расчёта в днях
    const val delay: Long = 1000  // задержка отрисовки в миллисекундах
    var maxPointsAtAxis: Int = 50 // максимальное количество точек на оси в одно время

    private val producerPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = 0.5,
            attackFactor = 0.0,
            defenseFactor = 10.0,
            nutrition = 1.0,
            hungerFactor = 1.0,
        ),
        count = 100,
    )

    private val predatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.1,
            attackFactor = 0.05,
            defenseFactor = 5.0,
            nutrition = 3.0,
            hungerFactor = 3.0,
        ),
        count = 10,
    )

    private val apexPredatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.2,
            attackFactor = 0.01,
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
