package ru.ssau.population.model

// одна популяция в конкретный момент времени
interface PopulationState {
    val population: Population
    val count: Int
}