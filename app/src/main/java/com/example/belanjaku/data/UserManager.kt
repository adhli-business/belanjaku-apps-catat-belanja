package com.example.belanjaku.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.security.MessageDigest

class UserManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD_HASH = "password_hash"
        private const val KEY_EMAIL = "email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val PREF_USERS = "registered_users"
    }

    fun registerUser(username: String, password: String, email: String? = null): Boolean {
        if (isUserRegistered(username)) {
            return false
        }

        // Store user credentials
        val userPrefs = context.getSharedPreferences(PREF_USERS, Context.MODE_PRIVATE)
        userPrefs.edit().apply {
            putString("$username.password", hashPassword(password))
            putString("$username.email", email)
            apply()
        }

        return true
    }

    fun loginUser(username: String, password: String): Boolean {
        val userPrefs = context.getSharedPreferences(PREF_USERS, Context.MODE_PRIVATE)
        val storedPasswordHash = userPrefs.getString("$username.password", null)

        if (storedPasswordHash != null && storedPasswordHash == hashPassword(password)) {
            saveUserSession(username)
            return true
        }
        return false
    }

    private fun saveUserSession(username: String) {
        val userPrefs = context.getSharedPreferences(PREF_USERS, Context.MODE_PRIVATE)
        editor.apply {
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, userPrefs.getString("$username.email", null))
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getCurrentUser(): User? {
        val username = sharedPreferences.getString(KEY_USERNAME, null) ?: return null
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        return User(username, "", email)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logoutUser() {
        editor.apply {
            clear()
            apply()
        }
    }

    private fun isUserRegistered(username: String): Boolean {
        val userPrefs = context.getSharedPreferences(PREF_USERS, Context.MODE_PRIVATE)
        return userPrefs.contains("$username.password")
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return Base64.encodeToString(digest, Base64.NO_WRAP)
    }

    private lateinit var context: Context
    init {
        this.context = context
    }
}
