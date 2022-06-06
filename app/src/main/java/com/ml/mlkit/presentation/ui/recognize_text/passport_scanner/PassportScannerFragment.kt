package com.ml.mlkit.presentation.ui.recognize_text.passport_scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.ml.mlkit.databinding.FragmentPassportScannerBinding
import com.ml.mlkit.presentation.analyzer.PassportDetectAnalyzer
import com.ml.mlkit.presentation.base.BaseFragment
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias PassportDetectListener = (isPassportDetect: Boolean) -> Unit

class PassportScannerFragment :
    BaseFragment<FragmentPassportScannerBinding>(FragmentPassportScannerBinding::inflate) {
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var passportPhotoFile: File
    private lateinit var uri: Uri
    private lateinit var currentVideoPath: String

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

    private fun requestPermissions() {
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(
                    binding.scanFacePreviewView.surfaceProvider
                )
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, PassportDetectAnalyzer { isPassportDetect ->

                        if (isPassportDetect) {
                            binding.boxGreen.visibility =
                                View.VISIBLE

                            binding.cameraCaptureButton.setOnClickListener {
                                takePhoto()
                            }

                        } else {
                            binding.boxGreen.visibility =
                                View.GONE
                        }

                    })
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis,
                    imageCapture

                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Filed Camera !", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {

        val imageCapture = imageCapture ?: return
       // passportPhotoFile = createPassportFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(passportPhotoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("photo", "capturedd")

//                    uri = FileProvider.getUriForFile(
//                        requireContext(),
//                        BuildConfig.APPLICATION_ID + ".provider", passportPhotoFile
//                    )
                    //  sharedViewModel.setUriPassportPhoto(uri)
//                    binding.root.let { view ->
//                        Navigation.findNavController(view)
//                            .navigate(R.id.action_detectPassportPhotoFragment_to_detectPassportPhotoSubmitFragment)
//                    }


                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("photo", "error")
                }

            })

    }

//    @SuppressLint("SimpleDateFormat")
//    fun createPassportFile(): File {
//     //   val timeStamp: String = SimpleDateFormat(ConstantsTools.FILENAME_DATE_FORMAT).format(Date())
//        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(
//            "Biometric_Passport_${timeStamp}",
//            ".jpg",
//            storageDir
//        ).apply {
//            currentVideoPath = absolutePath
//        }
//
//    }
}