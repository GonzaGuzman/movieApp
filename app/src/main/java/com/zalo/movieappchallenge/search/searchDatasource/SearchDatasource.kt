package com.zalo.movieappchallenge.search.searchDatasource

import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.disposables.Disposable

interface SearchDatasource {
    fun searchMovie(
        query: String,
        onSuccess: (responsive: MoviesResponse) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable
}