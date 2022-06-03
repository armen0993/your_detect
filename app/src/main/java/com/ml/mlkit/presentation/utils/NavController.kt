package com.ml.mlkit.presentation.utils

import androidx.navigation.NavController

fun NavController.isFragmentInBackStack(destinationId: Int) =
    try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        //Log.e("", e.localizedMessage)
        false
    }
