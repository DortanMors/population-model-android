package ru.ssau.population.model

data class PopulationStateImpl(
    override val population: Population,
    override val count: Long,
) : PopulationState
