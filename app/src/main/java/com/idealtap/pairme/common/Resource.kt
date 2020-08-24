package com.idealtap.pairme.common


class Resource<T> private constructor(private val data: T?, private val error: Exception? = null, val isSuccessful: Boolean) {

    constructor(data: T): this(data, null, true)
    constructor(exception: Exception): this(null, exception, false)

    fun data(): T {
        check(isSuccessful){ "Resource not successful. Call isSuccessful() first." }
        return data!!
    }

    fun error(): Exception {
        checkNotNull(error) { "Error is null. Call isSuccessful() first." }
        return error
    }

}