package ru.unilms.domain.auth.view.form

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ru.unilms.domain.common.form.validator.NotEmptyValidator
import ru.unilms.domain.common.form.validator.UrlValidator

class SelectApiUriForm : Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val apiUri = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator(), UrlValidator())
    )
}