package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
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
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flashlight.ui.MainScreenViewModel
import com.example.flashlight.utils.longToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.top_app_bar_title_flashlight),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        ) { paddingValues ->

            Surface(modifier = Modifier.padding(paddingValues)) {
                FlashlightButton(
                    viewModel.flashLightState,
                    onFlashlightStateChange = {
                        viewModel.changeFlashlightState()
                    },
                    LocalContext.current
                )
            }
        }
    }
}

@Composable
fun FlashlightButton(
    flashlightState: State<Boolean>,
    onFlashlightStateChange: () -> Unit,
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        FilledIconToggleButton(
            checked = flashlightState.value,
            shape = RoundedCornerShape(4.dp),
            onCheckedChange = {
                onFlashlightStateChange()
                switchFlashlightOnOff(flashlightState, context)
            }
        ){
            if (flashlightState.value){
                Image(
                    painter = painterResource(id = R.drawable.flashlight_on_64),
                    contentDescription = stringResource(R.string.content_description_light_is_on)

                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.flashlight_off_64),
                    contentDescription = stringResource(R.string.content_description_light_is_off)
                )
            }
        }
    }
}

fun switchFlashlightOnOff(
    flashlightState: State<Boolean>,
    context: Context
) {
    lateinit var cameraID: String
    val cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    try {
        cameraID = cameraManager.cameraIdList[0]
    } catch (e: Exception) {
        e.printStackTrace()
    }

    if (flashlightState.value) {
        try {
            cameraManager.setTorchMode(cameraID, true)
            context.longToast(context.getString(R.string.flashlight_turned_on))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        try {
            cameraManager.setTorchMode(cameraID, false)
            context.longToast(context.getString(R.string.flashlight_turned_off))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
