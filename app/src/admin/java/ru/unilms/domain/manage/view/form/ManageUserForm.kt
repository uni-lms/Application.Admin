package ru.unilms.domain.manage.view.form

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField

class ManageUserForm : Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val firstNameField = FieldState(
        state = mutableStateOf<String?>(null)
    )

    @FormField
    val lastNameField = FieldState(
        state = mutableStateOf<String?>(null)
    )

    @FormField
    val patronymicField = FieldState(
        state = mutableStateOf<String?>(null)
    )

    @FormField
    val roleNameField = FieldState(
        state = mutableStateOf<String?>(null)
    )


}