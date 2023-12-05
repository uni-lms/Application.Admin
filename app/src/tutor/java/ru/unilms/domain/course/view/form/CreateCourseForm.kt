package ru.unilms.domain.course.view.form

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ru.unilms.domain.course.model.Block
import ru.unilms.domain.manage.model.Group

class CreateCourseForm(groups: MutableList<Group?>, blocks: MutableList<Block?>, context: Context) :
    Form() {
    override fun self(): Form {
        return this
    }

    val nameField = FieldState<String?>(
        state = mutableStateOf(null)
    )

    val abbreviationField = FieldState<String?>(
        state = mutableStateOf(null)
    )

    val groupsField = FieldState(
        state = mutableStateOf(null),
        options = groups,
        optionItemFormatter = { "${it?.name}" }
    )

    val semesterField = FieldState<String?>(
        state = mutableStateOf(null)
    )
}