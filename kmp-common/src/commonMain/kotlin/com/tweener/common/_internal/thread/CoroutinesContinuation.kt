package com.tweener.common._internal.thread

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * @author Vivien Mahe
 * @since 01/10/2024
 */

/**
 * Calls the specified suspendable function block with this value as its receiver and returns its encapsulated result if invocation was successful, catching any Throwable exception, including a [CancellationException], that was thrown from the block function execution and encapsulating it as a failure.
 *
 * See this [issue](https://github.com/Kotlin/kotlinx.coroutines/issues/3658#issuecomment-1943562914) to better understand the [CancellationException] handling.
 */
suspend inline fun <T, R> T.suspendCatching(crossinline block: suspend T.() -> R): Result<R> = coroutineScope {
    try {
        Result.success(block())
    } catch (throwable: Throwable) {
        if (throwable is CancellationException) coroutineContext.ensureActive()
        Result.failure(throwable)
    }
}

/**
 * Resumes the execution of the corresponding coroutine passing value as the return value of the last suspension point, if this [CancellationException] is still active.
 */
inline fun <T> CancellableContinuation<T>.resumeIfActive(value: T): Unit {
    if (isActive) resume(value)
}

/**
 * Resumes the execution of the corresponding coroutine so that the exception is re-thrown right after the last suspension point, if this [CancellationException] is still active.
 */
inline fun <T> CancellableContinuation<T>.resumeWithExceptionIfActive(exception: Throwable): Unit {
    if (isActive) resumeWithException(exception)
}
