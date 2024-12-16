package com.tweener.common._internal.kotlinextensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * @author Vivien Mahe
 * @since 23/07/2024
 */

/**
 * An extension function for Flow that conditionally switches to one of two provided flows based on a condition.
 *
 * @param condition A function that takes a value of type T and returns a Boolean.
 * If true, the flow provided by [ifTrue] is used; otherwise, the flow provided by [ifFalse] is used.
 * @param ifTrue A suspend function that returns a flow of type R. This flow is used when the [condition] is true.
 * @param ifFalse A suspend function that returns a flow of type R. This flow is used when the [condition] is false. Defaults to an empty flow.
 * @return A flow of type R that emits values from the flow provided by [ifTrue] when the [condition] is true,
 * or values from the flow provided by [ifFalse] when the [condition] is false.
 *
 * ```
 * fun main() = runBlocking {
 *     val flowA = flowOf(true, false, true) // Sample FlowA emitting Boolean values
 *     val flowB = flowOf("Hello", "World") // Sample FlowB emitting String values
 *     val flowC = flowOf("No", "Match") // Sample FlowC emitting String values for false condition
 *
 *     flowA.conditional(
 *         condition = { it },
 *         ifTrue = { flowB },
 *         ifFalse = { flowC }
 *     ).collect { valueB ->
 *         println(valueB)
 *     }
 * }
 * ```
 * */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<T>.conditional(
    condition: (T) -> Boolean,
    ifTrue: suspend (T) -> Flow<R>,
    ifFalse: suspend (T) -> Flow<R> = { emptyFlow() },
): Flow<R> =
    this.flatMapLatest { value ->
        if (condition(value)) {
            ifTrue(value)
        } else {
            ifFalse(value)
        }
    }

/**
 * An extension function for CoroutineScope that runs an initialization block and returns a StateFlow.
 *
 * The provided block is executed immediately when this function is called. After executing the block,
 * the function emits a Unit value into a StateFlow. The StateFlow is eagerly started, meaning the block
 * is executed and the initial value is set as soon as the StateFlow is created, regardless of whether
 * there are any collectors.
 *
 * @param block A lambda function that takes no arguments and returns Unit. This block is executed immediately.
 * @return A StateFlow that emits Unit after the block is executed. The initial value of the StateFlow is Unit.
 *
 * ```
 * import kotlinx.coroutines.*
 * import kotlinx.coroutines.flow.*
 *
 * fun CoroutineScope.onInit(block: () -> Unit) = flow {
 *     block()
 *     emit(Unit)
 * }.stateIn(scope = this, started = SharingStarted.Eagerly, initialValue = Unit)
 *
 * fun main() = runBlocking {
 *     // Define a CoroutineScope
 *     val scope = this
 *
 *     // Use the onInit extension function
 *     val stateFlow = scope.onInit {
 *         println("Initialization block executed")
 *     }
 *
 *     // Collect from the stateFlow
 *     stateFlow.collect {
 *         println("StateFlow emitted: $it")
 *     }
 * }
 * ```
 */
fun CoroutineScope.onInit(block: () -> Unit) = flow {
    block()
    emit(Unit)
}.stateIn(scope = this, started = SharingStarted.Eagerly, initialValue = Unit)

/**
 * Returns a Flow whose values are generated with transform function by combining the most recently emitted values by each flow.
 * This is the same as [kotlinx.coroutines.flow.combine] with 6 parameters.
 */
@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
    )
}

/**
 * Returns a Flow whose values are generated with transform function by combining the most recently emitted values by each flow.
 * This is the same as [kotlinx.coroutines.flow.combine] with 7 parameters.
 */
@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(flow, flow2, flow3, flow4, flow5, flow6, flow7) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
    )
}

/**
 * Returns a Flow whose values are generated with transform function by combining the most recently emitted values by each flow.
 * This is the same as [kotlinx.coroutines.flow.combine] with 8 parameters.
 */
@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
    )
}
