package com.zalo.movieappchallenge.detail.detailPresenter

import com.zalo.movieappchallenge.network.models.Movie

//interfase de metodos de DetailView
interface DetailView {
    fun retrieverExtras(movie: Movie)
    fun showSnackBar(message: String)
}