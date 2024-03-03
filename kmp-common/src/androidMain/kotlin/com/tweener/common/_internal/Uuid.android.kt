package com.tweener.common._internal

import java.util.UUID

/**
 * @author Vivien Mahe
 * @since 27/08/2023
 */

actual fun generateRandomUUID() = UUID.randomUUID().toString()
