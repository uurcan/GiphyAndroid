package com.example.giphy_android.data.remote.response.gif.random

import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.data.remote.response.gif.Meta
import com.example.giphy_android.data.remote.response.gif.Pagination
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RandomGifResponse(
    @SerializedName("data") val data: Data,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("meta") val meta: Meta
) : Serializable