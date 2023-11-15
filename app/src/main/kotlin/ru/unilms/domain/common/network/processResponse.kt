package ru.unilms.domain.common.network

fun <T, E> processResponse(
    response: Response<T, E>,
): T? {
    return when (response) {
        is Response.Success -> response.body
        else -> null
    }
}