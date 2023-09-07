package ru.unilms

import android.content.res.loader.ResourcesProvider
import ch.benlu.composeform.Form

class SignUpForm(resourcesProvider: ResourcesProvider): Form() {
    override fun self(): Form {
        return this
    }
}