package com.duzhaokun123.toasticon

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView

fun dp2px(context: Context, dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5F).toInt()
}

fun ViewGroup.findFirstTextView(): TextView? {
    val count = childCount
    (0..count).forEach { i ->
        val child = this.getChildAt(i)
        if (child is TextView) return child
        else if (child is ViewGroup) child.findFirstTextView()?.let { return it }
    }
    return null
}