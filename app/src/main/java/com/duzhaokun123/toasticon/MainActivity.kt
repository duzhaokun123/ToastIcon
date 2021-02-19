package com.duzhaokun123.toasticon

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.edit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("prefs", Activity.MODE_PRIVATE)

        val sc1 = findViewById<SwitchCompat>(R.id.sc_1)
        val sc2 = findViewById<SwitchCompat>(R.id.sc_2)

        sc1.isChecked = prefs.getBoolean("show_app_name", false)
        sc1.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit { putBoolean("show_app_name", isChecked) }
        }
        sc2.isChecked = prefs.getBoolean("text_color_follow", true)
        sc2.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit { putBoolean("text_color_follow", isChecked) }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            Toast.makeText(this, "test toast", Toast.LENGTH_LONG).show()
        }
    }
}