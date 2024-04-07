package com.example.giphy_android.data.remote.response.gif

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pagination(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
) : Serializable
