package com.zalo.movieappchallenge.home.homePresenter

interface HomePresenterActions {
fun initComponent(page:Int)
fun getMovies(page: Int)
}