package ru.ssau.population.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.ssau.population.Defaults
import ru.ssau.population.model.ChartState
import ru.ssau.population.model.LifecycleInit
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl
import ru.ssau.population.model.PopulationStats

class PopulationLifecycleProcessorImpl : PopulationsLifecycleProcessor {

    private var populations: List<PopulationStats> = listOf()

    /**
     * Время между шагами расчёта в днях
     */
    private val timeStep: Float = Defaults.timeStep

    /**
     * Задержка отрисовки в миллисекундах
     */
    private val delay: Long = Defaults.delay

    /**
     * Максимальное количество точек на оси одновременно
     */
    private val maxPointsAtAxis = Defaults.maxPointsAtAxis // максимальное количество точек на оси одновременно

    /**
     * Внутренний поток состояний системы
     */
    private val _chartStateFlow = MutableStateFlow(ChartState(emptyList(), emptyList()))

    /**
     * Поток состояний системы, разница между двумя последовательными состояниями = timeStep
     */
    override val chartStateFlow: Flow<ChartState> = _chartStateFlow

    /**
     * Область действия корутины расчёта
     */
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * Текущая работа по непрерывному вычислению
     */
    private var job: Job? = null

    /**
     * Запуск непрерывного вычисления
     * @param init начальные параметры, чтобы начать расчёт заново. Если null, то возобновить приостановленный расчёт
     */
    override fun start(init: LifecycleInit?) {
        job = coroutineScope.launch {
            init?.let { initialChartState ->
                populations = initialChartState.populationsStates.map { it.population }
                // начальные численности популяций (в списке только численность за 0 период)
                val counts = initialChartState.populationsStates.map { listOf(it.count) }
                _chartStateFlow.emit(
                    ChartState(
                        t = listOf(0.0f), // начальный момент времени (нулевой)
                        y = counts,
                    )
                )
            }
            chartStateFlow.collect { currentState ->
                delay(delay)
                val newState = recalculateState(currentState)
                _chartStateFlow.emit(newState)
            }
        }
    }

    /**
     * Отмена непрерывного вычисления
     */
    override fun pause() {
        job?.cancel()
    }

    private fun recalculateState(currentState: ChartState): ChartState {
        val nextT = currentState.t.last() + timeStep // расчёт следующего момента времени
        val currentCounts = currentState.y.map { it.last() } // список последних численностей популяций
        val currentPopulationsStates = currentCounts.mapIndexed { index, count ->
            PopulationStateImpl(
                population = populations[index],
                count = count
            )
        }
        val nextTimeCounts = calculateNextCounts(currentPopulationsStates) // расчёт следующих численностей популяций
        // список моментов времени
        val newTimes = currentState.t.toMutableList().apply {
            if (size >= maxPointsAtAxis) {
                removeRange(0, size / 2)
            }
            add(nextT)
        }
        val newtCountsValues = currentState.y.mapIndexed { index, curve ->
            curve.toMutableList().apply {
                if (size >= maxPointsAtAxis) {
                    removeRange(0, size / 2)
                }
                add(nextTimeCounts[index])
            }.toList()
        }
        return ChartState(
            t = newTimes,
            y = newtCountsValues,
        )
    }

    private fun calculateNextCounts(
        currentPopulationsStates: List<PopulationState>,
    ): List<Float> {
        val result = currentPopulationsStates.map { currentPopulation ->
            val reproduceCount = currentPopulation.population.selfReproductionFactor * currentPopulation.count
            val battleLosses = // внутри популяции не бьёмся
                currentPopulationsStates.sumOf { otherPopulation ->
                    if (otherPopulation != currentPopulation) {
                        otherPopulation.population.attackFactor * currentPopulation.count * otherPopulation.count
                    } else {
                        0.0 // внутри популяции не бьёмся
                    }
                }
            val battleIncrease = // внутри популяции не бьёмся
                currentPopulationsStates.sumOf { otherPopulation ->
                    if (otherPopulation != currentPopulation) {
                        currentPopulation.population.attackFactor * currentPopulation.count * otherPopulation.count
                    } else {
                        0.0 // внутри популяции не бьёмся
                    }
                }
            val growth = reproduceCount + battleIncrease - battleLosses
            return@map (currentPopulation.count + growth * timeStep).toFloat() // отбрасываем дробную часть численности, так как полтора землекопа до двух не округляются
                .takeIf { it > 0 } ?: 0.0F // если число стало отрицательным, то считаем, что популяция вымерла
        }
        return result
    }
}

fun MutableList<Float>.removeRange(fromIndex: Int, toIndex: Int) {
    for (i in fromIndex until toIndex) {
        removeAt(fromIndex)
    }
}
