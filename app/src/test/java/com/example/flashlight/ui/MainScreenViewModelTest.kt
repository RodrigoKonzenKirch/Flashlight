package com.example.flashlight.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MainScreenViewModelTest(){

    lateinit var viewModel: MainScreenViewModel

    @Before
    fun setup(){
        viewModel = MainScreenViewModel()
    }

    @Test
    fun `flashlightState initial state should be false`(){
        assertThat(viewModel.flashLightState.value).isFalse()
    }

    @Test
    fun `changeFlashlight called first time should change flashlightState value to true`(){
        viewModel.changeFlashlightState()
        assertThat(viewModel.flashLightState.value).isTrue()
    }

}