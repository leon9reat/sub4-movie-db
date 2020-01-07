package com.medialink.sub4moviedb.data.movie

import com.medialink.sub4moviedb.data.OperationCallback

interface MovieDataSource {
    var page: Int
    var language: String

    fun getMovies(callback: OperationCallback)
    fun cancel()
}