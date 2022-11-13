package ru.osa.gb.homework.movless02.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.osa.gb.homework.movless02.AppState
import ru.osa.gb.homework.movless02.model.RepositoryLocalImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryLocalImpl = RepositoryLocalImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMoviesFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMoviesListByTitle("test")))
        }.start()
    }
}