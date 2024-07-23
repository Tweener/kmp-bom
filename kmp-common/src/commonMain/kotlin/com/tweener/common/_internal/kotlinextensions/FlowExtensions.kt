package com.tweener.common._internal.kotlinextensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest

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
