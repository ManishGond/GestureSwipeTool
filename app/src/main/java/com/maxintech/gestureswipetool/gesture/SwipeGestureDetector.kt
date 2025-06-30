package com.maxintech.gestureswipetool.gesture

class SwipeGestureDetector {

    private var previousY: Float? = null
    private val swipeThreshold = 0.15f  // Adjust based on testing

    fun detectSwipe(currentY: Float): HandGestureType {
        val prevY = previousY
        previousY = currentY

        return when {
            prevY != null && (prevY - currentY) > swipeThreshold -> HandGestureType.SWIPE_UP
            prevY != null && (currentY - prevY) > swipeThreshold -> HandGestureType.SWIPE_DOWN
            else -> HandGestureType.NONE
        }
    }
}
