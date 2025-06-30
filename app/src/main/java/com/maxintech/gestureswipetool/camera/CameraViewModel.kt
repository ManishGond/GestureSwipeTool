package com.maxintech.gestureswipetool.camera

import android.app.Application
import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {

    private val _cameraProvider = MutableLiveData<ProcessCameraProvider?>()
    val cameraProvider: LiveData<ProcessCameraProvider?> = _cameraProvider

    fun startCamera(context: Context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            _cameraProvider.postValue(cameraProviderFuture.get())
        }, ContextCompat.getMainExecutor(context))
    }
}
