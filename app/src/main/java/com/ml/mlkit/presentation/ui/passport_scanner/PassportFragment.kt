package com.ml.mlkit.presentation.ui.passport_scanner

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ml.mlkit.R
import com.ml.mlkit.databinding.FragmentPasportBinding
import com.ml.mlkit.presentation.base.BaseFragment


class PassportFragment : BaseFragment<FragmentPasportBinding>(FragmentPasportBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPassport.setOnClickListener {
            findNavController().navigate(R.id.action_passportFragment_to_passportScannerFragment)
        }
    }


}