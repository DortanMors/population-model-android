package ru.ssau.population.model

interface PopulationStats {
    val selfReproductionFactor: Double
    val attackFactor: Double
    val defenseFactor: Double
    val nutrition: Double
    val hungerFactor: Double
}
