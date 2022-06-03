package com.ml.mlkit.presentation.custom_view.toolbar

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Int.correctColor(ctx: Context, @ColorRes color: Int) =
    if (this != -1)
        this
    else
        ContextCompat.getColor(ctx, color)
