package com.maxintech.gestureswipetool.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.maxintech.gestureswipetool.gesture.HandGestureType

class SwipeGestureViewModel : ViewModel() {

    private val _gestureState = MutableStateFlow(HandGestureType.NONE)
    val gestureState = _gestureState.asStateFlow()

    fun updateGesture(gesture: HandGestureType) {
        if (gesture != HandGestureType.NONE) {
            _gestureState.value = gesture
        }
    }

    fun resetGesture() {
        _gestureState.value = HandGestureType.NONE
    }
}
