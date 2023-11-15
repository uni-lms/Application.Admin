package ru.unilms.domain.common.form.validator

import ch.benlu.composeform.Validator

class UrlValidator(errorText: String? = null) : Validator<String?>(
    validate = {
        """https://(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()!@:%_+.~#?&/=]*)""".toRegex()
            .matches("$it")
    },
    errorText = errorText ?: "Введите корректный адрес сервера"
)