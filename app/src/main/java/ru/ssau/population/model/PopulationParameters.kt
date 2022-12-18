package ru.ssau.population.model

data class PopulationParameters(
    override val selfReproductionFactor: Double,
    override val attackFactor: Double,
    override val defenseFactor: Double,
    override val nutrition: Double,
    override val hungerFactor: Double,
) : PopulationStats
