package com.example.belanjaku

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.belanjaku.data.UserManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userManager = UserManager(this)

        // Redirect to Home if already logged in
        if (userManager.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        tilUsername = findViewById(R.id.tilUsername)
        tilPassword = findViewById(R.id.tilPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val username = tilUsername.editText?.text.toString()
        if (username.isEmpty()) {
            tilUsername.error = "Username harus diisi"
            isValid = false
        } else {
            tilUsername.error = null
        }

        val password = tilPassword.editText?.text.toString()
        if (password.isEmpty()) {
            tilPassword.error = "Password harus diisi"
            isValid = false
        } else {
            tilPassword.error = null
        }

        return isValid
    }

    private fun performLogin() {
        showLoading(true)

        val username = tilUsername.editText?.text.toString()
        val password = tilPassword.editText?.text.toString()

        // Simulate network delay
        btnLogin.postDelayed({
            if (userManager.loginUser(username, password)) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                showLoading(false)
                showError("Username atau password salah")
            }
        }, 1000)
    }

    private fun showLoading(show: Boolean) {
        progressIndicator.visibility = if (show) View.VISIBLE else View.GONE
        btnLogin.isEnabled = !show
        btnRegister.isEnabled = !show
        tilUsername.isEnabled = !show
        tilPassword.isEnabled = !show
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}
