package ru.ssau.population.model

data class ChartState(
    val t: List<Double>,        // моменты времени
    val y: List<List<Double>>,  // список кривых (у каждой кривой список точек)
)
