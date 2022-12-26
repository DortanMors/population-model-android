package ru.ssau.population.model

data class PopulationStateImpl(
    override val population: PopulationStats,
    override val count: Float,
) : PopulationState
