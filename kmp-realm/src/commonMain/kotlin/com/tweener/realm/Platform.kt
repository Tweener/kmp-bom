package com.tweener.realm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
