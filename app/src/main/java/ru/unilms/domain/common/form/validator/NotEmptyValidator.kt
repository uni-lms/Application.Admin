package ru.unilms.domain.common.form.validator

import ch.benlu.composeform.Validator

class NotEmptyValidator<T>(errorText: String? = null) : Validator<T>(
    validate =
    {
        it != null && it != ""
    },
    errorText = errorText ?: "Поле не должно быть пустым"
)