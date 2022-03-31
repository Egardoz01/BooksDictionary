package com.booksdictionary.database

import android.content.Context
import android.content.res.Resources
import com.booksdictionary.R


enum class StatusEnum(private val code: Int) {
    NotStarted(0),
    InProgress(1),
    Finished(2);


    fun getLabel(context: Context): String {
        when (code) {
            0 -> return context.getString(R.string.notStarted)
            1 -> return context.getString(R.string.inProgress)
            2 -> return context.getString(R.string.finished)
        }

        return ""
    }

}