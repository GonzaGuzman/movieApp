package com.zalo.movieappchallenge.home.homePresenter

//interface con metodos de HomePresenter
interface HomePresenterActions {
    fun initComponent(page: Int)
    fun getMovies(page: Int)
}