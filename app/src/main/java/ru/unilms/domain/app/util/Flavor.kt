package ru.unilms.domain.app.util

import ru.unilms.BuildConfig

class Flavor {
    enum class Role(val value: String) {
        Admin("admin"),
        Student("student")
    }
}

fun getFlavor(): Flavor.Role {
    return when (BuildConfig.FLAVOR) {
        "admin" -> Flavor.Role.Admin
        "student" -> Flavor.Role.Student
        else -> Flavor.Role.Student
    }
}