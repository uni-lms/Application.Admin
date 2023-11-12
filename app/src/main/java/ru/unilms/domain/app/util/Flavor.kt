package ru.unilms.domain.app.util

import ru.unilms.BuildConfig

class Flavor {
    enum class Role(val value: String) {
        Admin("admin"),
        Student("student"),
        Tutor("tutor")
    }
}

fun getFlavor(): Flavor.Role {
    return when (BuildConfig.FLAVOR) {
        "admin" -> Flavor.Role.Admin
        "student" -> Flavor.Role.Student
        "tutor" -> Flavor.Role.Tutor
        else -> Flavor.Role.Student
    }
}