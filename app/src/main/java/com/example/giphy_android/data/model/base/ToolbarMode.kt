package com.example.giphy_android.data.model.base

sealed class ToolbarMode {
    object InputOnly : ToolbarMode()
    object Search : ToolbarMode()
    object TextOnly : ToolbarMode()
}