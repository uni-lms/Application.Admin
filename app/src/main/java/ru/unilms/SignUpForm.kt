package ru.unilms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.EmailValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import ru.unilms.di.ResourcesProvider
import ru.unilms.domain.model.user.Gender
import java.util.Date

class SignUpForm(resourcesProvider: ResourcesProvider) : Form() {

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
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val email = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(
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
    val gender = FieldState<Gender?>(
        state = mutableStateOf<Gender?>(null),
        validators = mutableListOf(NotEmptyValidator()),
        options = mutableListOf(
            Gender(resourcesProvider.getString(R.string.gender_male)),
            Gender(resourcesProvider.getString(R.string.gender_female))
        ),
        optionItemFormatter = { "${it?.name}" },
    )


}