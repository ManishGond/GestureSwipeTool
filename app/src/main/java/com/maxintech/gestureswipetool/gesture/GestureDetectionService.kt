package com.maxintech.gestureswipetool.gesture

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import com.maxintech.gestureswipetool.R

class GestureDetectionService : LifecycleService() {

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    private lateinit var gestureProcessor: HandGestureProcessor
    private var isRunning = false

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        handLandmarkerHelper = HandLandmarkerHelper(this)
        gestureProcessor = HandGestureProcessor(GestureInterpreter())
    }

    private fun startForegroundService() {
        val channelId = "gesture_detection"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Gesture Detection Service",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Gesture Detection Running")
            .setContentText("Monitoring hand gestures...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1001, notification)
    }

    private fun performGestureAction(gesture: HandGestureType) {
        when (gesture) {
            HandGestureType.SWIPE_UP -> {
                Log.d("GestureService", "🎯 Action: Swipe Up - Performing Next")
                // TODO: add real action, like launching intent
            }
            HandGestureType.SWIPE_DOWN -> {
                Log.d("GestureService", "🎯 Action: Swipe Down - Performing Previous")
                // TODO: add real action here too
            }
            else -> Unit
        }
    }

    fun startCameraDetection() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = androidx.camera.core.Preview.Builder().build()
            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            analysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
                val result: HandLandmarkerResult? = handLandmarkerHelper.detectHands(imageProxy)
                val gesture = gestureProcessor.processResult(result)

                if (gesture != HandGestureType.NONE) {
                    Log.d("GestureService", "🎉 Detected: $gesture")
                    performGestureAction(gesture)
                }

                imageProxy.close()
            }

            val selector = androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, selector, preview, analysis)
            } catch (e: Exception) {
                Log.e("GestureService", "Camera bind failed", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (!isRunning) {
            isRunning = true
            startCameraDetection()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = super.onBind(intent)
}
