package com.medialink.sub4moviedb.ui.popular.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.medialink.sub4moviedb.Consts
import com.medialink.sub4moviedb.data.OperationCallback
import com.medialink.sub4moviedb.data.movie.MovieRepository
import com.medialink.sub4moviedb.database.Favorite
import com.medialink.sub4moviedb.database.FavoriteDatabase
import com.medialink.sub4moviedb.database.FavoriteRepository
import com.medialink.sub4moviedb.model.movie.Movie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    private val movieRepository = MovieRepository()
    private val repoFavorite: FavoriteRepository

    private val _movies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val movies: LiveData<List<Movie>> = _movies

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isDataChanged = MutableLiveData<Boolean>()
    val isDataChanged : LiveData<Boolean> = _isDataChanged

    var localLanguage: String = "en"

    init {
        val favDao = FavoriteDatabase.getDatabase(application.applicationContext)
            .favoriteDao()
        repoFavorite = FavoriteRepository(favDao)

        //loadMovies(1)
    }

    fun loadMovies(page: Int) {
        _isViewLoading.value = true

        movieRepository.page = page
        movieRepository.getMovies(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.value = false
                _isDataChanged.value = false

                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _isEmptyList.value = false

                        val listMovie = obj as List<Movie>

                        launch {

                            /*val x = withContext(Dispatchers.IO) {
                                return@withContext async { updateFavorite(listMovie) }
                            }*/

                            var x = arrayListOf<Movie>()

                            for (i in listMovie.indices) {
                                val dataMovie =
                                    async {
                                        val isFav = repoFavorite.isFavorite(listMovie[i].id as Int)
                                        listMovie[i].isFavorite = isFav
                                        return@async listMovie[i]
                                    }

                                x.add(dataMovie.await())
                            }

                            _movies.postValue(x)
                        }

                    }
                }
                Log.d("debug", "onSuccess :")
            }

            override fun onError(obj: Any?) {
                _isViewLoading.value = false
                _isEmptyList.value = false
                _onMessageError.value = obj
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun insertFavorite(aFav: Favorite) {
        launch { repoFavorite.insert(aFav) }
    }

    fun updateMovieFavorite(movie: Movie, aPosition: Int) {
        val akanUpdate: ArrayList<Movie> = _movies.value as ArrayList<Movie>

        akanUpdate[aPosition].id?.let {
            if (akanUpdate[aPosition].isFavorite) {
                launch { repoFavorite.deleteOne(it) }
                akanUpdate[aPosition].isFavorite = false
            } else {
                val favItem = Favorite(
                    0,
                    movie.id!!,
                    movie.title!!,
                    movie.releaseDate!!,
                    movie.overview!!,
                    movie.posterPath!!,
                    Consts.MOVIE_TYPE
                )

                launch { repoFavorite.insert(favItem) }
                akanUpdate[aPosition].isFavorite = true
            }

            _movies.value = akanUpdate
        }
    }

    fun setDataChanged() {
        _isDataChanged.value = true
    }

}