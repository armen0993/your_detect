package com.ml.mlkit.presentation.ui.barcode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.ml.mlkit.databinding.FragmentBarcodeScannerBinding
import com.ml.mlkit.presentation.base.BaseFragment

import com.ml.mlkit.presentation.analyzer.BarcodeAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias BarcodeListener = (barcode: String) -> Unit

class BarcodeScannerFragment : BaseFragment<FragmentBarcodeScannerBinding>(FragmentBarcodeScannerBinding::inflate) {

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cameraExecutor = Executors.newSingleThreadExecutor()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        startCamera()

    }

    private fun requestPermissions(){
        val requestPermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions.launch(
                arrayOf(Manifest.permission.CAMERA)
            )
            startCamera()
        }
    }
    private fun startCamera() {

        val cameraProviderFuture = context?.let { ProcessCameraProvider.getInstance(it) }

        cameraProviderFuture?.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(
                    binding.previewView.surfaceProvider
                )
            }


            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer() { barcode ->

                        if (barcode.isNotEmpty()) {
                            binding.boxSuccess.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), barcode, Toast.LENGTH_SHORT).show()
                            // findNavController().navigate(R.id.action_payFragment_to_searchedBarcodeFragment)
                          //  viewModel.sendBarcode(barcode)
                        } else {
                            binding.boxSuccess.visibility = View.GONE
                        }
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (e: Exception) {
                Log.e("PreviewUseCase", "Binding failed! :(", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    fun createBarcodeScanner(){
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_QR_CODE
            )
            .build()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


}