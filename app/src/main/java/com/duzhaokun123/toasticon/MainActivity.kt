package com.duzhaokun123.toasticon

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val llRoot = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }.also { setContentView(it) }

        super.onCreate(savedInstanceState)

        @SuppressLint("WorldReadableFiles")
        @Suppress("DEPRECATION")
        val prefs = try {
            getSharedPreferences("prefs", MODE_WORLD_READABLE)
        } catch (e: SecurityException) {
            getSharedPreferences("prefs", MODE_PRIVATE)
        }

        Switch(this).apply {
            setText(R.string.show_app_name)
            isChecked = prefs.getBoolean("show_app_name", false)
            setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("show_app_name", isChecked).apply()
            }
        }.also { llRoot.addView(it, MATCH_PARENT, WRAP_CONTENT) }

        Switch(this).apply {
            setText(R.string.text_color_follow_toast)
            isChecked = prefs.getBoolean("text_color_follow", true)
            setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("text_color_follow", isChecked).apply()
            }
        }.also { llRoot.addView(it, MATCH_PARENT, WRAP_CONTENT) }

        Switch(this).apply {
            setText(R.string.add_name)
            isChecked = prefs.getBoolean("add_name", true)
            setOnClickListener {
                prefs.edit().putBoolean("add_name", isChecked).apply()
            }
        }.also { llRoot.addView(it, MATCH_PARENT, WRAP_CONTENT) }

        Button(this).apply {
            setText(R.string.show_toast)
            setOnClickListener {
                Toast.makeText(this@MainActivity, "test toast", Toast.LENGTH_LONG).show()
            }
        }.also { llRoot.addView(it, MATCH_PARENT, WRAP_CONTENT) }

        Button(this).apply {
            setText(R.string.show_toast_5s_later)
            setOnClickListener {
                Thread {
                    Thread.sleep(5000)
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "test toast", Toast.LENGTH_LONG).show()
                    }
                }.start()
            }
        }.also { llRoot.addView(it, MATCH_PARENT, WRAP_CONTENT) }
    }
}