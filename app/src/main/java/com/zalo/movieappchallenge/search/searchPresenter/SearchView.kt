package com.zalo.movieappchallenge.search.searchPresenter

import com.zalo.movieappchallenge.network.models.Movie

interface SearchView {
    fun loadRecycler()
    fun onPopularMoviesFetched(movies: List<Movie>)
    fun showSnackBar(message: String)
    fun textSearch()
}