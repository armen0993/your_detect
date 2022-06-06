package com.ml.mlkit.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<Binding : ViewBinding>( private val inflate: Inflate<Binding>) : Fragment()  {

    private var _binding: Binding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }

    protected open lateinit var navController: NavController

    private lateinit var  navOptions: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        navOptions = NavOptions.Builder()
            .setPopUpTo(navController.graph.startDestinationId, true)
            .setLaunchSingleTop(true)
            .build()
    }

    protected fun popBackStack() {
        navController.popBackStack()
    }

    protected fun navigateFragment(destinationId: Int, arg: Bundle? = null) {
        navController.navigate(destinationId, arg)
    }

    protected fun navigateRootFragment(destinationId: Int, arg: Bundle? = null) {
        navController.navigate(destinationId, arg, navOptions)
    }

    protected fun navigateFragment(destinations: NavDirections) {
        navController.navigate(destinations)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}