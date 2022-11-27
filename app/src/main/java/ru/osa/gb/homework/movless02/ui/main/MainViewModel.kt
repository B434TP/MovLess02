package ru.osa.gb.homework.movless02.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.osa.gb.homework.movless02.AppState
import ru.osa.gb.homework.movless02.model.RepositoryInternetImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryInternetImpl = RepositoryInternetImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMoviesFromInternetSource(searchText: String) = getDataFromInternetSource(searchText)

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDataFromInternetSource(searchText: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMoviesListByTitle(searchText)))
        }.start()
    }
}