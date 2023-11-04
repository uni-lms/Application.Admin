package ru.unilms.utils.networking

import ru.unilms.network.services.Response

//fun <T, E> processResponse(
//    response: Response<T, E>,
//    onSuccess: (T) -> Unit,
//    onError: (E?) -> Unit
//) {
//    when (response) {
//        is Response.Success -> onSuccess(response.body)
//        is Response.Error.HttpError -> onError(response.errorBody)
//        is Response.Error.NetworkError -> onError(null)
//        is Response.Error.SerializationError -> onError(null)
//    }
//}

fun <T, E> processResponse(
    response: Response<T, E>
): T? {
    return when (response) {
        is Response.Success -> response.body
        else -> null
    }
}