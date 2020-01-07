package com.medialink.sub4moviedb.ui.favorite.tv_show

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.medialink.sub4moviedb.database.Favorite
import com.medialink.sub4moviedb.database.FavoriteDatabase
import com.medialink.sub4moviedb.database.FavoriteRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TvShowFavoriteViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val favoriteRepo: FavoriteRepository

    init {
        val favDao = FavoriteDatabase.getDatabase(application.applicationContext)
            .favoriteDao()
        favoriteRepo = FavoriteRepository(favDao)
    }

    val favTvShow: LiveData<List<Favorite>>
        get() = favoriteRepo.favTvShow

    fun deleteFavorite(aFavorite: Favorite) {
        launch { favoriteRepo.delete(aFavorite) }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}