package ru.ssau.population.model

data class ChartState(
    val x: List<Int>,        // моменты времени
    val y: List<List<Int>>,  // список кривых (у каждой кривой список точек)
)
