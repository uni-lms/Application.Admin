package ru.unilms.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ru.unilms.domain.model.user.Gender
import ru.unilms.domain.model.user.Role
import ru.unilms.utils.forms.validators.EmailValidator
import ru.unilms.utils.forms.validators.NotEmptyValidator
import java.util.Date

class SignUpForm(genders: MutableList<Gender?>, roles: MutableList<Role?>) : Form() {

    override fun self(): Form {
        return this
    }

    @FormField
    val firstName = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val lastName = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val patronymic = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf()
    )

    @FormField
    val email = FieldState(
        state = mutableStateOf(null),
        validators = mutableListOf(
            NotEmptyValidator(),
            EmailValidator()
        )
    )

    @FormField
    val dateOfBirth = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )

    @FormField
    val gender = FieldState(
        state = mutableStateOf(null),
        validators = mutableListOf(NotEmptyValidator()),
        options = genders,
        optionItemFormatter = { "${it?.name}" },
    )

    @FormField
    val role = FieldState(
        state = mutableStateOf(null),
        validators = mutableListOf(NotEmptyValidator()),
        options = roles,
        optionItemFormatter = { "${it?.name}" },
    )


}