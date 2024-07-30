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
    ifTrue: suspend () -> Flow<R>,
    ifFalse: suspend () -> Flow<R> = { emptyFlow() },
): Flow<R> =
    this.flatMapLatest { value ->
        if (condition(value)) {
            ifTrue()
        } else {
            ifFalse()
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
