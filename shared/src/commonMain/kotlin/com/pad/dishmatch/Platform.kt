package com.pad.dishmatch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform