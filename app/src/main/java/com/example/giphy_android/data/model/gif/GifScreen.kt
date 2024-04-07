package com.example.giphy_android.data.model.gif

sealed class GifScreen(val name: String) {
    object Initial : GifScreen("initial")
    object Search : GifScreen("search")
    object Detail : GifScreen("detail")
}