package ru.osa.gb.homework.movless02.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val image: String,
    val title: String,
    val description: String
) : Parcelable



