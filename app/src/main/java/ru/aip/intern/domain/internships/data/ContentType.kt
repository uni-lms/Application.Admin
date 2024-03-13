package ru.aip.intern.domain.internships.data

enum class ContentType(val typeName: String) {
    Text("text"),
    Link("link"),
    File("file"),
    Assignment("assignment")
}