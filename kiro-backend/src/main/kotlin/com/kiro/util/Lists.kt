package com.kiro.util

fun <T> List<T>.hasNotDuplicates(): Boolean {
    return size == toSet().size
}