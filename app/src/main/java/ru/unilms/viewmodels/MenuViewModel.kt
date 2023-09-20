package ru.unilms.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.unilms.R
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MenuViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
    fun getUserName(): String {
        return context.resources.getString(R.string.fake_user_name)
    }
}