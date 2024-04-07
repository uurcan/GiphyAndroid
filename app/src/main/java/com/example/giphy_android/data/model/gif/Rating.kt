package com.example.giphy_android.data.model.gif

import com.example.giphy_android.R

sealed class Rating {
    data class GeneralAudience(
        val rating: String = "g"
    ) : Rating()

    data class ParentalGuidance(
        val rating: String = "pg"
    ) : Rating()

    data class ParentCautioned(
        val rating: String = "pg-13"
    ) : Rating()

    data class Restricted(
        val rating: String = "r"
    ) : Rating()

    companion object {
        fun getImageResId(rating: String): Int {
            return when (rating) {
                GeneralAudience().rating -> R.drawable.g_rating
                ParentalGuidance().rating -> R.drawable.pg_rating
                ParentCautioned().rating -> R.drawable.pg_13_rating
                Restricted().rating -> R.drawable.r_rating
                else -> throw IllegalArgumentException("Invalid rating")
            }
        }
    }
}
