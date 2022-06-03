package com.ml.mlkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ml.mlkit.databinding.ActivityMainBinding
import com.ml.mlkit.presentation.extensions.hide
import com.ml.mlkit.presentation.extensions.show
import com.ml.mlkit.presentation.ui.barcode.BarcodeFragment
import com.ml.mlkit.presentation.ui.barcode.BarcodeScannerFragment
import com.ml.mlkit.presentation.ui.home.HomeFragment
import com.ml.mlkit.presentation.ui.passport_scanner.PassportFragment
import com.ml.mlkit.presentation.ui.passport_scanner.PassportScannerFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    Slide(Gravity.BOTTOM).excludeTarget(R.id.nav_host_fragment, true)
                )
                when (f) {
                    is HomeFragment -> showBottomNav()
                    is BarcodeFragment -> showBottomNav()
                    is PassportFragment -> showBottomNav()
                    else -> hideBottomNav()
                }
            }
        }, true)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> navController.navigate(R.id.homeFragment)
                R.id.barcode_scanner_fragment -> navController.navigate(R.id.barcodeFragment)
                R.id.recognize_text_fragment -> navController.navigate(R.id.passportFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showBottomNav() {
        binding.bottomBar.show()

    }

    private fun hideBottomNav() {
        binding.bottomBar.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}