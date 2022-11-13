package ru.osa.gb.homework.movless02.model.data

import ru.osa.gb.homework.movless02.model.Movie


data class SearchByTitleDTO(
    val searchType: String,
    val expression: String,
    val results: List<Movie>,
    val errorMessage: String
)