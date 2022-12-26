package ru.ssau.population.model

data class ChartState(
    val t: List<Float>,        // моменты времени
    val y: List<List<Float>>,  // список кривых (у каждой кривой список точек)
)
