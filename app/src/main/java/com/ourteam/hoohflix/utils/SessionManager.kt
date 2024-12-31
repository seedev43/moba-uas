package com.ourteam.hoohflix.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    private val SESSION_COOKIE_KEY = "session_cookie"

    // Menyimpan cookie sesi
    fun saveSessionCookie(cookie: String?) {
        cookie?.let {
            editor.putString(SESSION_COOKIE_KEY, it)
            editor.apply()
        }
    }

    // Mengambil cookie sesi
    fun getSessionCookie(): String? {
        return prefs.getString(SESSION_COOKIE_KEY, null)
    }

    // Menghapus sesi
    fun clearSession() {
        editor.remove(SESSION_COOKIE_KEY)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return getSessionCookie() != null
    }
}
