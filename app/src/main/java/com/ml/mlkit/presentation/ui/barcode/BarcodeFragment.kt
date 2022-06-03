package com.ml.mlkit.presentation.ui.barcode

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ml.mlkit.R
import com.ml.mlkit.databinding.FragmentBarcodeBinding
import com.ml.mlkit.presentation.base.BaseFragment

class BarcodeFragment : BaseFragment<FragmentBarcodeBinding>(FragmentBarcodeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBarcode.setOnClickListener {
            findNavController().navigate(R.id.action_barcodeFragment_to_barcodeScannerFragment)
        }
    }





}