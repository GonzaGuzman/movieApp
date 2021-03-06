package com.zalo.movieappchallenge.detail.detailPresenter

import android.content.Intent
import com.zalo.movieappchallenge.network.models.Movie

//interface de metodos de DetailPresenter
interface DetailPresenterActions {
    fun getMovieDetail(id: Long)
    fun initComponent(intent: Intent)
    fun insertMovie(movie: Movie)
    fun getMovieLocalDatabase(id: Long): Boolean
    fun dataBaseLimit(num: Int): Int
}