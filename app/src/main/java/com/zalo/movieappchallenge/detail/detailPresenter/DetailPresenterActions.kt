package com.zalo.movieappchallenge.detail.detailPresenter


import android.content.Intent
import com.zalo.movieappchallenge.network.models.Movie

interface DetailPresenterActions {
    fun getMovieDetail(id: Long)
    fun initComponent(intent: Intent)
    fun insertMovie(movie: Movie)
    fun getMovieLocalDatabase(id: Long)
}