package com.tweener.common._internal.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @author Vivien Mahe
 * @since 20/11/2023
 */

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> requireNotNullOrThrow(value: T?, throwable: () -> Throwable): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        throw throwable()
    } else {
        return value
    }
}
