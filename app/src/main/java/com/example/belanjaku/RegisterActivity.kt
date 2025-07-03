package com.example.belanjaku

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.belanjaku.data.UserManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var btnRegister: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userManager = UserManager(this)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        tilUsername = findViewById(R.id.tilUsername)
        tilPassword = findViewById(R.id.tilPassword)
        tilEmail = findViewById(R.id.tilEmail)
        btnRegister = findViewById(R.id.btnRegister)
        progressIndicator = findViewById(R.id.progressIndicator)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar Akun"
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            if (validateInputs()) {
                performRegistration()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val username = tilUsername.editText?.text.toString()
        if (username.isEmpty()) {
            tilUsername.error = "Username harus diisi"
            isValid = false
        } else if (username.length < 4) {
            tilUsername.error = "Username minimal 4 karakter"
            isValid = false
        } else {
            tilUsername.error = null
        }

        val password = tilPassword.editText?.text.toString()
        if (password.isEmpty()) {
            tilPassword.error = "Password harus diisi"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "Password minimal 6 karakter"
            isValid = false
        } else {
            tilPassword.error = null
        }

        val email = tilEmail.editText?.text.toString()
        if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Format email tidak valid"
            isValid = false
        } else {
            tilEmail.error = null
        }

        return isValid
    }

    private fun performRegistration() {
        showLoading(true)

        val username = tilUsername.editText?.text.toString()
        val password = tilPassword.editText?.text.toString()
        val email = tilEmail.editText?.text.toString().takeIf { it.isNotEmpty() }

        // Simulate network delay
        btnRegister.postDelayed({
            if (userManager.registerUser(username, password, email)) {
                showSuccess("Registrasi berhasil! Silakan login.")
                finish()
            } else {
                showLoading(false)
                showError("Username sudah terdaftar")
            }
        }, 1000)
    }

    private fun showLoading(show: Boolean) {
        progressIndicator.visibility = if (show) View.VISIBLE else View.GONE
        btnRegister.isEnabled = !show
        tilUsername.isEnabled = !show
        tilPassword.isEnabled = !show
        tilEmail.isEnabled = !show
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(android.R.color.holo_red_light))
            .show()
    }

    private fun showSuccess(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(android.R.color.holo_green_light))
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
