package ru.unilms.domain.course.view.form

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ru.unilms.domain.course.model.Block

class CreateFileForm(blocks: MutableList<Block?>) : Form() {
    override fun self(): Form {
        return this
    }


    val blockField = FieldState(
        state = mutableStateOf(null),
        options = blocks
    )

    val isVisibleToStudentsField = FieldState(
        state = mutableStateOf(false)
    )

    val visibleNameField = FieldState<String?>(
        state = mutableStateOf(null)
    )

}