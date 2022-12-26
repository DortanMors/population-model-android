package ru.ssau.population

import ru.ssau.population.model.PopulationParameters
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl

object Defaults {
    const val timeStep: Double = 0.1 // время между шагами расчёта в днях
    const val delay: Long = 1  // задержка отрисовки в миллисекундах
    var maxPointsAtAxis: Int = 50000 // максимальное количество точек на оси в одно время

    private val producerPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = 0.01,
            attackFactor = 0.0,
            defenseFactor = 1.0,
            nutrition = 1.0,
            hungerFactor = 1.0,
        ),
        count = 100.0,
    )

    private val predatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.01,
            attackFactor = 0.0001,
            defenseFactor = 1.0,
            nutrition = 1.0,
            hungerFactor = 1.0,
        ),
        count = 200.0,
    )

    private val apexPredatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            selfReproductionFactor = -0.0,
            attackFactor = 0.01,
            defenseFactor = 10000.0,
            nutrition = 1.0,
            hungerFactor = 5.0,
        ),
        count = 0.0,
    )

    val populations = listOf(
        producerPopulationState,
        predatorPopulationState,
        apexPredatorPopulationState,
    )
}
