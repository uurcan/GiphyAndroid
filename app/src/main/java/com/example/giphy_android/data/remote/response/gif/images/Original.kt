package com.example.giphy_android.data.remote.response.gif.images

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Original(
    @SerializedName("url") val url: String
) : Serializable
