package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Flashlight",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                FlashlightButton(LocalContext.current)
            }
        }
    }
}

@Composable
fun FlashlightButton(context: Context) {
    val flashlightStatus = remember {
        mutableStateOf(false)
    }
    val buttonChecked = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        FilledIconToggleButton(
            // Could move the logic to a viewModel

            checked = buttonChecked.value,
            shape = RoundedCornerShape(4.dp),
            onCheckedChange = {
                flashlightStatus.value = !flashlightStatus.value

                lateinit var cameraID: String
                val cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

                try {
                    cameraID = cameraManager.cameraIdList[0]
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (flashlightStatus.value) {
                    try {
                        cameraManager.setTorchMode(cameraID, true)
                        Toast.makeText(context, "Flashlight turned on..", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        cameraManager.setTorchMode(cameraID, false)
                        Toast.makeText(context, "Flashlight turned off..", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        ){
            if (flashlightStatus.value){
                Image(painter = painterResource(id = R.drawable.flashlight_on_64), contentDescription = "Light is on")
            }else{
                Image(painter = painterResource(id = R.drawable.flashlight_off_64), contentDescription = "Light is off")
            }
        }
    }
}