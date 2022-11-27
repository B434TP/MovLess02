package ru.osa.gb.homework.movless02.model

interface Repository {
    fun getMovieById(id : String): Movie
    fun getMoviesListByTitle(searchString : String): List<Movie>
}