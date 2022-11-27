package ru.osa.gb.homework.movless02.model

data class MovieFullData(
    val id: String,
    val year: String,
    val runtimeStr : String,
    val imDbRating : String,
    val plot: String
)