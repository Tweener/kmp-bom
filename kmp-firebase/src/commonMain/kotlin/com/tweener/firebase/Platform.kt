package com.tweener.firebase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
