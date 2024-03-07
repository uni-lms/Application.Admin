package ru.aip.intern.networking

fun <T, E> processResponse(
    response: Response<T, E>
): T? {
    return when (response) {
        is Response.Success -> response.body.value
        else -> null
    }
}