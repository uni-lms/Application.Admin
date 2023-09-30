package ru.unilms.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.unilms.R
import ru.unilms.domain.model.auth.WhoAmIResponse
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MenuViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
    fun whoami(): WhoAmIResponse {
        return WhoAmIResponse(
            name = context.resources.getString(R.string.fake_user_name),
            email = context.resources.getString(R.string.fake_user_email)
        )
    }
}