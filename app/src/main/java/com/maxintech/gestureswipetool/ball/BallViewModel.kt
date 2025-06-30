package com.maxintech.gestureswipetool.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.view.WindowManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maxintech.gestureswipetool.gesture.HandGestureType
import com.maxintech.gestureswipetool.gesture.mapGestureToAction
import kotlinx.coroutines.launch

class BallViewModel(app: Application) : AndroidViewModel(app) {

    private val context = getApplication<Application>().applicationContext

    fun handleGesture(gesture: HandGestureType, activity: Activity?) = viewModelScope.launch {
        when (val action = mapGestureToAction(gesture)) {
            BallAction.IncreaseVolume -> adjustVolume(AudioManager.ADJUST_RAISE)
            BallAction.DecreaseVolume -> adjustVolume(AudioManager.ADJUST_LOWER)
            BallAction.IncreaseBrightness -> adjustBrightness(activity, +0.1f)
            BallAction.DecreaseBrightness -> adjustBrightness(activity, -0.1f)
            BallAction.Idle -> {}
        }
    }

    private fun adjustVolume(direction: Int) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustVolume(direction, AudioManager.FLAG_SHOW_UI)
    }

    private fun adjustBrightness(activity: Activity?, delta: Float) {
        activity?.let {
            val lp = it.window.attributes
            lp.screenBrightness = (lp.screenBrightness + delta).coerceIn(0.1f, 1.0f)
            it.window.attributes = lp
        }
    }
}
