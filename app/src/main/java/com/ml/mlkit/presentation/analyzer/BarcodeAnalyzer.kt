package com.ml.mlkit.presentation.analyzer

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.ml.mlkit.presentation.ui.barcode.BarcodeListener

class BarcodeAnalyzer(private val barcodeListener: BarcodeListener): ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        if (mediaImage != null) {
            val imageScan = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            scanner.process(imageScan)

                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcodeListener(barcode.rawValue ?: "")

                    }
                }
                .addOnFailureListener {

                }
                .addOnCompleteListener {
                    image.close()
                }
        }
    }
}