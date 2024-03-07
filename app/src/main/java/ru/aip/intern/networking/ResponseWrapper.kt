package ru.aip.intern.networking

data class ResponseWrapper<T>(
    val value: T?,
    val status: Int,
    val isSuccess: Boolean,
    val successMessage: String,
    val correlationId: String,
    val errors: List<String>,
    val validationErrors: List<String>
)
