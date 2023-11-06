package ru.unilms.domain.course.util.enums

import ru.unilms.R

enum class CourseBlockName(val labelId: Int) {
    Lectures(R.string.course_block_lectures),
    CourseProject(R.string.course_block_course_project),
    LabWorks(R.string.course_block_lab_works),
    FinalCertification(R.string.course_block_final_certification)
}