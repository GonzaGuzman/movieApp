package com.zalo.movieappchallenge.home.homePresenter

import com.zalo.movieappchallenge.network.models.Movie

interface HomeView {
    fun attachMoviesOnScrollListener()
    fun onPopularMoviesFetched(movies: List<Movie>)
    fun showSnackBar(message: String)
    fun loadRecycler()
    fun getMoviesByPage(page: Int)
    fun textSearch()
}