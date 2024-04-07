package com.example.giphy_android.data.remote.response.gif

import com.example.giphy_android.data.remote.response.gif.images.Images
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("bitly_url") val bitlyUrl: String,
    @SerializedName("embed_url") val embedUrl: String,
    @SerializedName("username") val username: String,
    @SerializedName("source") val source: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("create_datetime") val createDateTime: String,
    @SerializedName("title") val title: String,
    @SerializedName("alt_text") val altText: String?,
    @SerializedName("url") val url: String,
    @SerializedName("images") val images: Images
) : Serializable
