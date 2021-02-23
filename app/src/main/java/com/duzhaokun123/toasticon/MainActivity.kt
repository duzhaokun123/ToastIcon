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

        findViewById<Switch>(R.id.s_1).apply {
            isChecked = prefs.getBoolean("show_app_name", false)
            setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("show_app_name", isChecked).apply()
            }
        }
        findViewById<Switch>(R.id.s_2).apply {
            isChecked = prefs.getBoolean("text_color_follow", true)
            setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("text_color_follow", isChecked).apply()
            }
        }
        findViewById<Switch>(R.id.s_3).apply {
            isChecked = prefs.getBoolean("add_name", true)
            setOnClickListener {
                prefs.edit().putBoolean("add_name", isChecked).apply()
            }
        }
        findViewById<Button>(R.id.btn_1).setOnClickListener {
            Toast.makeText(this, "test toast", Toast.LENGTH_LONG).show()
        }
        findViewById<Button>(R.id.btn_2).setOnClickListener {
            Thread {
                Thread.sleep(5000)
                runOnUiThread {
                    Toast.makeText(this, "test toast", Toast.LENGTH_LONG).show()
                }
            }.start()
        }
    }
}