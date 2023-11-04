package ru.unilms.utils.enums

enum class TaskStatus(val value: String) {
    NotSent("not_sent"),
    Sent("sent"),
    Checked("checked"),
    Overdue("overdue")
}