package com.maxintech.gestureswipetool.gesture

import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import jakarta.inject.Inject

class HandGestureProcessor @Inject constructor() {

    private var previousX: Float? = null
    private var previousY: Float? = null

    fun processResult(result: HandLandmarkerResult?): HandGestureType {
        val landmarks = result?.landmarks()?.firstOrNull() ?: return HandGestureType.NONE

        val currentX = landmarks[0].x()
        val currentY = landmarks[0].y()

        val gesture = when {
            previousX != null && previousY != null -> {
                val dx = currentX - previousX!!
                val dy = currentY - previousY!!
                val threshold = 0.04f

                when {
                    dx > threshold && dx > Math.abs(dy) -> HandGestureType.SWIPE_RIGHT
                    dx < -threshold && Math.abs(dx) > Math.abs(dy) -> HandGestureType.SWIPE_LEFT
                    dy > threshold && dy > Math.abs(dx) -> HandGestureType.SWIPE_DOWN
                    dy < -threshold && Math.abs(dy) > Math.abs(dx) -> HandGestureType.SWIPE_UP
                    else -> HandGestureType.NONE
                }
            }
            else -> HandGestureType.NONE
        }

        previousX = currentX
        previousY = currentY

        return gesture
    }
}
