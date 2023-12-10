package ru.unilms.domain.manage.view.form

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form

class ManageGroupForm : Form() {
    override fun self(): Form {
        return this
    }

    val name = FieldState(
        state = mutableStateOf<String?>(null)
    )

    val currentSemester = FieldState(
        state = mutableStateOf<String?>(null)
    )

    val maxSemester = FieldState(
        state = mutableStateOf<String?>(null)
    )
}