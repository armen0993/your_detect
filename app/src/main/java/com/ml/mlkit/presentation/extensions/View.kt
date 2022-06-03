package com.ml.mlkit.presentation.extensions

import android.view.View

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
    alpha = 1F
}
fun View.show() {
    visibility =View.VISIBLE
    alpha = 1F
}

fun View.hide() {
    visibility =View.GONE
    alpha = 0F
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}