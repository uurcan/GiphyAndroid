package com.example.giphy_android.data.remote.response.gif

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meta(
    @SerializedName("status") val status: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("response_id") val responseId: String
) : Serializable
