package com.maxintech.gestureswipetool.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maxintech.gestureswipetool.camera.CameraPreview
import com.maxintech.gestureswipetool.camera.CameraViewModel
import com.maxintech.gestureswipetool.gesture.HandGestureType
import com.maxintech.gestureswipetool.gesture.HandLandmarkerHelper
import com.maxintech.gestureswipetool.viewmodel.ScreenViewModel
import com.maxintech.gestureswipetool.viewmodel.SwipeGestureViewModel
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    gestureViewModel: SwipeGestureViewModel = hiltViewModel(),
    ballViewModel: ScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val cameraProvider by cameraViewModel.cameraProvider.observeAsState()
    val handLandmarkerHelper = remember { HandLandmarkerHelper(context) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (granted) cameraViewModel.startCamera(context)
    }

    LaunchedEffect(hasCameraPermission) {
        if (hasCameraPermission) {
            cameraViewModel.startCamera(context)
        }
    }

    val detectedGesture = cameraViewModel.detectedGesture.value
    LaunchedEffect(detectedGesture) {
        gestureViewModel.updateGesture(detectedGesture)
    }

    val gestureState by gestureViewModel.gestureState.collectAsState()

    LaunchedEffect(gestureState) {
        while (gestureState != HandGestureType.NONE && gestureState != HandGestureType.FIST) {
            ballViewModel.handleGesture(gestureState, activity)
            delay(150L)
        }

        // reset gesture after fist is detected
        if (gestureState == HandGestureType.FIST) {
            gestureViewModel.resetGesture()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (hasCameraPermission) {
            cameraProvider?.let {
                CameraPreview(
                    cameraProvider = it,
                    modifier = Modifier.fillMaxSize(),
                    analyzer = { imageProxy ->
                        val result = handLandmarkerHelper.detectHands(imageProxy)
                        cameraViewModel.processLandmarkerResult(result)
                        imageProxy.close()
                    }
                )

                if (gestureState != HandGestureType.NONE) {
                    Text(
                        text = if (gestureState == HandGestureType.FIST) "Gesture Stopped" else "Gesture: ${gestureState.name}",
                        color = Color.Cyan,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            } ?: Text("Loading Camera...", color = Color.White)
        } else {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Camera permission required", color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Grant Permission")
                }
            }
        }
    }
}
