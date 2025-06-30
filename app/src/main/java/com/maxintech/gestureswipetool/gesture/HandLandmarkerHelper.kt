package com.maxintech.gestureswipetool.gesture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import com.maxintech.gestureswipetool.utils.toBitmap

class HandLandmarkerHelper(context: Context) {
    private val handLandmarker: HandLandmarker

    init {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("hand_landmarker.task") // 👈 Make sure this file exists in your `assets/`
            .build()

        val options = HandLandmarker.HandLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setNumHands(1)
            .build()

        handLandmarker = HandLandmarker.createFromOptions(context, options)
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    fun detectHands(imageProxy: ImageProxy): HandLandmarkerResult? {
        val mediaImage = imageProxy.image ?: return null
        val rotation = imageProxy.imageInfo.rotationDegrees

        val rawBitmap = imageProxy.toBitmap()

        // Rotate the bitmap
        val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
        val rotatedBitmap = Bitmap.createBitmap(
            rawBitmap, 0, 0,
            rawBitmap.width, rawBitmap.height,
            matrix, true
        )

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        return handLandmarker.detect(mpImage)
    }
}
