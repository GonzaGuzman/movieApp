package com.zalo.movieappchallenge.search.searchRepository

import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.core.Single

class SearchRepository(private val apiService: APIServiceImplements) {
    fun searchMovie(query: String): Single<MoviesResponse> = apiService.searchMovie(query)
}