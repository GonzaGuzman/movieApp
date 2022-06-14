package com.zalo.movieappchallenge.detail.detailPresenter


import com.zalo.movieappchallenge.network.models.Movie

interface DetailView {
    fun retrieverExtras(movie: Movie)
    fun showSnackBar(message: String)
}