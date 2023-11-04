package ru.unilms.utils.forms.validators

import ch.benlu.composeform.Validator

class NotEmptyValidator<T>(errorText: String? = null) : Validator<T>(
    validate =
    {
        it != null && it != ""
    },
    errorText = errorText ?: "Поле не должно быть пустым"
)