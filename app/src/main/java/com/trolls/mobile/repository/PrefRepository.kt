package com.trolls.mobile.repository

import android.content.Context

class PrefRepository(context: Context) {
    private val KEY_LATELY_SEARCH = "latelySearch"

    private var latelySearch = setOf<String>()

    private val pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun getLatelySearch(): Set<String> {
        latelySearch = pref.getStringSet(KEY_LATELY_SEARCH, setOf()) ?: setOf()
        return latelySearch
    }

    fun setLatelySearch(latelySearch: Set<String>) {
        editor.putStringSet(KEY_LATELY_SEARCH, latelySearch)
        editor.apply()
    }
}
