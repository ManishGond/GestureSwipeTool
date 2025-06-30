package com.maxintech.gestureswipetool.viewmodel

sealed class BallAction {
    object IncreaseVolume : BallAction()
    object DecreaseVolume : BallAction()
    object IncreaseBrightness : BallAction()
    object DecreaseBrightness : BallAction()
    object Idle : BallAction()
}
