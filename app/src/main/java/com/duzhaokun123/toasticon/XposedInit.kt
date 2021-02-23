package com.duzhaokun123.toasticon

import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.crossbowffs.remotepreferences.RemotePreferences
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.ikws4.library.xposedktx.getObjectField
import io.ikws4.library.xposedktx.hookMethod

class XposedInit : IXposedHookLoadPackage {
    private lateinit var application: Application

    private var showAppName = false
    private var textColorFollow = true
    private var addName = true

    private val pm by lazy { application.packageManager }
    private val icon by lazy { pm.getApplicationIcon(application.applicationInfo) }
    private val appName by lazy { pm.getApplicationLabel(application.applicationInfo) }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "android") return

        Application::class.java.hookMethod("onCreate",
            afterHookedMethod = {
                application = this
                val prefs = RemotePreferences(
                    this, "com.duzhaokun123.toasticon.preferences", "prefs"
                )
                showAppName = prefs.getBoolean("show_app_name", false)
                textColorFollow = prefs.getBoolean("text_color_follow", true)
                addName = prefs.getBoolean("add_name", true)
            })

        Toast::class.java.hookMethod("show",
            beforeHookedMethod = before@{
                val contentView = view
                if (contentView != null) {
                    contentView.id = View.generateViewId()
                    val rlRoot = RelativeLayout(application)
                    rlRoot.addView(contentView)
                    val ivIcon = ImageView(application).apply {
                        layoutParams =
                            RelativeLayout.LayoutParams(dp2px(context, 20F), dp2px(context, 20F))
                                .apply {
                                    addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                                    addRule(RelativeLayout.ALIGN_BOTTOM, contentView.id)
                                }
                        setImageDrawable(icon)
                    }
                    rlRoot.addView(ivIcon)
                    if (showAppName) {
                        ivIcon.id = View.generateViewId()
                        val tvName = TextView(application).apply {
                            layoutParams =
                                RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                    .apply {
                                        addRule(RelativeLayout.END_OF, ivIcon.id)
                                        addRule(RelativeLayout.ALIGN_BOTTOM, contentView.id)
                                        marginStart = dp2px(context, 5F)
                                    }
                            text = appName
                            textSize = 12F
                            if (textColorFollow && contentView is ViewGroup) {
                                contentView.findFirstTextView()?.let {
                                    setTextColor(it.textColors)
                                }
                            }
                        }
                        rlRoot.addView(tvName)
                    }
                    view = rlRoot
                } else if (addName) {
                    val rawText = this.getObjectField("mText") as CharSequence
                    if (rawText.startsWith(appName).not()) {
                        val text = StringBuilder(appName)
                            .append(": ")
                            .append(rawText)
                        setText(text)
                    }
                }
            })
    }
}