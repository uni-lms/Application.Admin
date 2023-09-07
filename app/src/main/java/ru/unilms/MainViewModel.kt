package ru.unilms

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.di.ResourcesProvider
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(resourcesProvider: ResourcesProvider) : ViewModel()