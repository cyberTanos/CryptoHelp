package com.tanya.finhelp.util

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Float?.orZero(): Float {
    return this ?: 0f
}
