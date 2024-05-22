package ru.aip.intern.networking

import ru.aip.intern.util.UiText

data class Response<T>(val isSuccess: Boolean, val value: T?, val errorMessage: UiText?)