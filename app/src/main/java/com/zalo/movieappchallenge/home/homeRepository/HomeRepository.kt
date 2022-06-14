package com.zalo.movieappchallenge.home.homeRepository

import com.zalo.movieappchallenge.network.APIServiceImplements
import com.zalo.movieappchallenge.network.models.MoviesResponse
import io.reactivex.rxjava3.core.Single

class HomeRepository (private val apiServiceImpl:APIServiceImplements) {

    fun getPopularMovies(page:Int): Single<MoviesResponse> {
        return apiServiceImpl.getPopularMovies(page)
    }
}