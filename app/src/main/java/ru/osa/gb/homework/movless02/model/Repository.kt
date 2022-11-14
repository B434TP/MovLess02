package ru.osa.gb.homework.movless02.model

interface Repository {
    fun getMovieById(id : String): MovieFullData
    fun getMoviesListByTitle(searchString : String): List<Movie>
}