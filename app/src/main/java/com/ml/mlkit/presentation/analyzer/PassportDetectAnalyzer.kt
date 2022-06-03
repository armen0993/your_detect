package com.ml.mlkit.presentation.analyzer

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.ml.mlkit.presentation.ui.passport_scanner.PassportDetectListener

class PassportDetectAnalyzer(private val isPassportDetect: PassportDetectListener) :
    ImageAnalysis.Analyzer {
    private val listPassportCheckName =
        mutableListOf<String>("PASSPORT", "REPUBLIC", "COUNTRY CODE", "<<<<<")
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val result = recognizer.process(image).addOnSuccessListener {
                isPassportDetect(false)
                for (block in it.textBlocks) {
                    val blockText = block.text
                    val blockCornerPoints = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox

                        for (element in line.elements) {
                            val elementText = element.text
                            val elementCornerPoints = element.cornerPoints
                            val elementFrame = element.boundingBox

                            if (listPassportCheckName.contains(elementText)) {
                                Log.d("lineText", " lineText = ${elementText}\n")
                                isPassportDetect(true)
                            }
                        }
                    }
                }


            }
                .addOnFailureListener {


                }
                .addOnCompleteListener {
                    imageProxy.close()

                }
        }
    }
}