package com.zalo.movieappchallenge.search.searchPresenter

import android.content.Intent
import android.util.Log
import com.zalo.movieappchallenge.search.searchDatasource.SearchDatasource
import com.zalo.movieappchallenge.util.KEY_SEARCH
import io.reactivex.rxjava3.disposables.CompositeDisposable

const val TAG = "SearchPresenter"

class SearchPresenter(
    private val searchView: SearchView,
    private val searchDatasource: SearchDatasource,
) : SearchPresenterActions {
    private val composite = CompositeDisposable()

    //utiliza query para buscar un titulo en API TMdb y actualiza adapter con lista de resultados
    override fun searchMovies(intent: Intent) {
        val search = intent.getStringExtra(KEY_SEARCH) ?: ""
        with(searchView) {
            composite.add(
                searchDatasource.searchMovie(search,
                    {
                        loadRecycler()
                        onPopularMoviesFetched(it.movies)
                    },
                    { Log.e(TAG, it.message.toString()) })
            )
            textSearch()
        }
    }
}