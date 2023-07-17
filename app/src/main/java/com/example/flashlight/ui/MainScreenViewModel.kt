package com.example.flashlight.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor( ): ViewModel() {
    private val _flashLightState = mutableStateOf(false)
    var flashLightState: State<Boolean> = _flashLightState
        private set

    fun changeFlashlightState(){
        _flashLightState.value = !_flashLightState.value
    }





}