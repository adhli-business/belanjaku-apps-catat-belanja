package com.example.belanjaku

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeActivity : BaseAuthenticatedActivity() {
    private lateinit var fabAddItem: ExtendedFloatingActionButton
    private lateinit var btnAddItem: MaterialButton
    private lateinit var btnItemList: MaterialButton
    private lateinit var btnTotal: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupViews()
        setupListeners()
        setupScrollBehavior()
    }

    private fun setupViews() {
        // Find views
        fabAddItem = findViewById(R.id.fabAddItem)
        btnAddItem = findViewById(R.id.btnAddItem)
        btnItemList = findViewById(R.id.btnItemList)
        btnTotal = findViewById(R.id.btnTotal)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "BelanjaKu"
    }

    private fun setupListeners() {
        // Both FAB and button can add items
        val addItemClickListener = {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

        fabAddItem.setOnClickListener { addItemClickListener() }
        btnAddItem.setOnClickListener { addItemClickListener() }

        btnItemList.setOnClickListener {
            startActivity(Intent(this, ItemListActivity::class.java))
        }

        btnTotal.setOnClickListener {
            startActivity(Intent(this, TotalActivity::class.java))
        }
    }

    private fun setupScrollBehavior() {
        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && fabAddItem.isExtended) {
                // Scrolling down, collapse FAB
                fabAddItem.shrink()
            } else if (scrollY < oldScrollY && !fabAddItem.isExtended) {
                // Scrolling up, extend FAB
                fabAddItem.extend()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
