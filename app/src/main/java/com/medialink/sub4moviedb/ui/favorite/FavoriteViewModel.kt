package com.medialink.sub4moviedb.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medialink.sub4moviedb.database.FavoriteDatabase
import com.medialink.sub4moviedb.database.FavoriteRepository
import com.medialink.sub4moviedb.database.Favorite
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val repository : FavoriteRepository

    init {
        val favDao = FavoriteDatabase.getDatabase(application.applicationContext)
            .favoriteDao()
        repository = FavoriteRepository(favDao)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val favMovies: LiveData<List<Favorite>>
        get() = repository.favMovie

    val favTvShow: LiveData<List<Favorite>>
        get() = repository.favTvShow

    fun insert(aFav: Favorite) {
        launch { repository.insert(aFav) }
    }

    fun delete(aFav: Favorite) {
        launch {
            repository.delete(aFav)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}