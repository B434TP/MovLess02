package ru.osa.gb.homework.movless02.model

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.osa.gb.homework.movless02.model.data.SearchByTitleDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class RepositoryInternetImpl : Repository {

    //  мой ключ к API
    private val API_KEY = "k_5d3794be"

    private var Tag = "MYREPO"

    // Возвращает конкретный фильм по его id
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMovieById(id: String): MovieFullData {
        return getFullDataFromSearchResult(searchByIDOnServer(id))
    }

    // Поиск фильмов из базы по названию
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMoviesListByTitle(searchString: String): List<Movie> {

        return getListFromSearchResult(searchByTitleOnServer(searchString));
    }


    // Возвращает полные данные о фильме из результата поиска
    private fun getFullDataFromSearchResult(GSONResult: String): MovieFullData {

        Log.d(Tag, "GSONResult: $GSONResult")

        val searchResDTO: MovieFullData =
            Gson().fromJson(GSONResult, MovieFullData::class.java)



        return searchResDTO
    }


    // Возвращает список фильмов из результатов поиска
    private fun getListFromSearchResult(GSONResult: String): List<Movie> {

        val searchResDTO: SearchByTitleDTO =
            Gson().fromJson(GSONResult, SearchByTitleDTO::class.java)

        Log.d(Tag, "searchResDTO: ${searchResDTO.results}")

        return searchResDTO.results
    }

    // Запрашивает данные через API, поиск по ID фильма
    @RequiresApi(Build.VERSION_CODES.N)
    private fun searchByIDOnServer(ID: String): String {

        val uri: String = "https://imdb-api.com/en/API/Title/$API_KEY/$ID"
        Log.d(Tag, "searchByTitleOnServer uri ------------- $uri")

        return getDataFromServer(uri)

    }



    // Запрашивает данные через API, поиск по названию фильма
    @RequiresApi(Build.VERSION_CODES.N)
    private fun searchByTitleOnServer(searchString: String): String {

        val uri: String = "https://imdb-api.com/API/Search/$API_KEY/$searchString"
        Log.d(Tag, "searchByTitleOnServer uri ------------- $uri")

        return getDataFromServer(uri)

    }

    // Запрашивает данные c сервера по uri
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDataFromServer(uri: String): String {


        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val url = URL(uri)
        var answer = "{}"

        var urlConnection: HttpsURLConnection? = null
        try {
            urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            Log.d(Tag, "getDataFromServer GET URL= $url")
            var reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            Log.d(Tag, "getDataFromServer reader:  ${reader.toString()}")
            answer = getLines(reader)

        } catch (e: Exception) {
            Log.e("MYREPO", "Fail connection", e)
            Log.d(Tag, "getDataFromServer: Exception!")
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            Log.d(Tag, "getDataFromServer: disconnect")
        }

        Log.d(Tag, "getDataFromServer answer: $answer")
        return answer
    }


    // Преобразует поток в строку
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        val ret = reader.lines().collect(Collectors.joining("\n"))

        Log.d(Tag, "getLines: $ret")
        return ret
    }


}




