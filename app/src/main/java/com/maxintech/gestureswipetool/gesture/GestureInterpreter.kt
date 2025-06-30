package com.maxintech.gestureswipetool.gesture

import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import javax.inject.Inject

class GestureInterpreter @Inject constructor() {
    private val swipeDetector = SwipeGestureDetector()

    fun detectGesture(result: HandLandmarkerResult): HandGestureType {
        val landmarks = result.landmarks().firstOrNull() ?: return HandGestureType.NONE
        val indexFingerTipY = landmarks.getOrNull(8)?.y() ?: return HandGestureType.NONE
        return swipeDetector.detectSwipe(indexFingerTipY)
    }
}
