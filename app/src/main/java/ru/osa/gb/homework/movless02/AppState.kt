package ru.osa.gb.homework.movless02

import ru.osa.gb.homework.movless02.model.Movie

sealed class AppState {
    data class Success(val moviesData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}