package com.medialink.sub4moviedb.ui.favorite.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.medialink.sub4moviedb.data.movie.MovieRepository
import com.medialink.sub4moviedb.database.FavoriteDatabase
import com.medialink.sub4moviedb.database.FavoriteRepository
import com.medialink.sub4moviedb.database.Favorite
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieFavoriteViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    private val repository: FavoriteRepository

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        val favDao = FavoriteDatabase.getDatabase(application.applicationContext).favoriteDao()
        repository = FavoriteRepository(favDao)
    }

    val favMovie: LiveData<List<Favorite>>
        get() = repository.favMovie

    fun deleteFavorite(aFavorite: Favorite) {
        launch {
            repository.delete(aFavorite)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}