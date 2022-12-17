package ru.ssau.population.model

data class PopulationParameters(
    override val aCoefficient: Double,
    override val bCoefficient: Double,
) : Population
