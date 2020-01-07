package com.medialink.sub4moviedb.data.tv_show

import com.medialink.sub4moviedb.data.OperationCallback

interface TvShowDataSource {
    var page: Int
    var language: String

    fun getTvShows(callback: OperationCallback)
    fun cancel()
}