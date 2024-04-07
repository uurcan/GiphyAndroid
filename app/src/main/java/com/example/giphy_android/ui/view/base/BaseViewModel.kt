package com.example.giphy_android.ui.view.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<S>(private val stateFlow: MutableStateFlow<S>): ViewModel() {

    var state: S
        get() = stateFlow.value
        set(value) {
            stateFlow.value = value
        }

    @Composable
    fun subscribeToState() = stateFlow.collectAsState().value

}