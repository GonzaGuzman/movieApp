package com.zalo.movieappchallenge.detail.detailDatasource

import com.zalo.movieappchallenge.network.models.Movie
import io.reactivex.rxjava3.disposables.Disposable

interface DetailDataSource {
    fun getMovieDetail(
        id: Long,
        onSuccess: (responsive: Movie) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

    fun getMovieLocalDatabase(
        id: Long,
        onSuccess: (responsive: Movie) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

    fun insertMovie(
        movie: Movie,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable
    fun setCountItems(itemNumber: Int)
    fun getCountItems(): Int
}