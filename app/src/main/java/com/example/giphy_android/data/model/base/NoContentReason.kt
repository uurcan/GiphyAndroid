package com.example.giphy_android.data.model.base

import com.example.giphy_android.R

sealed class NoContentReason {
    object None : NoContentReason()

    data class NoInternet(
        val textResId: Int? = R.string.error_no_network_connection,
        val imageResId: Int? = R.drawable.no_internet
    ) : NoContentReason()

    data class EmptyData(
        val textResId: Int? = R.string.search_result_empty,
        val imageResId: Int? = R.drawable.ic_empty_state
    ) : NoContentReason()
}