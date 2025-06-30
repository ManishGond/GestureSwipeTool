package com.maxintech.gestureswipetool.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageAnalysis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.maxintech.gestureswipetool.camera.CameraPreview
import com.maxintech.gestureswipetool.camera.CameraViewModel
import com.maxintech.gestureswipetool.gesture.HandGestureType
import com.maxintech.gestureswipetool.gesture.HandLandmarkerHelper
import com.maxintech.gestureswipetool.viewmodel.SwipeGestureViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    gestureViewModel: SwipeGestureViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraProvider by cameraViewModel.cameraProvider.observeAsState()
    val handLandmarkerHelper = remember { HandLandmarkerHelper(context) }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // 👇 Setup MediaPipe Analyzer
    val analyzer = remember(cameraViewModel) {
        ImageAnalysis.Analyzer { imageProxy ->
            // TODO: Replace with your MediaPipe detection logic
            val result = handLandmarkerHelper.detectHands(imageProxy)
            cameraViewModel.processLandmarkerResult(result)
            imageProxy.close()
        }
    }

    // 👇 Launch camera after permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (granted) {
            cameraViewModel.startCamera(context)
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            cameraViewModel.startCamera(context)
        }
    }

    // 👇 Observe detected gesture and push to SwipeGestureViewModel
    val detectedGesture = cameraViewModel.detectedGesture.value
    LaunchedEffect(detectedGesture) {
        gestureViewModel.updateGesture(detectedGesture)
    }

    // 👇 Reset gesture after delay (auto clear after 1s)
    val gestureState by gestureViewModel.gestureState.collectAsState()
    LaunchedEffect(gestureState) {
        if (gestureState != HandGestureType.NONE) {
            delay(1000L)
            gestureViewModel.resetGesture()
        }
    }

    // 👇 UI Rendering
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (hasPermission) {
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

                // 👇 Show detected gesture
                if (gestureState != HandGestureType.NONE) {
                    Text(
                        text = gestureState.name,
                        color = Color.Cyan,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    )
                }
            } ?: Text("Loading Camera...", color = Color.White)
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
