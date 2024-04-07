package com.example.giphy_android.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.giphy_android.R

@Composable
fun getDimensionSpacing(): Dp = dimensionResource(id = R.dimen.dimension_spacing)

@Composable
fun getContentSpacing(): Dp = dimensionResource(id = R.dimen.content_spacing)

@Composable
fun getInputSpacing(): Dp = dimensionResource(id = R.dimen.input_spacing)