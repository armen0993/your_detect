package com.ml.mlkit.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ml.mlkit.R
import com.ml.mlkit.databinding.FragmentSplashBinding
import com.ml.mlkit.presentation.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(3000)
            navigateFragment(R.id.action_splashFragment_to_homeFragment)
        }
    }
}