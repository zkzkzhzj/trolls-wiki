package com.trolls.mobile.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    fun setThemeMode(isCheck: Boolean) {
        if (isCheck) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            return
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun getBottomSheetMaxHeight(height: Int): Int {
        return (height * BOTTOM_SHEET_OPEN_PERCENTAGE).toInt()
    }

    fun getBottomSheetMinHeight(height: Int): Int {
        return (height * BOTTOM_SHEET_CLOSE_PERCENTAGE).toInt()
    }

    companion object {
        const val LOG_TITLE = "LOG_DATA"
        const val BOTTOM_SHEET_OPEN_PERCENTAGE = 0.8
        const val BOTTOM_SHEET_CLOSE_PERCENTAGE = 0.07
    }
}
