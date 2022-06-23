package com.zalo.movieappchallenge.home.homeDataSource

import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.disposables.Disposable

interface HomeDataSource {
    fun getListMovies(
        page: Int,
        onSuccess: (responsive: MoviesResponse) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable
}