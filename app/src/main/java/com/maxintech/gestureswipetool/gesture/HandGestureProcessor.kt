package com.maxintech.gestureswipetool.gesture

import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import javax.inject.Inject

class HandGestureProcessor @Inject constructor(
    private val gestureInterpreter: GestureInterpreter
) {
    fun processResult(result: HandLandmarkerResult?): HandGestureType {
        if (result == null) return HandGestureType.NONE
        return gestureInterpreter.detectGesture(result)
    }
}
