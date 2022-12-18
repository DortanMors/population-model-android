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
import ru.ssau.population.model.PopulationStats
import ru.ssau.population.model.PopulationState
import ru.ssau.population.model.PopulationStateImpl

class PopulationLifecycleProcessorImpl : PopulationsLifecycleProcessor {

    private var populations: List<PopulationStats> = listOf()

    /**
     * Время между шагами расчёта в днях
     */
    private val timeStep: Long = Defaults.timeStep

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
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
                        t = listOf(0), // начальный момент времени (нулевой)
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
                removeFirst()
            }
            add(nextT)
        }
        val newtCountsValues = currentState.y.toMutableList().apply {
            if (size >= maxPointsAtAxis) {
                removeFirst()
            }
            add(nextTimeCounts)
        }
        return ChartState(
            t = newTimes,
            y = newtCountsValues,
        )
    }

    private fun calculateNextCounts(
        currentPopulationsStates: List<PopulationState>,
    ): List<Long> {
        currentPopulationsStates[0].population
        val result = listOf<Long>(0) // todo реализовать расчёт следующей численности популяции
        return result
    }
}
