package com.duzhaokun123.toasticon

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)

        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        val s1 = findViewById<Switch>(R.id.s_1)
        val s2 = findViewById<Switch>(R.id.s_2)

        s1.isChecked = prefs.getBoolean("show_app_name", false)
        s1.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_app_name", isChecked).apply()
        }
        s2.isChecked = prefs.getBoolean("text_color_follow", true)
        s2.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("text_color_follow", isChecked).apply()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            Toast.makeText(this, "test toast", Toast.LENGTH_LONG).show()
        }
    }
}