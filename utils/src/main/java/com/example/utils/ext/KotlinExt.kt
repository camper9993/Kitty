package com.example.utils.ext

inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        return null
    }
}