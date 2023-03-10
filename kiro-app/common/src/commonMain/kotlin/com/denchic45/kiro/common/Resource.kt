package com.denchic45.kiro.common

import com.github.michaelbull.result.mapBoth
import com.kiro.common.ResponseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

sealed interface Resource<out T> {
    object Loading : Resource<Nothing>
    data class Success<T>(val value: T) : Resource<T>
    data class Error(val failure: Failure) : Resource<Nothing>
}


fun <T> Resource<T>.getData() = (this as Resource.Success).value

typealias EmptyResource = Resource<Unit>

fun emptyResource(): EmptyResource = Resource.Success(Unit)

fun <T> Resource<T>.success() = this as Resource.Success

fun <T> Resource.Success<T>.updateResource(function: (T) -> T): Resource.Success<T> {
    return Resource.Success(function(value))
}

fun <T> ResponseResult<T>.toResource(): Resource<T> = mapBoth(
    success = { Resource.Success(it) },
    failure = { Resource.Error(it.toFailure()) }
)

fun <T> ResponseResult<T>.toEmptyResource(): EmptyResource = mapBoth(
    success = { Resource.Success(Unit) },
    failure = { Resource.Error(it.toFailure()) }
)

inline infix fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> = apply {
    if (this is Resource.Success) {
        action(value)
    }
}

inline infix fun <T> Resource<T>.onFailure(action: (Failure) -> Unit): Resource<T> = apply {
    if (this is Resource.Error) {
        action(failure)
    }
}

inline fun <T, V> Resource<T>.map(transform: (T) -> V): Resource<V> {
    return when (this) {
        is Resource.Error -> this
        is Resource.Loading -> this
        is Resource.Success -> Resource.Success(transform(value))
    }
}

fun <T, V> Resource<T>.flatMap(function: (T) -> Resource<V>): Resource<V> {
    return when (this) {
        is Resource.Error -> this
        is Resource.Loading -> this
        is Resource.Success -> function(value)
    }

}

inline fun <T> Resource<T>.mapError(transform: (Failure) -> Failure): Resource<T> {
    return when (this) {
        is Resource.Error -> Resource.Error(transform(failure))
        is Resource.Loading,
        is Resource.Success,
        -> this
    }
}

//inline fun <T, V> Resource<T>.mapBoth(onSuccess: (T) -> V, onFailure: (Failure) -> V): V {
//    return when (this) {
//        is Resource.Error -> onFailure(failure)
//        is Resource.Loading -> this
//        is Resource.Success -> onSuccess(value)
//    }
//}

fun <T> Flow<Resource<T>>.filterSuccess(): Flow<Resource.Success<T>> = filterIsInstance()

fun <T> Flow<Resource<T>>.updateResource(onSuccess: (T) -> T): Flow<Resource<T>> = map {
    when (it) {
        is Resource.Error -> it
        is Resource.Loading -> it
        is Resource.Success -> Resource.Success(onSuccess(it.value))
    }
}

fun <T, V> Flow<Resource<T>>.mapResource(function: (T) -> V): Flow<Resource<V>> = map {
    when (it) {
        is Resource.Error -> it
        is Resource.Loading -> it
        is Resource.Success -> Resource.Success(function(it.value))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, V> Flow<Resource<T>>.flatMapResource(function: (T) -> Flow<Resource<V>>): Flow<Resource<V>> {
    return flatMapLatest {
        when (it) {
            is Resource.Error -> flowOf(it)
            is Resource.Loading -> flowOf(it)
            is Resource.Success -> function(it.value)
        }
    }
}