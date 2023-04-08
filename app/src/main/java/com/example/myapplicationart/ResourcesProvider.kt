package com.example.myapplicationart

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(
    private val context: Context
) {
    fun provideStringResourcesWithArgs(@StringRes res: Int, vararg formatArgs: Any): String =
        context.getString(res, *formatArgs)
}