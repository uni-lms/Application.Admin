package ru.unilms.domain.common.extension

import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> Collection<T>.toMutableStateList() = SnapshotStateList<T>().also { it.addAll(this) }