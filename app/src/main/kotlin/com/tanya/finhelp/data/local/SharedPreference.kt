package com.tanya.finhelp.data.local

import android.content.SharedPreferences

private const val USERNAME_KEY = "username"

class SharedPreference(
    private val sharedPref: SharedPreferences
) {

    fun saveUsername(username: String) {
        return sharedPref.edit().putString(USERNAME_KEY, username).apply()
    }

    fun getUsername(): String {
        return sharedPref.getString(USERNAME_KEY, "").orEmpty()
    }

    fun deleteUsername() {
        sharedPref.edit().remove(USERNAME_KEY).apply()
    }
}
