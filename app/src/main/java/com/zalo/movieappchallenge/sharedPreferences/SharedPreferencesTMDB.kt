package com.zalo.movieappchallenge.sharedPreferences

import com.zalo.movieappchallenge.appController.AppController

class SharedPreferencesTMDB {
    var countItem: Int
        get() = prefs.getInt(COUNT_ITEM, 0)
        set(value) = prefs.edit().putInt(COUNT_ITEM, value).apply()

    companion object{
        val prefs = AppController.preferences
        const val COUNT_ITEM = "countItem"
    }
}