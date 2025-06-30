package com.maxintech.gestureswipetool.gesture

import com.maxintech.gestureswipetool.viewmodel.BallAction

fun mapGestureToAction(type: HandGestureType): BallAction {
    return when (type) {
        HandGestureType.SWIPE_LEFT -> BallAction.DecreaseBrightness
        HandGestureType.SWIPE_RIGHT -> BallAction.IncreaseBrightness
        HandGestureType.SWIPE_UP -> BallAction.IncreaseVolume
        HandGestureType.SWIPE_DOWN -> BallAction.DecreaseVolume
        else -> BallAction.Idle
    }
}
