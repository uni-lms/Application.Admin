package ru.unilms.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ru.unilms.utils.forms.validators.NotEmptyValidator
import ru.unilms.utils.forms.validators.UrlValidator

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