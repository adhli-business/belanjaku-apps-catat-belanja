package com.example.belanjaku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.belanjaku.data.UserManager

abstract class BaseAuthenticatedActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager

    override fun onStart() {
        super.onStart()
        userManager = UserManager(this)

        if (!userManager.isLoggedIn()) {
            // Redirect to login if user is not authenticated
            startActivity(Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }

    protected fun getUserManager(): UserManager = userManager
}
