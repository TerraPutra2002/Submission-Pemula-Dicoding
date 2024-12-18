package com.example.submissionjaranannusantara

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jaranan (
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable