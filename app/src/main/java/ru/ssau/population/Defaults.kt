package ru.ssau.population

import ru.ssau.population.model.PopulationParameters
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl

object Defaults {
    const val timeStep: Long = 1 // время между шагами расчёта в днях
    const val delay: Long = 100  // задержка отрисовки в миллисекундах
    const val maxPointsAtAxis: Long = 100 // максимальное количество точек на оси в одно время

    val producerPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            aCoefficient = 0.0001,
            bCoefficient = 0.0001,
        ),
        count = 1000,
    )

    val predatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            aCoefficient = 0.0001,
            bCoefficient = 0.0001,
        ),
        count = 1000,
    )

    val apexPredatorPopulationState: PopulationState = PopulationStateImpl(
        PopulationParameters(
            aCoefficient = 0.0001,
            bCoefficient = 0.0001,
        ),
        count = 1000,
    )
}
