package ru.unilms.domain.auth.util.enums

import ru.unilms.R

enum class UserRole(val labelId: Int) {
    Student(R.string.role_student),
    Tutor(R.string.role_tutor),
    Administrator(R.string.role_admin)
}