package com.example.belanjaku

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.belanjaku.data.UserManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userManager = UserManager(this)

        // Cek status login
        if (!userManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupViews()
        setupUserInfo()
        setupListeners()
    }

    private fun setupViews() {
        try {
            tvUsername = findViewById(R.id.tvUsername)
            tvEmail = findViewById(R.id.tvEmail)
            btnLogout = findViewById(R.id.btnLogout)

            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = "Profil Saya"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
        }
    }

    private fun setupUserInfo() {
        try {
            val currentUser = userManager.getCurrentUser()
            currentUser?.let { user ->
                tvUsername.text = "Username: ${user.username}"
                tvEmail.text = "Email: ${user.email ?: "Tidak ada"}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupListeners() {
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Keluar") { _, _ ->
                performLogout()
            }
            .show()
    }

    private fun performLogout() {
        userManager.logoutUser()
        startActivity(Intent(this, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
